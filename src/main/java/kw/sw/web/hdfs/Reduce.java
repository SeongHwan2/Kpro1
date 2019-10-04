package kw.sw.web.hdfs;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> {

	//출력값 변수 선언
	IntWritable result = new IntWritable();

	@Override
	protected void reduce(Text key, Iterable<IntWritable> value, Reducer<Text, IntWritable, Text, IntWritable>.Context context) throws IOException, InterruptedException {
		// 결합변수 설정
		int sum = 0;
		
		for(IntWritable v : value) {
			sum += v.get();
		};
		
		result.set(sum);
		System.out.println(result);
		
		context.write(key, result);
	}
	
	
}
