package kw.sw.web.hdfs;

import java.io.IOException;
import java.util.HashMap;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.ibatis.session.SqlSession;

import kw.sw.web.mybatis.mybatisApp;


public class Reduce1 extends Reducer<Text, Text, Text, Text> {

	
	SqlSession session = mybatisApp.sqlSessionFactory.openSession();
	
	//출력값 변수 선언
	Text result = new Text();
	
	//value값 담을 변수 선언
	String v1 = "";
	String v2 = "";
	String[] values;
	@Override
	protected void reduce(Text key, Iterable<Text> value, Reducer<Text, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		// 결합변수 설정
//		int sum = 0;
//		
//		for(IntWritable v : value) {
//			sum += v.get();
//		};
		
//		result.set(sum);
		
		
		for(Text v : value) {
			HashMap<String, Object> map = new HashMap<String, Object>();
//			System.out.println(key + " : " + v.toString());
			values = v.toString().split(",");
			v1 = values[0].trim();
			v2 = values[1].trim();
			
			map.put("nickname", key.toString());
			map.put("date", v1);
			map.put("msg", v2);
			System.out.println(map.toString());
			session.insert("sql.dataInsert", map);
			session.commit();
			
			
//			result.set(v);
//			System.out.println(result);
//			System.out.println("닉네임 : " + key + " 시간 : " + v1 + " 대화내용 : " + v2);
			
		}
		
		
		
		
		
//		context.write(key, result);
	}
	
	
}
