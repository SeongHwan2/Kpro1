package kw.sw.web.hdfs;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Hadoop {

	protected HashMap<String, Object> resultMap;
	// local 및 hadoop 설정 객체 선언
	protected Configuration localConf = null;
	protected Configuration hadoopConf = null;
	
	protected String local = "D:\\IDE\\workspace\\upload\\";
	protected String hdfsUrl = "";
	
	protected FileSystem localSystem = null;
	protected FileSystem hadoopSystem = null;
	public void run(String nickname, String fileName) throws IOException {
		System.out.println("Hadoop Start!");
		localConf = new Configuration();
		//넘어온 nickname, fileName 확인
		System.out.println(nickname + "-----" + fileName);
		
		
		localSystem = FileSystem.getLocal(localConf);
		// 해당 사용자의 폴더 안에 저장된 파일 목록 확인!
		FileStatus[] fileList = localSystem.listStatus(new Path(local + nickname + "\\"));
		for(int i = 0; i < fileList.length; i++) {
			System.out.println(fileList[i].getPath().getName());
		}
		//localSystem 종료
//		localSystem.close();
		
	}
}
