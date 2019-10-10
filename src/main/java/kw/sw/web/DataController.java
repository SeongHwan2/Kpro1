package kw.sw.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import kw.sw.web.service.DataService;
import net.sf.json.JSONObject;

@Controller
public class DataController {

	@Autowired
	DataService ds;
	
	@PostMapping("/dataSelect")
	public void dataSelect(HttpServletRequest req, HttpServletResponse res) {
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String,Object>>();
		String index = req.getParameter("index");
		result = ds.dataSelect(index);
		
		JSONObject jobj = new JSONObject();
		jobj.put("result", result);
		
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json; charset=UTF-8");
			res.getWriter().write(jobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@PostMapping("/tr")
	public void tr() {
		ds.truncate();
	}
}
