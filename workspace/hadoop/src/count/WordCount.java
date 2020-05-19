package count;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

public class WordCount {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		if(args.length != 2) {
			System.err.println("사용법 : WordCount [input] [output]");
			System.exit(0);
		}
		
		// job의 속성을 정의
		Job job = Job.getInstance(conf, "WordCount"); 	// 실행할 Job이름
		job.setJarByClass(WordCount.class); 			// job class 이름
		job.setMapperClass(MyMapper.class); 			// mapper class 이름
		job.setReducerClass(MyReducer.class); 			// reducer class 이름
		
		// 1. 입출력데이터형식지정	
		job.setInputFormatClass(TextInputFormat.class);
		job.setOutputFormatClass(TextOutputFormat.class);
		
		// 2. 출력키, 값 데이터형식지정
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(IntWritable.class);
		
		// 3. 입력파일, 출력디렉토리를 지정
		FileInputFormat.addInputPath(job, new Path(args[0]));
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// 4. Hadoop 분석작업실행
		job.waitForCompletion(true);			
	}
}


































