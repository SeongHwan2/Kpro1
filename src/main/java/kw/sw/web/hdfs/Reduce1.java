package kw.sw.web.hdfs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce1 extends Reducer<Text, Text, Text, IntWritable> {

	//출력값 변수 선언
	Text result = new Text();

	@Override
	protected void reduce(Text key, Iterable<Text> value, Reducer<Text, Text, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		// 결합변수 설정
//		int sum = 0;
//		
//		for(IntWritable v : value) {
//			sum += v.get();
//		};
		
//		result.set(sum);
		
		
		for(Text v : value) {
			result.set(v);
		}
		
		System.out.println(result);
		
//		context.write(key, result);
	}
	
	
}
