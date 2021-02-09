package logger;

import java.awt.Color;
import java.awt.Rectangle;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import UI.UIManager;

public class LogManager {

	static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");;
	static LocalDateTime today = null;
	static String logDate = "";
	
	
	
	public static void logError(String errorMsg) {
		today = LocalDateTime.now();
		logDate = formatter.format(today).toString() + " - ";
		UIManager.appendToPane(logDate, Color.BLACK);
		UIManager.appendToPane(errorMsg+ "\n", Color.RED);
		UIManager.logManager.scrollRectToVisible(new Rectangle(0,UIManager.logManager.getHeight(),1,20));
	}
	
	public static void logInfo(final String infoMsg) {
		today = LocalDateTime.now();
		logDate = formatter.format(today).toString() + " - ";
		UIManager.appendToPane(logDate, Color.RED);
		UIManager.appendToPane(infoMsg + "\n", new Color(6, 138, 6));
		UIManager.logManager.scrollRectToVisible(new Rectangle(0,UIManager.logManager.getHeight(),1,20));
	}
	
	public static void logAlert(final String alertMsg) {
		today = LocalDateTime.now();
		logDate = formatter.format(today).toString() + " - ";
		UIManager.appendToPane(alertMsg + "\n", Color.BLUE);
		UIManager.logManager.scrollRectToVisible(new Rectangle(0,(UIManager.logManager.getHeight())+5,1,100));
	}


	
}
