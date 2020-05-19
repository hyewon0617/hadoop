package count;

import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

// Reducer<InputKey, InputValue, OutputKey, OutputValue >
public class MyReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

	private IntWritable result = new IntWritable();
	
	//합치는 과정(reduce) : key, list -> key, value를 합치는 과정
	@Override
	protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

		int sum = 0;
		for(IntWritable value:values) {
			sum += value.get(); // 리스트를 합산하는 과정
		}
		result.set(sum);        // 출력값을 설정
		context.write(key, result); //read 1, a 2, book 2
	}
	

	
}
