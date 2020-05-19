package airline;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class ArrivalDelayCount {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		// 입출력데이터의 경로 확인
		if(args.length !=2) {
			System.err.println("사용법 : airline.ArrivalDelayCount [input] [output]");
			System.exit(2);
		}
		
		// 1. Job속성설정
		Job job = Job.getInstance(conf, "ArrivalDelayCount");
		
		// 2. 입출력데이터경로 설정
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));

		// 3. Job class설정
		job.setJarByClass(ArrivalDelayCount.class);
		job.setMapperClass(ArrivalDelayCountMapper.class);
		job.setReducerClass(DelayCountReducer.class);
		
		// 4. 입출력데이터형식 설정
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		// 5. 출력키, 값의 데이터형식 설정
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		// 6. MapReduce를 실행
		job.waitForCompletion(true);
	}

}
