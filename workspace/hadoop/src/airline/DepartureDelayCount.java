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



public class DepartureDelayCount {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		if(args.length !=2) {
			System.err.println("사용법 : airline.DepartureDelayCount [input] [output]");
			System.exit(2);
		} 
		
		// 1. Job속성
		Job job = Job.getInstance(conf, "DepartureDelayCount");
		
		// 2. 입력파일경로 (master서버의 입력경로)
		FileInputFormat.addInputPath(job, new Path(args[0]));
		
		// 3. 출력파일경로(HDFS의 출력경로)
		FileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// 4. Job속성 : java class(main, mapper, reducer)정의
		job.setJarByClass(DepartureDelayCount.class);			// main class
		job.setMapperClass(DepartureDelayCountMapper.class);	// mapper class
		job.setReducerClass(DelayCountReducer.class); 			// reducer class
		
		// 5. Job속성 : 입출력데이터형 설정
		job.setInputFormatClass(TextInputFormat.class);         // csv파일의 데이타
		job.setOutputFormatClass(TextOutputFormat.class);		// mapper의 결과 데이터
		job.setOutputKeyClass(Text.class);                      // reducer의 key : 2006.01
		job.setOutputValueClass(IntWritable.class);             // reducer의 값  : 지연건수
			
		// 6. hdfs job을 실행
		job.waitForCompletion(true);
		
	}
}