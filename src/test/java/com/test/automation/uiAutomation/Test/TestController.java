package com.test.automation.uiAutomation.Test;

import com.test.automation.uiAutomation.ExcelReader.*;
import com.test.automation.uiAutomation.ReportUtils.ReportUtil;
import com.test.automation.uiAutomation.Test.Keywords;
import com.test.automation.uiAutomation.Utils.Resources;
import com.test.automation.uiAutomation.Utils.TestUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestController extends Resources{

	@BeforeClass
	public void initBrowser() throws IOException {
		Initialize();
	}

	@Test
	public void TestCaseController() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, IOException {
		
		String startTime = TestUtils.now("dd.MMMM.yyyy hh.mm.ss aaa");
		ReportUtil.startTesting(System.getProperty("user.dir")+"//src//test//java//com//test//automation//uiAutomation//Reports//index.html", startTime, "Test", "1.5");
		ReportUtil.startSuite("Suite1");
		String TCStatus="Pass";
		
		// loop through the test cases
		for(int TC=2;TC<=SuiteData.getRowCount("TestCases");TC++) {			
			String TestCaseID = SuiteData.getCellData("TestCases", "TCID", TC);
			String RunMode = SuiteData.getCellData("TestCases", "RunMode", TC);
			
			if(RunMode.equals("Y")) {
				TCStatus="Pass";
				String TSStatus="Pass";
				
				
				int rows = TestStepData.getRowCount(TestCaseID);
				if(rows<2) { 
					rows=2;
				}
				
				// loop through test data
				for(int TD=2;TD<=rows;TD++ ) {
				
					// loop through the test steps
					System.out.println("SuiteData.getRowCount(TestCaseID)"+SuiteData.getRowCount(TestCaseID));
					
					switch(Config.getProperty("Browser"))
					{
					case "Chrome":
						System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"//drivers//chromedriver_v2-37.exe");
						driver = new ChromeDriver();
						break;
						
					case "IE":
						System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"//drivers//IEDriverServer.exe");
						driver = new InternetExplorerDriver();
						break;
						
					case "Firefox":
						System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//drivers//geckodriver.exe");
						driver = new FirefoxDriver();
					}
					
					//driver = new EventFiringWebDriver(dr);
					driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					driver.manage().window().maximize();
					
					for(int TS=2;TS<=SuiteData.getRowCount(TestCaseID);TS++) {
						keyword = SuiteData.getCellData(TestCaseID, "Keyword", TS);
						webElement = SuiteData.getCellData(TestCaseID, "WebElement", TS);
						ProceedOnFail = SuiteData.getCellData(TestCaseID, "ProceedOnFail", TS);
						TSID = SuiteData.getCellData(TestCaseID, "TSID", TS);
						Description = SuiteData.getCellData(TestCaseID, "Description", TS);						
						TestDataField = SuiteData.getCellData(TestCaseID, "TestDataField", TS);				
						TestData = TestStepData.getCellData(TestCaseID, TestDataField, TD);							
												
						Method method = Keywords.class.getMethod(keyword);	
						TSStatus = (String) method.invoke(method);
						
						if(TSStatus.contains("Failed")) {
							// take the screenshot
							//String filename = TestCaseID+"["+(TD-1)+"]"+TSID+"["+TestData+"]";
							String filename = TestCaseID+"["+(TD-1)+"]"+TSID;
							TestUtils.getScreenShot(filename);
							
							TCStatus=TSStatus;
							// report error
							ReportUtil.addKeyword(Description, keyword, TSStatus, "Screenshot/"+filename+".jpg");
							

							if(ProceedOnFail.equals("N")) {
								break;
							}
						} else {
							ReportUtil.addKeyword(Description, keyword, TSStatus, null);
						}
						
						/*
						if(ProceedOnFail.equals("N")) {
							break;
						}*/						
						
					}
					ReportUtil.addTestCase(TestCaseID, startTime, TestUtils.now("dd.MMMM.yyyy hh.mm.ss aaa"), TCStatus);
					//if (driver!=null)
					    driver.quit();
				}
			}else {
				// skip the test case
				ReportUtil.addTestCase(TestCaseID, startTime, TestUtils.now("dd.MMMM.yyyy hh.mm.ss aaa"), "Skipped");
				if (driver!=null)
				    driver.quit();
			}
		}

		ReportUtil.endSuite();
		ReportUtil.updateEndTime(TestUtils.now("dd.MMMM.yyyy hh.mm.ss aaa"));
		
	}
	
	
	@AfterClass
	public void quitBrowser() {
		System.out.println("In quitBrowser---------------------------");
		if (driver!=null)
		    driver.quit();
	}
}
