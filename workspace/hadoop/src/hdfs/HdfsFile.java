package hdfs;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HdfsFile {

	public static void main(String[] args) {
		
		// java HdfsFile [filename] [contents]
		
		if(args.length != 2) { // 명령행에 매개변수가 2개가 아니면
			
			System.err.println("사용방법 : HdfsFile [filename] [contents]");
			System.exit(0);
		}
		
		
		try {
			
			Configuration conf = new Configuration();
			FileSystem hdfs = FileSystem.get(conf); 	// Hadoop 파일시스템을 제어하는 객체
			Path path = new Path(args[0]); 				// 입력경로
			
			if(hdfs.exists(path)) {       				// 입력경로가 존재한다면
				hdfs.delete(path, true); 				// HDFS파일을 삭제
			}
			
			FSDataOutputStream os = hdfs.create(path); 	// HDFS에 파일을 생성
 			os.writeUTF(args[1]);                    	// HDFS파일에 내용을 저장
 			os.close();
 			
 			FSDataInputStream is = hdfs.open(path);		// HDFS파일을 Open
 			String inputString = is.readUTF();			// HDFS파일을 Read
 			is.close();
			System.out.println("Read Data : " + inputString);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
	}
}