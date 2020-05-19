package airline;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class ArrivalDelayCountMapper 
		extends Mapper<LongWritable, Text, Text, IntWritable>{
	
	// map 출력 : key와 vaule
	private Text outputkey = new Text();
	private final static IntWritable ouputValue = new IntWritable(1);
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {
		
		AirlinePerformanceParser parser = new AirlinePerformanceParser(value);
		
		// 출력키
		outputkey.set(parser.getYear() + "," + parser.getMonth());
		
		// 출력값 : 지연시간이 0보다 크면 출력데이터를 생성
		if(parser.getArriveDelayTime() > 0) {
			context.write(outputkey, ouputValue);
		}
	}

}