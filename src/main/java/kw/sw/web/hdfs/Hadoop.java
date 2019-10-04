package kw.sw.web.hdfs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class Hadoop {

	protected HashMap<String, Object> resultMap;
	// local 및 hadoop 설정 객체 선언
	protected Configuration localConf = null;
	protected Configuration hadoopConf = null;
	//local Path 정의
	protected String Local = "D:\\IDE\\workspace\\upload\\";
	protected String hdfsUrl = "hdfs://192.168.3.125:9000";
//	protected String hdfsUrl = "hdfs://192.168.3.240:9000"; //service server 주소
	// HADOOP 정제 대상 경로, 처리 경로 객체설정
	protected final String INPUT = "/input/";
	protected final String OUTPUT = "/output";
	protected final String TARGET = "/part-r-00000";
	protected Path inputPath = null;
	protected Path outputPath = null;
	protected FileSystem localSystem = null;
	protected FileSystem hadoopSystem = null;
	protected String Rlocal = "";
	
	/**************************************************
	 * >> 상태값 설정 << 
	 * 0 : 접속 오류 (Hadoop 연결 문제 발생)
	 * 1 : 정제 오류 (MapReduce 처리 문제 발생)
	 * 2 : 처리 완료 (전체 정상 처리)
	 **************************************************/
	
	public List<HashMap> run(String nickname, String fileName, String index) throws IOException {
		System.out.println("Hadoop" + index +  " Start!");
		List<HashMap> result = null;
		int status = 0;
		if(init(nickname, fileName)) {
			if(fileCopy(fileName)) {
				try {
					if(mapReduce(index)) {
						result = resultData();
						System.out.println("파일 정제 완료");
						status = 2;
					}
				} catch (Exception e) {
					e.printStackTrace();
					status = 1;
				}
			}
		}
//		System.out.println(init(nickname,fileName));
		return result;
		
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
		System.out.println("Hadoop init 결과 :" + status);
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
	
	//mapreduce 요청 메소드
	protected boolean mapReduce(String index) throws IOException, ClassNotFoundException, InterruptedException {
		boolean status = false;
		//시작 알림
		System.out.println("Hadoop mapReduce() >>> Start!");
		
		if(hadoopSystem.exists(new Path("/output"))) {
			hadoopSystem.delete(new Path("/output"), true);
		}
		//정제 작업 객체 생성
		Job job = Job.getInstance(hadoopConf, "test");
		//실행 대상 클래스 지정
		job.setJarByClass(Hadoop.class);
		//Mapper 객체 지정
		job.setMapperClass(Map.class);
		//Reducer 객체 지정
		job.setReducerClass(Reduce.class);
		//Mapper 객체 출력(키, value) 정의
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(IntWritable.class);
		//정제 결과 출력 (키, value) 정의
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		//작업시 생성될 테스크 정의
		job.setNumReduceTasks(1);
		
		//원본 및 대상 경로 정의
		FileInputFormat.addInputPath(job, inputPath);
		FileOutputFormat.setOutputPath(job, outputPath);
		//종료 알림
		System.out.println("Hadoop mapReduce() >> End");
//		status = true;
		return job.waitForCompletion(true);
//		job.waitForCompletion(true);
//		return status;
	}
	
	//정제 완료된 데이터 불러오기
	protected List<HashMap> resultData() throws IOException {
		System.out.println("Hadoop resultData() >>> Start!");
		List<HashMap> resultList = new ArrayList<HashMap>();
		//정제 결과 데이터 경로 생성
		Path targetPath = new Path(OUTPUT + TARGET);
		// 문자열에 결과 담기 위한 변수
		StringBuffer sb = new StringBuffer();
		if(hadoopSystem.exists(targetPath)) {
//			FSDataInputStream fsis = hadoopSystem.open(targetPath);
//			int byteRead = 0;
//			while((byteRead = fsis.read()) > 0) {
//				sb.append((char)byteRead);
//			}
			InputStream fsi = hadoopSystem.open(targetPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fsi));
			String str = "";
			boolean status = true;
			while((str = br.readLine()) != null) {
				HashMap<String, Object> Smap = new HashMap<String, Object>();
//				String temp="";
				String key[] = str.split(",");
//				sb.append(str + "\r\n");
				for(String b : key) {
					System.out.println(b);
//					if(status) {
//						temp=b;
//						status=false;
//					}else {
//						Smap.put(temp, b.trim());
//						status=true;
//					}
					if(status) {
//						System.out.println(b);
						Smap.put("key", b);
//						sb.append(b);
						status = false;
					}else {
						Smap.put("value", b.trim());
//						sb.append(" : " + b + "\\n");
						status = true;
					}
						
				}
				resultList.add(Smap);
			}
			br.close();
			fsi.close();
		}
//		System.out.println(resultList);
		System.out.println("Hadoop resultData() >>> End");
		return resultList;
	}
	

}
