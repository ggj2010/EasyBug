/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2013
 */

package com.gaoguangjin.baseinfo.controller;

import java.io.File;
import java.io.PrintWriter;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.framework.base.BaseController;

/**
 * �ϴ�
 * @author author
 * @version 1.0
 * @since 1.0
 */

@Controller
@RequestMapping("/main")
public class BiUploadController extends BaseController {
	
	private static final Logger logger = LoggerFactory.getLogger(BiUploadController.class);
	
	// �����ļ��ϴ���
	@RequestMapping(value = "/fileUpload", method = { RequestMethod.POST, RequestMethod.GET })
	public void fileUpload2(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String path = "ckeditor/uploader/upload/";
		String clientPath = "";
		String fileName = "";
		// �������·���
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
				.getServletContext());
		// ���form�Ƿ���enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// ��CommonsMultipartFile�̳ж���,ӵ������ķ���.
				MultipartFile file = multiRequest.getFile(iter.next());
				if (file != null) {
					fileName = file.getOriginalFilename();
					clientPath = request.getSession().getServletContext().getRealPath(path) + File.separator + fileName;
					File localFile = new File(clientPath);
					file.transferTo(localFile);
				}
			}
		}
		// F:/ѧϰ�����/apache-tomcat-6.0.37/webapps/Bug_01/ckeditor/uploader/upload/bi_role_menu.sqlbi_role_menu.sql
		path = request.getContextPath() + "/ckeditor/uploader/upload/";
		path = path.replace('\\', '/');// ����������滻�����������
		// CKEditorFuncNum�ǻص�ʱ��ʾ��λ�ã��������������
		String callback = request.getParameter("CKEditorFuncNum");
		out.println("<script type='text/javascript'>window.parent.CKEDITOR.tools.callFunction(" + callback + ", '"
				+ path + fileName + "', '');</script>");
		out.flush();
		out.close();
		
	}
}
