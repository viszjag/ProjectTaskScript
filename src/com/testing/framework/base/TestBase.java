package com.testing.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import com.testing.framework.utils.Utility;


public abstract class TestBase {
	public static WebDriver driver = null;
	public static Logger log=null;
	public static Properties config = null;
	public static Properties data = null;
	protected static Connection conn = null;


	protected TestBase() {
		initLog();
		initConfig();
		initData();
		initDriver();
	}

	//To get Console log and file log. 
	private void initLog() {
		if (log == null) {
			PropertyConfigurator.configure("config/log4j.properties");
			log = Logger.getLogger("MercatusLogger");
			log.debug("Initialized log file successfully!!");

			// to suppress warning message related to log4j initialization in system
			System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
			log.debug("-------------------------------------------------");
		}
	}

	//Initialize all property files.

	private static void initConfig() {
		if (config == null) {
			config = new Properties();
			FileInputStream ip = null;

			String fileName = "config.properties";//property to save config related data
			try {
				String path = System.getProperty("user.dir") + File.separator
						+ "config" + File.separator + fileName;
				ip = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				config.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void initData() {
		if (data == null) {
			data = new Properties();
			FileInputStream ip = null;

			String fileName = "data.properties";//property to save test data related to testcases
			try {
				String path = System.getProperty("user.dir") + File.separator
						+ "config" + File.separator + fileName;
				ip = new FileInputStream(path);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			try {
				data.load(ip);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void initDriver() {//select browser and create webdriver instance for same
		if (driver == null) {
			String os=System.getProperty("os.name");
			log.info("Operating System: "+os);
			log.info("Browser = " + config.getProperty("browser"));
			if (config.getProperty("browser").equalsIgnoreCase("firefox")) {

				if(config.getProperty("operatingSystem").contains("windows")){
					try {//kill any driver instances if running
						Runtime.getRuntime().exec("taskkill /F /IM geckodriver.exe");//important, don't delete this
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				}else if(config.getProperty("operatingSystem").equalsIgnoreCase("unix")){
					System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\drivers\\geckodriver");
				}
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE,"/dev/null");//to suppress browser logs
				driver = new FirefoxDriver();

			} else if (config.getProperty("browser").equalsIgnoreCase("chrome")) {
				if(config.getProperty("operatingSystem").contains("windows")){
					try {//kill any driver instances if running
						Runtime.getRuntime().exec("taskkill /F /IM chromedriver.exe");//important, don't delete this
					} catch (IOException e) {
						e.printStackTrace();
					}
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
				}else if(config.getProperty("operatingSystem").equalsIgnoreCase("unix")){
					System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver");
				}

				// To remove message "You are using an unsupported command-line flag: --ignore-certificate-errors.
				//                    Stability and security will suffer."
				// Added an argument 'test-type'
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				ChromeOptions options = new ChromeOptions();
				options.addArguments("test-type");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new ChromeDriver(capabilities);
			}
			driver.manage().window().maximize();
			log.info("URL = "+config.getProperty("url"));
			driver.get(config.getProperty("url"));
			driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			log.debug("-------------------------------------------------");
		}
	}

	public static void quitDriver() {
		driver.quit();
		driver = null;
		log.info("Closing the Browser!!");
		log.info("Execution log file is generated at "+System.getProperty("user.dir")+File.separator +"Application.log");
	}

	@AfterMethod
	public void getResult(ITestResult result) {
		log.debug("Method Name: " + result.getMethod().getMethodName());
		log.debug("Success Status: " + result.isSuccess());

		if (!result.isSuccess()) {
			Utility.takeScreenShot(driver, result.getMethod().getMethodName());
		}
	}

	@AfterSuite
	public void quit() {
		quitDriver();//quit driver after all suites are over
	}

	public static void waitInSeconds(int seconds){
		long time=seconds*1000;
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
