package main.core.data.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList; 

public class FFileWriter{
	private BufferedWriter buffer;
	
	public FFileWriter(String filePath,String fileName)throws Exception{
		File directory=new File(filePath);
		if(!directory.exists()){
			directory.mkdirs();
        }
		File file=new File(filePath+"/"+fileName);
		file.createNewFile();
		buffer=new BufferedWriter(new FileWriter(file));
	}
	
	public void write(String line)throws Exception{
		buffer.write(line);
		buffer.write("\r\n");
	}
	
	public void write(ArrayList<String> content)throws Exception{
		for(String line:content){
			this.write(line);
		}
	}
	
	public void close()throws Exception{
		buffer.flush();
		buffer.close();
	}
}
