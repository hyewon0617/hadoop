package sort;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.SequenceFile;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.SequenceFileOutputFormat;

public class StringSort {

	public static void main(String[] args) throws Exception {
		
		Configuration conf = new Configuration();
		
		// 1. Job속성정의
		Job job = Job.getInstance(conf, "StringSort");
		job.setJarByClass(StringSort.class);
		
		// 2. Mapper와 Reducer를 정의
		//    1) Hadoop에서 제공하는 기본 mapper를 사용
		//	     -> 입력데이터가 그대로 출력데이터가 된다
		job.setMapperClass(Mapper.class);
		
		//    2) Hadoop에서 제공하는 기본 Reducer를 사용
		//       -> map의 출력 데이터가 그대로 reducer의 출력이 된다.
		//       -> reducer 단계에서 내부적으로 sort처리가 된다.
		job.setReducerClass(Reducer.class);
		
		// 3. map출력과 reduce의 출력의 key와 value의 데이터타입정의
		//    1) map의 출력
		job.setMapOutputKeyClass(Text.class);
		job.setMapOutputValueClass(Text.class);
		//    2) reduce의 출력
		job.setOutputKeyClass(Text.class);
		job.setOutputValueClass(Text.class);
		//    3) reduce의 출력수를 1로 설정(data 갯수가 줄어들지 않음)
		job.setNumReduceTasks(1);
		
		// 4. 입출력데이터형식지정
		job.setInputFormatClass(KeyValueTextInputFormat.class);
		job.setOutputFormatClass(SequenceFileOutputFormat.class);
		
		// 5. 입력데이터파일
		FileInputFormat.addInputPath(job, new Path(args[0]));
		
		// 6. 출력디렉토리지정 - 시퀀스파일형식으로
		SequenceFileOutputFormat.setOutputPath(job, new Path(args[1]));
		
		// 7. 블럭단위압축(통상적으로 60%사이즈 감소)
		SequenceFileOutputFormat.setOutputCompressionType(job, 
				SequenceFile.CompressionType.BLOCK);
		
		// 8. MapReduce
		job.waitForCompletion(true);
		
	}

}


























