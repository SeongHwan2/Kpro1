package kw.sw.web.hdfs;

import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;


//Mapper<(입력키<행번호> : 입력값<행의글자>) , (출력키<글자> : 출력값<글자>)>
public class Map1 extends Mapper<LongWritable, Text, Text, Text> {
	//출력키 변수
	protected Text textKey = new Text();
	//출력 값 변수
	protected Text textValue = new Text();
	
	
	@Override
	protected void map(LongWritable key, Text value, Mapper<LongWritable, Text, Text, Text>.Context context) throws IOException, InterruptedException {
		
//		System.out.println(value.toString());
		//문자열 자르기
		
		String[] values = value.toString().split("]");
		String strKey = "";
		String strCom = "";
		String strVal1 = "";
		String strVal2 = "";
		String strValC = "";
		int length = 0;
		if(values.length > 2) {
			//출력키에 넣을 문자열 변수 선언
			strKey = values[0];
			//출력 value 문자열 변수 선언 & 정의
			strVal1 = values[1];
			strValC = "";
			strVal2 = values[2];
			length = strKey.length();
		} else {
			return;
		}
		//count 변수 선언
		int cnt = 0;
		//출력키에 문자열 변수 적용
		if((strKey.indexOf("[") > -1) && (length > 0)) {
			strCom = strKey.replace("[", "");
			//출력값 변수에 문자열 변수 적용 (키)
			textKey.set(strCom);	
		}
		
		if (strVal1.indexOf("[") > -1) {
			strValC = strVal1.replace("[", "");
			textValue.set(strValC + "," + strVal2.trim());
		}
		System.out.println(textKey + " " + textValue);
		
		context.write(textKey, textValue);
		
	}

	

	
	
}
