package com.testing.framework.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.testing.framework.base.PageBase;
import com.testing.framework.base.TestBase;

public class Login extends PageBase {

	//Webelement Locators
	public By username=By.id("userName");
	public By nextButton=By.xpath("//*[@id='login']//button[@type='submit']");
	public By password=By.xpath("//input[@type='password']");
	public By signInButton=By.xpath("//button[.='Sign in']");

	public By homePageBanner=By.xpath("/div[@class='home-banner']");
	
	/*public By projectTab = By.xpath("//a[.='Projects']");
	public By dealRoom = By.xpath("//*[contains(@id,'shortName')]//span[.='Name']");*/
	public By taskGrid = By.xpath("//*[@id='table-container']");



	public Login(WebDriver driver) {
		super(driver);
	}



	public boolean login(String userName,String password){
		elementExists(this.username).clear();
		elementExists(this.username).sendKeys(userName);
		elementExists(nextButton).click();
		TestBase.waitInSeconds(5);
		elementExists(this.password).sendKeys(password);
		elementExists(signInButton).click();
		if(elementExists(homePageBanner)!=null)
			return true;
		else
			return false;
	}
	
	public boolean openProjectTaskTab(String projectId){
		driver.get(TestBase.config.getProperty("url")+"/index.html#projects/"+projectId+"/tasks");
		TestBase.waitInSeconds(5);
		if(elementExists(taskGrid)!=null){
			log.debug("Tasks tab loaded for Project with id: "+projectId);
			return true;
		}
		else
			return false;
	}

}