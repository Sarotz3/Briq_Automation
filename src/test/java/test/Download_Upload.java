package test;

import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class Download_Upload {

	public static void main(String[] args) throws InterruptedException {

		WebDriver driver = new EdgeDriver();

		driver.get("https://the-internet.herokuapp.com/download");

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));

		List<WebElement> allFileLinks = driver.findElements(By.xpath("//div[@id='content']//div//child::a"));

		Set<String> files = new LinkedHashSet<>();
		
		for(WebElement link : allFileLinks) {

			String text = link.getText();
			if(!text.toLowerCase().contains("png")) {
				files.add(text);
				link.click();	
			}
		}
		
		String userDirectory = System.getProperty("user.dir");
		int usernameIndex = userDirectory.lastIndexOf(System.getProperty("user.name"));

		String desiredPath = userDirectory.substring(0, usernameIndex + System.getProperty("user.name").length());

		driver.get("https://the-internet.herokuapp.com/upload");
		
		
		
		try {
			for(String f: files) {
				WebElement upload = driver.findElement(By.xpath("(//input[@type='file'])[2]"));
				Thread.sleep(1000);
				upload.sendKeys(desiredPath+"\\Downloads\\"+f);
			}
		} catch (Exception e) {
		
		}
		
		Thread.sleep(2000);
		driver.quit();
		
	}
}
