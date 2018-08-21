package com.test.automation.uiAutomation.uiActions;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class Test 
{
	static WebDriver driver;
	
	public static void main(String args[]) throws InterruptedException
	{
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver_v2-37.exe");
		driver = new ChromeDriver();
		driver.get("https://test.wshcloud.com");
		driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.findElement(By.id("userName")).sendKeys("IPMN\\Admin1");
		driver.findElement(By.id("password")).sendKeys("Test");
		driver.findElement(By.id("login")).click();
		Thread.sleep(15000);
		driver.findElement(By.xpath("//*[@id='TopLevelMenu']//a[contains(@href,'Financials')]")).click();
		driver.findElement(By.xpath("//a[contains(@href,'Billing')]")).click();
		Thread.sleep(5000);
		WebElement Source = driver.findElement(By.xpath("//div[@data-widget='DailyVisitsAndTotalBillsWidget']//div[@class='wsc-widgetTitleText']"));
		//WebElement Target = driver.findElement(By.xpath("//div[@data-widget='BillLagandSubmissionlagWidget']//div[@class='wsc-widgetTitleText']"));
		Actions act = new Actions(driver);
		act.dragAndDropBy(Source, 150, 200).build().perform();
		Thread.sleep(10000);
	}
}
