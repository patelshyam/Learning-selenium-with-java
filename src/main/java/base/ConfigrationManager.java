package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ConfigrationManager {

	String result = "";
	static String propFileName = "src/main/resources/config.properties";
	 
	
	public static String getProperty(String name) throws IOException
	{
		
		FileReader reader=new FileReader(new File(propFileName)); 
		Properties prop = new Properties();
		prop.load(reader);
		
		return prop.getProperty(name);
		
	}
	
}
