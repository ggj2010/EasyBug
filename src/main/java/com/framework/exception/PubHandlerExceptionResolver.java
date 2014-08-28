package com.framework.exception;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.DocumentException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import com.gaoguangjin.exception.CLException;
import com.gaoguangjin.util.DateUtil;
import com.gaoguangjin.util.Global;



public class PubHandlerExceptionResolver  extends
SimpleMappingExceptionResolver  {
	
	protected ModelAndView doResolveException(
			javax.servlet.http.HttpServletRequest httpServletRequest,
			javax.servlet.http.HttpServletResponse httpServletResponse,
			java.lang.Object o, java.lang.Exception e) {
		httpServletRequest.setAttribute("ex", e);
		e.printStackTrace();
		
		CLException cle=null;
		Hashtable<String, Object[]> errorMap=null;
		
		try {
			 errorMap=Global.getErrorMap();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
		if(errorMap!=null){
			Iterator iter = errorMap.entrySet().iterator(); 
			while (iter.hasNext()) { 
			    Map.Entry entry = (Map.Entry) iter.next(); 
			    Object key = entry.getKey(); 
			    Object[] vals = (Object[])entry.getValue(); 
			    
			    String className=vals[1].toString();
			    if(e.getClass().toString().equals(className)){
			    	cle=new CLException();
			    	cle.setCode(key.toString());
			    	cle.setMsg(vals[0].toString());
			    	cle.setDetail(e.getMessage());
			    	cle.setStackTrace(e.getStackTrace());
			    	cle.setTime(DateUtil.getSystemTimeByFullString());
			    }
			} 
		}
		
		if(cle==null){
			cle=new CLException();
	    	cle.setCode("00000");
	    	cle.setMsg("δ֪�쳣");
	    	cle.setDetail(e.getMessage());
	    	cle.setStackTrace(e.getStackTrace());
	    	cle.setTime(DateUtil.getSystemTimeByFullString());
		}
		
		return super.doResolveException(httpServletRequest,
				httpServletResponse, o, cle);
	}
	
	

}
