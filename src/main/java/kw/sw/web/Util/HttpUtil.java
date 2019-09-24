package kw.sw.web.Util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import net.sf.json.JSONObject;

public class HttpUtil {

	public static HashMap<String, Object> getUrl(String apiUrl) {
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        try {
               URL u = new URL(apiUrl);
               HttpURLConnection conn = (HttpURLConnection) u.openConnection();
               conn.setRequestMethod("POST");
               int resCode = conn.getResponseCode(); // >> 요청 처리 코드  받기
               if(resCode == 200) {
                     InputStream input = conn.getInputStream();
                     InputStreamReader inputReader = new  InputStreamReader(input, "utf-8"); // input 리더로 input 읽어서 정보가져오기
                     BufferedReader br = new BufferedReader(inputReader);  // 우리가 알수있는 코드로 바꾸기 (inputStreamReader 값)
                     String line = "";
                     String result = "";
                     while((line = br.readLine()) != null) {
                            result += line;
                     }
                     System.out.println(result);
                     JSONObject jObj = JSONObject.fromObject(result);
                     //키값 받아오기
                     Iterator<?> iterator = jObj.keys();
                     while(iterator.hasNext()) {
                            String key = iterator.next().toString();
                            String value = jObj.getString(key);
                            resultMap.put(key, value);
                     }
                     input.close();
               }
        } catch (Exception e) {
               e.printStackTrace();
        }
               return resultMap;
        }
	
	public static HashMap<String, Object> getUrl2(String apiUrl, String token){
		HashMap<String, Object> resultMap = new HashMap<String, Object>();
		try {
			URL url = new URL(apiUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + token);
			int resCode = conn.getResponseCode();
			if(resCode == 200) {
				InputStream input = conn.getInputStream();
				InputStreamReader inputReader = new InputStreamReader(input, "utf-8");
				BufferedReader br = new BufferedReader(inputReader);
				String line = "";
				String result = "";
				while((line = br.readLine()) != null) {
					result += line;
				}
				JSONObject jObject = JSONObject.fromObject(result);
				Iterator<?> iterator = jObject.keys();
				while(iterator.hasNext()) {
					String key = iterator.next().toString();
					String value = jObject.getString(key);
					resultMap.put(key, value);
				}				
				input.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
