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
	
	Logger varLogger;
		
	public SysLogger() {
		 varLogger = LoggerFactory.getLogger(SysLogger.class);
	}
	
	public void log(String msg) throws IOException
	{
		System.out.println(msg);
		varLogger.trace(msg);
		FileWriter fileWriter = new FileWriter("C:\\Users\\iayus\\Desktop\\syslog.txt",true);
		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.println(msg);
		printWriter.close();
	}

	public void info(String string) {
		// TODO Auto-generated method stub
		varLogger.info("info: "+string);
	}
}