package com.focus.base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import com.focus.listener.WebEventListener;
import com.focus.page.Page;
import com.focus.utility.OptionsManager;

public class BasePage extends Page {
	public Properties prop;
	public static WebDriver driver;
	public OptionsManager optionsManager;
	public static EventFiringWebDriver eventDriver;
	public static WebEventListener eventListener;

	// a base page constructor is created
	public BasePage(WebDriver driver) {
		super(driver);
	}

	// start the web driver
	public WebDriver initDriver(Properties prop) {
		String platformName = prop.getProperty("platform");
		String browserName = prop.getProperty("browser");
		if (platformName.equals("windows")) {
			if (browserName.equals("chrome")) {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.chrome.driver", prop.getProperty("windrivers")+"/chromedriver.exe");
				driver = new ChromeDriver(optionsManager.getChromeOptions());
			} else if (browserName.equals("firefox")) {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.gecko.driver", prop.getProperty("windrivers")+"/geckodriver.exe");
				driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			} else {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.ie.driver", prop.getProperty("windrivers")+"/IEDriverServer.exe");
				driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			}
		} else if (platformName.equals("linux")) {
			if (browserName.equals("chrome")) {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.chrome.driver", prop.getProperty("linuxdrivers")+"/chromedriver");
				driver = new ChromeDriver(optionsManager.getChromeOptions());
			} else if (browserName.equals("firefox")) {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.chrome.driver", prop.getProperty("linuxdrivers")+"/geckodriver");
				driver = new ChromeDriver(optionsManager.getChromeOptions());
			} else {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.ie.driver", prop.getProperty("linuxdrivers")+"/IEDriverServer");
				driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			}
		} else {
			if (browserName.equals("chrome")) {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.chrome.driver", prop.getProperty("macdrivers")+"/chromedriver");
				driver = new ChromeDriver(optionsManager.getChromeOptions());
			} else if (browserName.equals("firefox")) {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.chrome.driver", prop.getProperty("macdrivers")+"/geckodriver");
				driver = new ChromeDriver(optionsManager.getChromeOptions());
			} else {
				optionsManager = new OptionsManager(prop);
				System.setProperty("webdriver.ie.driver", prop.getProperty("macdrivers")+"/IEDriverServer");
				driver = new FirefoxDriver(optionsManager.getFirefoxOptions());
			}
		}

		eventDriver = new EventFiringWebDriver(driver);
		eventListener = new WebEventListener();
		eventDriver.register(eventListener);
		driver = eventDriver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(prop.getProperty("appurl"));
		return driver;
	}

	// get properties from properties file
	public Properties initProperties() {
		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream("./src/main/java/com/focus/config/config.properties");
			prop.load(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}