package kw.sw.web;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import kw.sw.web.beans.ListBean;
import kw.sw.web.beans.joinBean;
import kw.sw.web.dao.Dao;
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
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		return "web2Home";
	}
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home2(Locale locale, Model model) {
		return "home";
	}
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
	public String login(HttpServletRequest req, @Valid joinBean loBean, BindingResult result) {
		String msg = "";
		if(result.hasErrors()) {
			System.out.println("result has error!");
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error : errors) {
				System.out.println("error : " +  error.getDefaultMessage());
				msg = error.getDefaultMessage();
				req.setAttribute("msg", "값을 모두 입력해주세요");
//			 System.out.println(req.getAttribute("msg"));
			}
			return "login";
		}else {
			String id = loBean.getId();
			String pw = loBean.getPassword();
			System.out.println(loBean);
			System.out.println(id + " : " + pw);
			HashMap<String, Object> resultMap =  d.checkId(loBean);
			System.out.println(resultMap);
			if(resultMap != null) {
				String nick = resultMap.get("nickname").toString();
				HttpSession hs = req.getSession();
				hs.setAttribute("nick", nick);
				return "redirect:/home";
			}else {
				req.setAttribute("msg", "등록되지 않은 아이디 이거나, 아이디 또는 비밀번호를 잘못 입력 하셨습니다.");
			}
			return "login";
		}
	}
	
	@RequestMapping(value="/join", method=RequestMethod.GET)
	public String join() {
		System.out.println("get");
		return "join";
	}
	
	@RequestMapping(value="/join", method=RequestMethod.POST)
	public String join(HttpServletRequest req, @Valid joinBean loBean, BindingResult result) {
			System.out.println("Post");
			String msg = "";
			if(result.hasErrors()) {
				System.out.println("result has error!");
				List<ObjectError> errors = result.getAllErrors();
				for(ObjectError error : errors) {
//					System.out.println("error : " +  error.getDefaultMessage());
					msg = error.getDefaultMessage();
					req.setAttribute("msg", "값을 모두 입력해주세요");
//				 System.out.println(req.getAttribute("msg"));
				}
				return "join";
			}else {
				String id = loBean.getId();
				String pw = loBean.getPassword();
				String nick = loBean.getNickname();
				System.out.println(loBean);
				System.out.println(id + " : " + pw + " : " + nick);
				d.join(loBean);
				return "login";
			}
		
	}
	
	@RequestMapping(value="/idCheck", method=RequestMethod.POST)
	public void idCheck(joinBean loBean, HttpServletRequest req, HttpServletResponse res) {
		System.out.println(loBean);
		boolean check = false;
		HashMap<String, Object> result = d.checkId(loBean);
		System.out.println(result);
		if(result != null) {
			check = true;
		}
		JSONObject jobj = new JSONObject();
		jobj.put("check", check);
		
		try {
			res.setCharacterEncoding("UTF-8");
			res.setContentType("application/json; charset=UTF-8");
			res.getWriter().write(jobj.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value="/select", method=RequestMethod.POST)
	public void selectList(HttpServletRequest req, HttpServletResponse res) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String nick = req.getParameter("nick");
		System.out.println(nick);
		if(nick != "") {
			params.put("nick", nick);
			List<ListBean> resultList = ls.select(params);
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
		
		System.out.println("select 시작");
	}
	
	@RequestMapping("/create")
	public String create() {
		return "create";
	}
	
	@RequestMapping(value="/insert", method=RequestMethod.POST)
	public String insert(HttpServletRequest req , @RequestParam("file") MultipartFile[] files, HttpServletResponse res, ListBean lBean) {
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
					byte data1[] =  file.getBytes(); // getByte로 파일 내용 받기 >> byte로 넘어오기 때문에 byte패열에 저장
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
		return "redirect:/home";
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
		 try {
			 InputStream input = new FileInputStream(path); 
			 OutputStream output = res.getOutputStream(); 
			 IOUtils.copy(input, output);
			 res.setHeader("Content-Disposition", "attachment; filename=\"" + fileName +"\""); 
			 input.close(); 
			 output.close();
			 } catch (Exception e) {
				 e.printStackTrace(); 
		 }
		 
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession hs) {
		System.out.println(hs.getAttribute("nick"));
		hs.invalidate();
		return "redirect:/home";
	}
	
	@RequestMapping("/ck")
	public String ck() {
		return "write";
	}
}
