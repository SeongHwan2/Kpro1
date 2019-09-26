package kw.sw.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kw.sw.web.hdfs.Hadoop;

@Controller
public class HadoopController {

	@GetMapping("/analy")
	public String analy() {
		return "chart";
	}
	
	@PostMapping("/analy")
	public String analyPost(HttpServletRequest req) {
		Hadoop hadoop = new Hadoop();
		String fileName = req.getParameter("fileName");
		System.out.println(fileName);
		hadoop.run();
		return "chart";
	}
}
