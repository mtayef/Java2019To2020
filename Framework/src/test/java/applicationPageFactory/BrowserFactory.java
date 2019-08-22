package applicationPageFactory;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserFactory {


	// Setting up Browser using DesiredCapability. Hint: check DesiredCapability docs 
	public static WebDriver startApplication(String browser,String url) 
	{
		// Initialize driver object
		WebDriver driver = null;
		DesiredCapabilities cap = new DesiredCapabilities();
		cap.setJavascriptEnabled(true);
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
		
		if (browser.equalsIgnoreCase("Chrome")) 
		{
			
			System.setProperty("webdriver.chrome.driver", "./Browser/chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.merge(cap);
			driver=new ChromeDriver(options);
		}
		else if (browser.equalsIgnoreCase("ChromeHeadless")) 
		{
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			
			cap.setCapability(ChromeOptions.CAPABILITY, options);
			System.setProperty("webdriver.chrome.driver", "./Browser/chromedriver.exe");
			
			options.merge(cap);
			driver=new ChromeDriver(options);
		}
		else if (browser.equalsIgnoreCase("FirefoxHeadless")) 
		{
			FirefoxBinary binary = new FirefoxBinary();
			binary.addCommandLineOptions("--headless");
			System.setProperty("webdriver.gecko.driver", "./Browser/geckodriver.exe");
			FirefoxOptions options = new FirefoxOptions();
			options.setBinary(binary);
			cap.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
			options.merge(cap);
			driver = new FirefoxDriver(options);
			
		}
		else if(browser.equalsIgnoreCase("Firefox"))
		{
			FirefoxOptions options = new FirefoxOptions();
			System.setProperty("webdriver.gecko.driver", "./Browser/geckodriver.exe");
			options.merge(cap);
			driver=new FirefoxDriver(options);
		} 
		else if(browser.equalsIgnoreCase("IE"))
		{
			cap.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, true);
			System.setProperty("webdriver.ie.driver", "./Browser/IEDriverServer.exe");
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.merge(cap);
			driver=new InternetExplorerDriver(options);
		}
		else
		{
			System.err.println("ERROR: Sorry this test only support Chrome, Firefox and IE");
		}
		
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		
		// Complete all timing methods and return driver 
		return driver;
	}
	
	public static WebDriver startApplicationOnCloud(String browser, String url, String OSName, String version) {

		System.out.println("INFO: Setting up the browser on Cloud");

		WebDriver driver = null;

		DesiredCapabilities cap = new DesiredCapabilities();

		cap.setJavascriptEnabled(true);
		cap.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);

		cap.setCapability("os", OSName);
		cap.setCapability("os_version", version);

		final String USERNAME = "test15057";
		final String AUTOMATE_KEY = "1CmZWt4uLdB4VpdvhhgA";
		final String hubURL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";

		URL urlCloud = null;
		try 
		{
			urlCloud = new URL(hubURL);

		} catch (Exception e) {

		}

		
		System.out.println("User has passed Browser name "+browser);
		
		if (browser.equalsIgnoreCase("Chrome")) {

			cap.setCapability("browser", "Chrome");

			driver = new RemoteWebDriver(urlCloud, cap);

		}else if (browser.equalsIgnoreCase("Firefox"))
		{
			cap.setCapability("browser", "Firefox");
			
			driver = new RemoteWebDriver(urlCloud, cap);

		} else if (browser.equalsIgnoreCase("IE")) 
		{
			InternetExplorerOptions options = new InternetExplorerOptions();
			options.merge(cap);
			driver = new InternetExplorerDriver(options);
		} else
		{
			System.out.println("ERROR: Sorry This framework only support Chrome, FF, IE Browser");
		}

		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.get(url);
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		System.out.println("INFO: Browser and Application is set");

		return driver;
	}
}
