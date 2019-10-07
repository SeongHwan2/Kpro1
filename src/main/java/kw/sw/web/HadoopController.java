package kw.sw.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import kw.sw.web.hdfs.Hadoop;
import kw.sw.web.hdfs.Hadoop1;
import net.sf.json.JSONObject;

@Controller
public class HadoopController {

	@GetMapping("/analy")
	public String analy(HttpServletRequest req) {
		String fileName = req.getParameter("fileName");
		String nickName = req.getParameter("nickName");
		req.setAttribute("fileName", fileName);
		req.setAttribute("nickName", nickName);
		return "chart";
	}
	
	@PostMapping("/analy")
	public void analyPost(HttpServletRequest req, HttpServletResponse res, Model model) throws IOException {
//		Hadoop hadoop = new Hadoop();
		Hadoop1 hadoop1 = new Hadoop1();
		String fileName = req.getParameter("fileName");
		System.out.println(fileName);
		String nickName = req.getParameter("nickName");
		String index = req.getParameter("index");
//		System.out.println(index);
//		List<HashMap> result = hadoop.run(nickName, fileName);
		List<HashMap> result = hadoop1.run(nickName, fileName, index);
		System.out.println(result);
		
//		model.addAttribute("result", result);
//		req.setAttribute("fileName", fileName);
//		req.setAttribute("nickName", nickName);
		
		 JSONObject Jobj = new JSONObject(); 
		 Jobj.put("result", result);
		 
		 try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json; charset=UTF-8");
			res.getWriter().write(Jobj.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
}
