package kw.sw.web;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import kw.sw.web.beans.ListBean;
import kw.sw.web.dao.Dao;
import kw.sw.web.service.FileService;
import kw.sw.web.service.ListService;
import net.sf.json.JSONObject;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	@Autowired
	Dao d;
	
	@Autowired
	ListService ls;
	
	@Autowired
	FileService fs;
	
	@RequestMapping("/")
	public String home(Locale locale, Model model) {
		return "web2Home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) {
		return "home";
	}
	
	@RequestMapping(value="/select", method=RequestMethod.POST)
	public void selectList(HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String nick = req.getParameter("nick");
		System.out.println(nick);
		if(nick != "") {
			System.out.println("select 시작");
			params.put("nick", nick);
			List<ListBean> resultList = ls.select(params);
//			System.out.println(resultList.size());
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("list", resultList);
			JSONObject jobj = JSONObject.fromObject(result);
			try {
				res.setCharacterEncoding("UTF-8");
				res.setContentType("application/json; charset=UTF-8");
				res.getWriter().write(jobj.toString());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	@RequestMapping("/ud")
	public void cud(HttpServletRequest req, HttpServletResponse res, ListBean lBean) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String type = req.getParameter("type");
		System.out.println(type);
		System.out.println(lBean.getNo());
		params.put("lbean", lBean);
		params.put("type", type);
		int status = ls.ud(params);
		JSONObject jobj = new JSONObject();
		jobj.put("status", status);
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json; charset=UTF-8");
			res.getWriter().write(jobj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/download", method=RequestMethod.POST)
	public void download(HttpServletRequest req, HttpServletResponse res) {
		String path = req.getParameter("url");
		String fileName = req.getParameter("fileName");
		System.out.println(path);
		fs.FileDown(path, fileName, res); 
	}

	@RequestMapping("/ck")
	public String ck() {
		return "write";
	}
}
