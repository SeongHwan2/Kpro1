package kw.sw.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kw.sw.web.Util.HttpUtil;
import kw.sw.web.beans.ListBean;
import kw.sw.web.dao.Dao;
import kw.sw.web.service.FileService;
import net.sf.json.JSONObject;

@Controller
public class Web2Controller {
	
	@Autowired
	Dao d;
	
	@Autowired
	FileService fs;
	
	@RequestMapping("/write")
	public String write() {
		return "write";
	}

	@RequestMapping("/loin")
	public void loin(HttpServletRequest req, HttpServletResponse res) {
		try {
			String url = "https://accounts.kakao.com/login?continue=";
			String url2 = "https://kauth.kakao.com/oauth/authorize";
			url2 += "?client_id=15d3f05a889119af54eb25ef333399df";
			url2 += "&redirect_uri=http://ysh.gudi.kr/KakaoBack";
//			url2 += "&redirect_uri=http://gdj16.gudi.kr:20012/KakaoBack";
//			url2 += "&redirect_uri=http://localhost:8080/KakaoBack"; //localhost >> 내부용
			url2 += "&response_type=code";
			url += URLEncoder.encode(url2, "UTF-8");
			System.out.println(url);
			res.sendRedirect(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/KakaoBack")
	public String KakaoBack(HttpServletRequest req, Model model) {
		HashMap<String, Object> resultMap = null;
		try {
			// 토큰 요청
			String url = "https://kauth.kakao.com/oauth/token";
			url += "?client_id=15d3f05a889119af54eb25ef333399df&redirect_uri=";
			url += URLEncoder.encode("http://ysh.gudi.kr/KakaoBack", "UTF-8");
//			url += URLEncoder.encode("http://gdj16.gudi.kr:20012/KakaoBack", "UTF-8");
//			url += URLEncoder.encode("http://localhost:8080/KakaoBack", "UTF-8"); //localhost >> 내부용
			url += "&code=" + req.getParameter("code");
			url += "&grant_type=authorization_code";
			resultMap = HttpUtil.getUrl(url);
//			System.out.println(resultMap);
			
			String access_token = resultMap.get("access_token").toString(); // 사용자 정보 요청
			String userUrl = "https://kapi.kakao.com/v2/user/me"; 
			userUrl +="?access_token=" + access_token; 
			resultMap = HttpUtil.getUrl(userUrl);
//			System.out.println(resultMap);
//			System.out.println(resultMap.get("properties"));
			JSONObject jobj = JSONObject.fromObject(resultMap.get("properties"));
//			jobj.put("pro", resultMap.get("properties"));
//			System.out.println(jobj.get("pro"));
//			System.out.println(jobj);
			System.out.println(jobj.get("nickname") + " " + jobj.get("profile_image"));
			String nick = jobj.get("nickname").toString();
			String proUrl = jobj.get("profile_image").toString();
			HttpSession hsession = req.getSession();
			hsession.setAttribute("nick", nick);
			hsession.setAttribute("imgUrl", proUrl);
			hsession.setAttribute("access_token", access_token);
//			model.addAttribute("access_token",access_token); 
//			model.addAttribute("pro", jobj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/";
	}
	
	@RequestMapping("/lout")
	public String logout(HttpServletRequest req, HttpServletResponse res, HttpSession hsession) throws IOException {
		String key = req.getParameter("key");
		String url = "https://kapi.kakao.com/v1/user/logout";
		HashMap<String, Object> resultMap = HttpUtil.getUrl2(url, key);
		System.out.println(resultMap);
		hsession.invalidate();
		return "redirect:/";
	}
	
	@RequestMapping("/create2")
	public String create2() {
		return "create2";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert2(HttpServletRequest req , @RequestParam("file") MultipartFile[] files, HttpServletResponse res, ListBean lBean) {
		fs.fileUpload(lBean, files);
		return "redirect:/";
	}
	
	@RequestMapping("/chart")
	public String chart() {
		return "chart";
	}
	

}
