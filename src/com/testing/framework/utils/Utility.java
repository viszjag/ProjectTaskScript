package com.testing.framework.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.testing.framework.base.TestBase;
public class Utility {

	//Take screen shot if the test fails.
	public static void takeScreenShot(WebDriver driver, String fileName) {
		TestBase.log.info("Capturing ScreenShot... ");
		File srcFile = ((TakesScreenshot) (driver))
				.getScreenshotAs(OutputType.FILE);
		try {
			String filepath=System.getProperty("user.dir")
					+ "/screenshots/" + fileName + ".jpg";
			TestBase.log.info("Screenshot path: "+filepath);
			FileUtils.copyFile(srcFile, new File(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
