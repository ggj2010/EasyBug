package com.gaoguangjin.baseinfo.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.framework.base.BaseController;
import com.gaoguangjin.baseinfo.entity.BiUserInfo;
import com.gaoguangjin.util.Util;

@Controller
@RequestMapping("/")
public class BiMainController extends BaseController {
	
	/**
	 * 浏览器兼容性判断
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/browserUpdate")
	public ModelAndView browserUpdate(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String str = request.getParameter("browseMessage");// 获取浏览器版本信息
		String[] str1 = str.split(",");
		String browseName = str1[0];// 获取浏览器的名字
		if (browseName.equals("IEBrowse")) {
			mav.addObject("browseName", browseName);
		}
		else {
			mav.addObject("browseName", "unknown");
		}
		mav.setViewName("/browserUpdate");
		return mav;
	}
	
	@RequestMapping(value = "/wait")
	public ModelAndView waitJsp() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/wait");
		return mav;
	}
	
	@RequestMapping(value = "/getImage")
	public void vcode(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0L);
		response.setContentType("image/jpeg");
		try {
			int width = 60;
			int height = 20;
			BufferedImage image = new BufferedImage(width, height, 1);
			Graphics g = image.getGraphics();
			Random random = new Random();
			g.setColor(new Color(255, 255, 255));
			g.fillRect(0, 0, width, height);
			g.setFont(new Font("Times New Roman", 0, 18));
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}
			
			String sRand = Util.getRandomString(4, 1);
			g.setColor(new Color(0, 0, 0));
			String validationcode = "";
			for (int i = 0; i < 4; i++) {
				String str = sRand.substring(i, i + 1);
				if (Math.random() >= 0.5D)
					str = str.toLowerCase();
				g.drawString(str, 13 * i + 6, 16);
				validationcode = validationcode + str;
			}
			HttpSession session = request.getSession();
			g.dispose();
			ImageIO.write(image, "JPEG", response.getOutputStream());
			session.setAttribute("code", validationcode);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}
	
	/**
	 * 功能：返回上一级
	 * @param HttpServletRequest request
	 * @return 返回String
	 */
	@RequestMapping("/main/back")
	public String back(HttpServletRequest request) throws Exception {
		String targetUrl = null;
		try {
			targetUrl = getSessionListUrl(request);
			return "redirect:" + targetUrl;
		}
		catch (Exception e) {
			
			throw e;
		}
	}
	
	/**
	 * 功能：返回上一级
	 * @param HttpServletRequest request
	 * @return 返回String
	 */
	@RequestMapping("/main/back2")
	public String back2(HttpServletRequest request) throws Exception {
		String targetUrl = null;
		try {
			targetUrl = getSessionListUrl(request);
			targetUrl = getSessionListUrl(request);
			return "redirect:" + targetUrl;
		}
		catch (Exception e) {
			
			throw e;
		}
	}
	
	@RequestMapping(value = "/getSubmit", method = { RequestMethod.GET, RequestMethod.POST })
	public String testImages(BiUserInfo base, HttpServletRequest request) {
		System.out.println(base.getMemo());
		request.setAttribute("memo", base.getMemo());
		return "forward:" + "/show.jsp";
		
	}
}
