package kw.sw.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import kw.sw.web.hdfs.Hadoop;

@Controller
public class HadoopController {

	@RequestMapping("/analy")
	public String analy() {
		Hadoop hadoop = new Hadoop();
		hadoop.run();
		return "chart";
	}
}
