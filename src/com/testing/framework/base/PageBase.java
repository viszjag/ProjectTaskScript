package com.testing.framework.base;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public abstract class PageBase {

	protected WebDriver driver = null;
	private WebDriverWait wait =null;
	public static org.apache.log4j.Logger log=TestBase.log;

	public PageBase(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Wait until WebElement is visible on page(DOM)
	 * 
	 * @param locator
	 *               locator used to find the element
	 * @return WebElement,
	 *               the WebElement once it is located and visible else return null, if locator is not visible or timedout
	 */
	//wait for web element until it is visible.
	public WebElement elementExists(By locator){
		wait = new WebDriverWait(driver, 2);
		try {
			wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return driver.findElement(locator);  
		}catch(ElementNotVisibleException e){
			return null;
		} catch (TimeoutException e) {
			return null;
		}
	}


}
