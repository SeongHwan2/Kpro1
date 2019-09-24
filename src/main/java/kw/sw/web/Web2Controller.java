package kw.sw.web;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

import kw.sw.web.beans.ListBean;
import kw.sw.web.dao.Dao;
import kw.sw.web.Util.HttpUtil;
import net.sf.json.JSONObject;

@Controller
public class Web2Controller {
	
	@Autowired
	Dao d;
	
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
			url2 += "&redirect_uri=http://gdj16.gudi.kr:20012/KakaoBack";
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
			url += URLEncoder.encode("http://gdj16.gudi.kr:20012/KakaoBack", "UTF-8");
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
	
	@RequestMapping(value="/insert2", method=RequestMethod.POST)
	public String insert2(HttpServletRequest req , @RequestParam("file") MultipartFile[] files, HttpServletResponse res, ListBean lBean) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		String fileName = "";
		String url = "";
		String ext = "";
		String originalfileName = "";
		if(files.length > 0) {
			//파일 업로드 부분!!
			try {
	//			req.setCharacterEncoding("UTF-8"); >> MultipartFile에 받아질시에 깨져서 들어오기때문에 req에 넣어줘도 필터가 되지 않음! 필터를 넣어줘야 spring이 알아서 처리해서 받아옴
				for(int i = 0; i < files.length; i++) {
					MultipartFile file = files[i];
					System.out.println(file.getOriginalFilename());
					//원본파일명 생성(test.txt)
					originalfileName = file.getOriginalFilename();
					// 파일 확장자 생성 (.txt)
					ext = originalfileName.substring(originalfileName.lastIndexOf("."), originalfileName.length());
					//고유한 파일명 생성(UUID)
					fileName = UUID.randomUUID().toString(); //고유 파일명 만들기
					// 프로젝트 경로 받기
	//				System.out.println(req.getContextPath()); // 화면에서만 사용하면 좋다!!
	//				System.out.println(req.getSession().getServletContext().getRealPath("/")); //file처리시 유용
					
					//데이터 가져오기
					byte data1[] =  file.getBytes();// getByte로 파일 내용 받기 >> byte로 넘어오기 때문에 byte패열에 저장
					//text 구조 변경 시험
					InputStream data2 = file.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(data2));
					StringBuffer sb = new StringBuffer();
					String line = "";
					//저장경로 + 파일명 정의
	//				String realPath = req.getSession().getServletContext().getRealPath("/"); // 프로젝트까지 위치
					String Path = "D:\\IDE\\workspace\\upload\\"; // 작성자 / 메뉴 / 날짜 / 시간 / 파일명 등으로 관리할수있다 >> 디렉토리 관리
					url = "D:\\IDE\\workspace\\upload\\" + fileName + ext;
					System.out.println(url);
					req.setAttribute("url", url);
					// 파일 객체 생셩
					File f = new File(Path);
					// 디렉토리 없을경우 생성!!
					if(!f.isDirectory()) { //디렉토리 확인
						f.mkdirs();
					}
					
					//출력 객체 생성 + 파일 객체 넣기  (저장경로 + uuid + 확장자)
					OutputStream os = new FileOutputStream(new File(Path + fileName + ext));
					//가져온 데이터 출력 객체에 넣기
					os.write(data1);
					//출력 객체 종료
					os.close();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		if(fileName != "") {
			lBean.setFileName(originalfileName);
			lBean.setFileUrl(url);
		}
		System.out.println(fileName + " - " + url);
//		System.out.println(req.getAttribute("url"));
//		System.out.println(params.toString());
		d.insert(lBean);
		return "redirect:/home2";
	}
	
	@RequestMapping("/chart")
	public String chart() {
		return "chart";
	}

}
