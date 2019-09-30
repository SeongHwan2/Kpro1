package kw.sw.web.hdfs;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


//Mapper<(입력키<행번호> : 입력값<행의글자>) , (출력키<글자> : 출력값<1>)>
public class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
	//출력키 변수
	protected Text textKey = new Text();
	//출력 값 변수
	protected IntWritable intValue = new IntWritable();
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		String[] values = value.toString().split("]");
		
		//출력키에 넣을 문자열 변수 선언
		String strKey = values[0];
		System.out.println(strKey);
		//count 변수 선언
		int cnt = 0;
		//출력키에 문자열 변수 적용
		textKey.set(strKey);
		
		intValue = new IntWritable(cnt);
		
		
	}

	

	
	
}
