package main.core.data.file;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList; 

public class FFileReader{
	private String filePath;
	private String fileName;
	
	public FFileReader(String filePath,String fileName){
		this.filePath=filePath;
		this.fileName=fileName;
	}
	
	public ArrayList<String> read()throws Exception{
		BufferedReader buffer=new BufferedReader(new InputStreamReader(new FileInputStream(filePath+"/"+fileName)));
		ArrayList<String> result=new ArrayList<String>();
		String line=buffer.readLine();
		while(line!=null){
			line=line.trim();
			if(!line.isEmpty()){
				result.add(line);
			}
			line=buffer.readLine();
		}
		buffer.close();
		return result;
	}
}
