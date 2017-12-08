package com.testing.framework.tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.testing.framework.base.TestBase;
import com.testing.framework.pages.Login;


public class OpenProjectTasksTest extends TestBase {

	Login login=new Login(driver);

	@BeforeTest
	public void login(){
		driver.manage().deleteAllCookies();
		login.login(config.getProperty("userEmail"), config.getProperty("userPassword"));
	}


	@Test
	public void testScenario() throws IOException{

		String splitBy = ",";
		BufferedReader br = new BufferedReader(new FileReader(System.getProperty("user.dir") + File.separator
				+ "config" + File.separator + config.getProperty("fileName")));
		String line = br.readLine();
		while ((line = br.readLine()) !=null) {
			String[] b = line.split(splitBy);
			log.debug("Opening task tab for Project with id: "+b[0]);
			login.openProjectTaskTab(b[0]);
		}
		br.close();
	}

}
