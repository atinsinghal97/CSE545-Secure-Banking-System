package business;



import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SysLogger{
	
	Logger logger;
		
	public SysLogger() {
		 logger = LoggerFactory.getLogger(SysLogger.class);
	}
	
	public void log(String message) throws IOException
	{
		System.out.println(message);
		logger.trace(message);
		FileWriter fileWriter = new FileWriter("C:\\Users\\iayus\\Desktop\\syslog.txt",true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(message);
		printWriter.close();
	}

	public void info(String string) {
		// TODO Auto-generated method stub
		logger.info("info: "+string);
	}
}