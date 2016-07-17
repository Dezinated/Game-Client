package utils;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {

	public static String loadAsString(String file){
		StringBuilder result = new StringBuilder();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String buffer = "";
			while((buffer = br.readLine()) != null){
				result.append(buffer + '\n');
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return result.toString();
	}
	
}
