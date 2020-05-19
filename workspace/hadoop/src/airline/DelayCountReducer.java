package airline;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class DelayCountReducer extends Reducer<Text, IntWritable, Text, IntWritable>{

	// 결과값 : 지연건수
	private IntWritable result = new IntWritable();
	
	// 계산결과를 합치는 로직 
	// key, list(value) : 2006.01 (1,1,1,1,......) -> 2006.01 10
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) 
			throws IOException, InterruptedException {

		int sum = 0;
		for(IntWritable value : values) {
			sum += value.get();
		}
		result.set(sum);
		context.write(key, result);
	}
	
}
