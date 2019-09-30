package kw.sw.web.hdfs;

import java.io.IOException;
import java.util.HashMap;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class Hadoop {

	protected HashMap<String, Object> resultMap;
	// local 및 hadoop 설정 객체 선언
	protected Configuration localConf = null;
	protected Configuration hadoopConf = null;
	//local Path 정의
	protected String Local = "D:\\IDE\\workspace\\upload\\";
	protected String hdfsUrl = "hdfs://192.168.3.125:9000";
	// HADOOP 정제 대상 경로, 처리 경로 객체설정
	protected final String INPUT = "/input/";
	protected final String OUTPUT = "/output";
	protected final String TARGET = "/part-r-00000";
	protected Path inputPath = null;
	protected Path outputPath = null;
	
	protected FileSystem localSystem = null;
	protected FileSystem hadoopSystem = null;
	protected String Rlocal = "";
	public void run(String nickname, String fileName) throws IOException {
		System.out.println("Hadoop Start!");
		
		System.out.println(init(nickname, fileName));
		System.out.println(fileCopy(fileName));
	}
	
	protected boolean init(String nickname, String fileName) {
		System.out.println("hadoop init() >> start");
		boolean status = true;
		//넘어온 nickname, fileName 확인
		System.out.println(nickname + "-----" + fileName);
		Rlocal = Local + nickname + "\\";
		try {
			localConf = new Configuration();
			hadoopConf = new Configuration();
			//hadoop hdfs 주소 설정
			hadoopConf.set("fs.defaultFS", hdfsUrl);
			
			//경로 정의 
			inputPath = new Path(INPUT + nickname + "/" +  fileName);
			outputPath = new Path(OUTPUT);
			
			//HADOOP 정제시 사용할 경로 정의
			localSystem = FileSystem.getLocal(localConf);
			hadoopSystem = FileSystem.get(hadoopConf);
			/*
			if(hadoopSystem.exists(new Path("/output"))) {
				hadoopSystem.delete(new Path("/output"), true);
			}
			*/
			FileStatus[] fileList = localSystem.listStatus(new Path(Rlocal));
			// 해당 사용자의 폴더 안에 저장된 파일 목록 확인!
			for(int i = 0; i < fileList.length; i++) {
				System.out.println(fileList[i].getPath().getName());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = false;
		}
		System.out.println("hadoop init() >>> End");
		return status;
	}
	
	//분석 대상 파일 Hadoop에 저장
	protected boolean fileCopy(String fileName) {
		System.out.println("hadoop fileCopy() >>> start!");
		boolean status = true;
		try {
			Path filePath = new Path(Rlocal + fileName);
			System.out.println(filePath);
			FSDataInputStream fsis = localSystem.open(filePath);
			FSDataOutputStream fsos = hadoopSystem.create(inputPath);
			
			int byteRead = 0;
			while((byteRead = fsis.read()) > 0) {
				fsos.write(byteRead);
			}
			fsis.close();
			fsos.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
		}
		System.out.println("hadoop fileCopy() >>> End");
		System.out.println("fileCopy 결과 : " + status);
		return status;
	}
	

}
