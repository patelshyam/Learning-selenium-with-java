package selenium;

import logger.LogManager;
import scraper.Scraper;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import UI.UIManager;
import base.ConfigrationManager;
import base.KidsInfo;
import io.ExcelOperation;



public class ChromeManager {


	//Login => Take List From Excel => Process List One By One => End

	String msg = null;
	String videos[] = null;
	String photo[] = null;
	String defaultMobile = null;
	int sleep = 0;
	
	public boolean login(WebDriver driver) throws NumberFormatException, IOException {

		boolean isSuccessfull = false;
		sleep = Integer.parseInt(ConfigrationManager.getProperty("sleep"));
		try {
			
			msg = UIManager.msgField.getText();
			defaultMobile = UIManager.textMobile.getText();
			if(msg.contains("\n"))
			{
			msg = msg.replace("\n", Keys.chord(Keys.SHIFT,Keys.ENTER));
			}
			// Look For search panel in code - Which mens user have logged in successfully to the main page
			
			driver.get("https://web.whatsapp.com/send?phone="+defaultMobile+"&text&source&data&app_absent");
			WebElement canvas = new WebDriverWait(driver, 60).until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@id,\"pane-side\")]")));

			
			LogManager.logInfo("LoggedIn Successfully");
			
			processData(driver);
			
			

		} catch (Exception e) {
			e.printStackTrace();
			LogManager.logAlert("Taken too much time for logged In");
			Scraper.shutDownScraper();
			Scraper.shutDownChrome();
		}

		return isSuccessfull;

	}
	
	public void processData(WebDriver driver) throws InterruptedException
	{
		
		List<KidsInfo> data = ExcelOperation.getKidsInfos();
	
		for(KidsInfo k: data)
		{
			try {
				String msgCopy = msg;
			
				WebElement textBox = driver.findElement(By.xpath("//footer//div[contains(@class,\"copyable-text selectable-text\")]"));
				textBox.sendKeys("http://wa.me/" + k.getMobileno() + "\n");
				Thread.sleep(sleep);
				
				String xpathForLink = "//a[contains(@href,'http://wa.me/"+k.getMobileno()+"')]";
				WebElement linkText = driver.findElement(By.xpath( xpathForLink));
				linkText.click();
				Thread.sleep(sleep);
				String invalidStringXpath = "//div[contains(text(),\"Phone number shared via url is invalid.\")]";
				boolean linkvalidityCheck = false;
				
				try {
					WebElement invalidStringCheck = driver.findElement(By.xpath(invalidStringXpath));
					linkvalidityCheck = false;
					String okayButtonXpath = "//div[contains(text(),\"OK\")]";
					Thread.sleep(sleep);
					
					WebElement okayButton = driver.findElement(By.xpath(okayButtonXpath));
					okayButton.click();
					LogManager.logAlert(k.getMobileno() + " Is not valid");
				}
				catch(Exception ex){
					linkvalidityCheck = true;
				}
				
				if(linkvalidityCheck)
				{
					textBox = driver.findElement(By.xpath("//footer//div[contains(@class,\"copyable-text selectable-text\")]"));
					msgCopy = msgCopy.replace("[pname]", k.getParentName());
					msgCopy = msgCopy.replace("[kname]", k.getKidName());
					msgCopy = msgCopy.replace("[url]", k.getUrl());
					textBox.sendKeys(msgCopy + "\n");
					Thread.sleep(sleep);
					
					putImages(driver);
					putVideo(driver);
					Thread.sleep(sleep);
					LogManager.logInfo("To " + k.getMobileno() + " Send Successfully");
				}
				
			
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}finally {
				Thread.sleep(sleep);
				String formatDefaultNumber = formatMobile(defaultMobile);
				String xpathForDefaultLink = "//span[contains(@title,'"+formatDefaultNumber+"')]";
				WebElement primaryContact = driver.findElement(By.xpath(xpathForDefaultLink));
				primaryContact.click();
				Thread.sleep(sleep);
			}
		}
	
	}
	
	public void putVideo(WebDriver driver)
	{
		String videos[] = null;
		String videoPath = UIManager.videoPath.getText();
		if(videoPath.length()>1)
		{
			
				WebElement textBox = driver.findElement(By.xpath("//footer//div[contains(@class,\"copyable-text selectable-text\")]"));
				textBox.sendKeys(videoPath + "\n");
		}
	}
	
	public void putImages(WebDriver driver)
	{
		String images[] = null;
		String imagePath = UIManager.picturesPath.getText();
			if(imagePath.length() > 1)
			{
			if(imagePath.contains(","))
			{
				images = imagePath.split(",");
				for(String i:images)
				{
					attachDoc(i,driver);
				}
			}
			else
			{
				attachDoc(imagePath,driver);
			}
		}
	}
	
	public String formatMobile(String mobile)
	{
		long phoneFmt = Long.parseLong(mobile);
		//get a 12 digits String, filling with left '0' (on the prefix)   
		DecimalFormat phoneDecimalFmt = new DecimalFormat("0000000000");
		String phoneRawString= phoneDecimalFmt.format(phoneFmt);

		java.text.MessageFormat phoneMsgFmt=new java.text.MessageFormat("+{0} {1} {2}");
		    //suposing a grouping of 3-3-4
		String[] phoneNumArr={phoneRawString.substring(0, 2),
		          phoneRawString.substring(2,7),
		          phoneRawString.substring(7,12)};

		return phoneMsgFmt.format(phoneNumArr);
	}

	public void attachDoc(String Path,WebDriver driver)
	{
		WebElement attachment = driver.findElement(By.xpath("//div[@title=\"Attach\"]"));
		attachment.click();
		
		WebElement imageBox = driver.findElement(By.xpath("//input[@accept=\"image/*,video/mp4,video/3gpp,video/quicktime\"]"));
		imageBox.sendKeys(Path);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebElement sendButton = driver.findElement(By.xpath("//span[@data-testid=\"send\"]"));
		sendButton.click();
		
	}
}
