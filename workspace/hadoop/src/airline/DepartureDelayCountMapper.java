package airline;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class DepartureDelayCountMapper 
	extends Mapper<LongWritable, Text, Text, IntWritable>{

	// map출력키
	private Text outputkey = new Text();
	
	// map출력값
	private final static IntWritable outputvalue = new IntWritable(1);

	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		AirlinePerformanceParser parser = new AirlinePerformanceParser(value);
		
		// 출력키 설정
		outputkey.set(parser.getYear()+ "," + parser.getMonth());
		
		// 출력값 : 지연시간이 0보다 크면 출력데이터생성
		if(parser.getDepartureDelayTime() > 0) {
			context.write(outputkey, outputvalue);
		}
	}
	
}
