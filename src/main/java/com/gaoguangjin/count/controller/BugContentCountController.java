package com.gaoguangjin.count.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.base.Page;
import com.gaoguangjin.bug.service.BugContentService;
import com.gaoguangjin.count.service.BugContentCountService;
import com.gaoguangjin.count.vo.BugCountVO;
import com.gaoguangjin.project.entity.ProjectVO;
import com.gaoguangjin.project.service.ProjectService;

@Controller
@RequestMapping("/main")
public class BugContentCountController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(BugContentCountController.class);
	@Autowired
	private BugContentCountService bugCountService;
	@Autowired
	private BugContentService bugContentService;
	@Autowired
	private ProjectService projectService;
	
	/**
	 * ���ܣ�ͳ����Ŀ
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/project", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView organList(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			// ����ַ�ŵ�session����
			this.setSessionListUrl(request);
			List<String[]> sumList = bugCountService.findProject(bugCountVO);
			int sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
			}
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("sum", sum);
			view.addObject("sumList", sumList);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_project");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
		}
		catch (Exception e) {
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�ͳ����У��״̬
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/status", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView statusList(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			
			List<String[]> sumList = bugCountService.findStatus(bugCountVO);
			int sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
			}
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("sum", sum);
			view.addObject("sumList", sumList);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_status");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
		}
		catch (Exception e) {
			
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�ͳ��bug�����
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/handler", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView handlerList(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			
			List<String[]> sumList = bugCountService.findHandler(bugCountVO);
			int sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
			}
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("sum", sum);
			view.addObject("sumList", sumList);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_handler");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
		}
		catch (Exception e) {
			
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�ͳ��bug�����
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/assigner", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView assignerList(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request)
			throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			
			List<String[]> sumList = bugCountService.findAssigner(bugCountVO);
			int sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
			}
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("sum", sum);
			view.addObject("sumList", sumList);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_assigner");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
		}
		catch (Exception e) {
			
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�ͳ��bug������
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/create", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView createList(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			
			List<String[]> sumList = bugCountService.findCreate(bugCountVO);
			int sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
			}
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("sum", sum);
			view.addObject("sumList", sumList);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_create");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
		}
		catch (Exception e) {
			
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�ͳ��bug������
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/module", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView moduleList(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			
			List<String[]> sumList = bugCountService.findModule(bugCountVO);
			int sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
			}
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("sum", sum);
			view.addObject("sumList", sumList);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_module");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
		}
		catch (Exception e) {
			
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�ͳ��bug������
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/version", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView versionList(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			
			List<String[]> sumList = bugCountService.findVersion(bugCountVO);
			int sum = 0;
			for (int i = 0; i < sumList.size(); i++) {
				Object[] str = sumList.get(i);
				sum += Integer.parseInt(str[2].toString());
			}
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("sum", sum);
			view.addObject("sumList", sumList);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_version");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
		}
		catch (Exception e) {
			
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
	/**
	 * ���ܣ�ͳ����У������ѧУ
	 * @param fPayRecord : entity,pageNo :ҳ��,pageSize :
	 * @return ����view
	 */
	@RequestMapping(value = "/count/bugContent/show", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView show(BugCountVO bugCountVO, Integer pageNo, HttpServletRequest request) throws Exception {
		ModelAndView view = new ModelAndView();
		try {
			// ����ַ�ŵ�session����
			this.setSessionListUrl(request);
			pageNo = pageNo == null ? 1 : pageNo;
			int pageSize = this.getCookiesPageSize(request);
			
			Page pagedData = bugCountService.getPagedShow(bugCountVO, pageNo, pageSize);
			
			// �õ�������Ŀ��ͳ��ʱ���õ�
			ProjectVO projectVO = projectService.getProject();
			view.addObject("projectVO", projectVO);
			
			view.addObject("pagedData", pagedData);
			view.addObject("bugCountVO", bugCountVO);
			view.setViewName("/bugCount/bug_count_show");
			
			logger.info("�����շѷ�ҳ��ѯ�ɹ�:" + bugCountVO.toString());
			
		}
		catch (Exception e) {
			
			logger.error("�����շѷ�ҳ��ѯʧ��:" + bugCountVO.toString());
			throw e;
		}
		return view;
	}
	
}
