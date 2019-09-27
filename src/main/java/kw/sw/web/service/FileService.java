package kw.sw.web.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kw.sw.web.beans.ListBean;
import kw.sw.web.dao.Dao;

@Service
public class FileService {
	
	@Autowired
	Dao d;
	
	public void fileUpload(ListBean lBean, MultipartFile[] files) {
		
		System.out.println(lBean.toString());
		String fileName = "";
		String url = "";
		String ext = "";
		String originalfileName = "";
		String title = lBean.getTitle();
		String nickName = lBean.getNickName();
		
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
//					byte data1[] =  file.getBytes();// getByte로 파일 내용 받기 >> byte로 넘어오기 때문에 byte패열에 저장
					//text 구조 변경 시험
					InputStream data2 = file.getInputStream();
					BufferedReader br = new BufferedReader(new InputStreamReader(data2));
					StringBuffer sb = new StringBuffer();
					String line = "";
					HashMap<String, Object> nickMap = new HashMap<String, Object>(); 
					while((line = br.readLine()) != null) {
//						sb.append(line);
//						sb.append(",");
//						sb.append("\r");
//						String[] arr2 = line.split(" ");
//						System.out.println(arr2[i]);
						int a = line.indexOf("---------------");
						int b = line.indexOf("저장한");
						int c = line.indexOf("카카오톡 대화");
						if(a == -1 && b == -1 && c == -1) {
//							System.out.println(line);
							sb.append(line);
							sb.append("\r\n");							
//							an += line + "\r\n";
						}
					}
					br.close();
//					System.out.println(sb);
//					System.out.println(an);
					//저장경로 + 파일명 정의
//					String realPath = req.getSession().getServletContext().getRealPath("/"); // 프로젝트까지 위치
					String Path = "D:\\IDE\\workspace\\upload\\" + nickName + "\\"; // 작성자 / 메뉴 / 날짜 / 시간 / 파일명 등으로 관리할수있다 >> 디렉토리 관리
					url = "D:\\IDE\\workspace\\upload\\" + nickName + "\\" + fileName + ext;
					System.out.println(url);
//					req.setAttribute("url", url);
					// 파일 객체 생셩
					File f = new File(Path);
					// 디렉토리 없을경우 생성!!
					if(!f.isDirectory()) { //디렉토리 확인
						f.mkdirs();
					}
					
					//출력 객체 생성 + 파일 객체 넣기  (저장경로 + uuid + 확장자)
					FileWriter fw = new FileWriter(new File(Path + fileName + ext));
//					OutputStream os = new FileOutputStream(new File(Path + fileName + ext));
					//가져온 데이터 출력 객체에 넣기
					fw.write(sb.toString());
//					fw.write(an);
					fw.flush();
//					os.write(data1);
					//출력 객체 종료
					fw.close();
//					os.close();
				}
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		if(fileName != "") {
			System.out.println(ext);
			lBean.setFileName(originalfileName); //title + ext로 변경 예정!
			lBean.setFileUrl(url);
		}
		System.out.println(lBean.toString());
		System.out.println(fileName + " - " + url);
		d.insert(lBean);
	}
}
