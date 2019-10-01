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

@Controller
public class HadoopController {

	@GetMapping("/analy")
	public String analy() {
		return "chart";
	}
	
	@PostMapping("/analy")
	public String analyPost(HttpServletRequest req, HttpServletResponse res, Model model) throws IOException {
		Hadoop hadoop = new Hadoop();
		String fileName = req.getParameter("fileName");
		System.out.println(fileName);
		String nickName = req.getParameter("nickName");
		List<HashMap> result = hadoop.run(nickName, fileName);
		System.out.println(result);
		
//		model.addAttribute("result", result);
		req.setAttribute("result", result);
		
//		 JSONObject Jobj = new JSONObject(); 
//		 Jobj.put("result", result);
//		 res.getWriter().write(Jobj.toString());
		 
		return "chart";
	}
}
