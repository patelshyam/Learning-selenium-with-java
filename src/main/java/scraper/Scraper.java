package scraper;

import java.io.IOException;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import UI.UIManager;
import base.ConfigrationManager;
import logger.LogManager;
import selenium.ChromeManager;

public class Scraper {
	private static WebDriver driver;
	private static ChromeManager chromeManager;
	
	public Scraper() {
		try {
			
			LogManager.logInfo("Start Scraper.....................................................[Success]");
			String osName = System.getProperty("os.name").toUpperCase();
			if(osName.contains("WINDOWS"))
			{
				System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			}
			else
			{	
				System.setProperty("webdriver.chrome.driver", "chromedriver");
			}
			// configurations
			ChromeOptions browserConfig = new ChromeOptions();
//			browserConfig.addArguments("--headless");
//			browserConfig.addArguments("--incognito");
			browserConfig.addArguments("--user-data-dir="+ConfigrationManager.getProperty("defaultUserPath"));
			browserConfig.setPageLoadStrategy(PageLoadStrategy.EAGER);
			driver = new ChromeDriver(browserConfig);
		
			chromeManager = new ChromeManager();

		} catch (Exception e) {
			LogManager.logError(e.toString());
			LogManager.logError("Error Loading Chrome Web Driver");
		}
	}
	
	public void startScraper() throws NumberFormatException, IOException
	{
		chromeManager.login(driver);
		shutDownScraper();
		shutDownChrome();
	}
	
	public void stopScrapper()
	{
		shutDownScraper();
		shutDownChrome();
	}
	
	public static void shutDownScraper() {
		// below statements will execute when thread status is set to false
		UIManager.setScraperThreadStatus(false);
		LogManager.logAlert("-------------------- Scraper Stopped. ------------------");
		UIManager.startBtn.setEnabled(true);
		UIManager.stopBtn.setEnabled(false);
	}

	public static void shutDownChrome() {
		try {
			driver.quit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
