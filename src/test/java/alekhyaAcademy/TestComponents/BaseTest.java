package alekhyaAcademy.TestComponents;

import org.testng.annotations.Test; 

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.testng.annotations.Test;
import org.testng.annotations.Test;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import alekhyaAcademy.pageObjects.LandingPage;

public class BaseTest {

	public WebDriver driver;
	public LandingPage landingPage;

	@Test
	public WebDriver initializeDriver() throws IOException {
		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "//src//main//java//alekhyaAcademy//resources//GlobalData.properties");
		prop.load(fis);
		// String browsersName = "";
		//String browsersName = System.getProperty("browser")!=null ? System.getProperty("browser") : prop.getProperty("browser");
		String browsersName = prop.getProperty("browser");

		System.out.println(browsersName);
		if (browsersName.equalsIgnoreCase("chrome")) {
			System.setProperty("webDriver.chrome.driver", "/home/satyanarayana/Alekhya/Testing/chromedriver-v0.33.0-linux-aarch64er_linux64/");
			driver = new ChromeDriver();
		} else if (browsersName.equalsIgnoreCase("firefox")) {
			System.setProperty("webDriver.gecko.driver", "//home//satyanarayana//Alekhya//Testing//geckodriver-v0.33.0-linux-aarch64//");
		} else if (browsersName.equalsIgnoreCase("Edge")) {
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToMap(String filePath) throws IOException {
		// read json to string
		String jsonContent = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
		// string to HashMap jackson Databind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent,
				new TypeReference<List<HashMap<String, String>>>() {
				});
		return data;
	}

	public String getScreenshot(String testcaseName, WebDriver driver) throws IOException {
		 TakesScreenshot ts = (TakesScreenshot)driver;
		 File source = ts.getScreenshotAs(OutputType.FILE);
		 File file = new File(System.getProperty("user.dir")+"//reports//"+testcaseName+".png");
		 FileUtils.copyFile(source, file);
		 return System.getProperty("user.dir")+"//reports//"+testcaseName+".png";
	 }
	
	@BeforeMethod(alwaysRun = true)
	public LandingPage launchApplication() throws IOException {
		driver = initializeDriver();
		landingPage = new LandingPage(driver);
		landingPage.goTo();
		return landingPage;
	}

	@AfterMethod(alwaysRun = true)
	public void tearMethod() {
		driver.close();
	}
}
