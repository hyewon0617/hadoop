package count;

import java.io.IOException;
import java.util.StringTokenizer;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

// Mapper<InputKey, InputValue, OutKey, OutValue>
public class MyMapper extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	// 출력값
	private final static IntWritable one = new IntWritable(1); // 숫자 1(고정값) only
	private Text word = new Text(); // 출력키
	// 1, read a book
	// read, 1
	// a, 1
	// book, 1
	
	@Override
	protected void map(LongWritable key, Text value, Context context)
			throws IOException, InterruptedException {

		StringTokenizer st = new StringTokenizer(value.toString());
		while(st.hasMoreTokens()) {
			word.set(st.nextToken());  // 출력키
			context.write(word, one);  // 출력데이터 : read, 1
		}
	}

}