package test;

import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class AddTableDataInCSVFile {
	
	public static void generateCSVFile(List<List<String>> data) {
		
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy-HH-mm-ss");
        String formattedDate = dateFormat.format(currentDate);

        String fileName = "./CSVFiles/" + formattedDate + ".csv";

        try {
            FileWriter writer = new FileWriter(fileName);
            for (List<String> row : data) {
                writer.append(String.join(",", row));
                writer.append("\n");
            }
            writer.flush();
            writer.close();
            System.out.println("CSV file created: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		WebDriver driver = new EdgeDriver();
		
		driver.get("https://the-internet.herokuapp.com/challenging_dom");
		
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
		
		List<List<String>> data = new ArrayList<>();
		
		ArrayList<String> headers = new ArrayList<>(); 
		
		List<WebElement> headerElements = driver.findElements(By.xpath("//table//tr//th"));
		
		for(WebElement h : headerElements) {
			headers.add(h.getText());
		}
		
		data.add(headers);
			
		int rowSize = driver.findElements(By.xpath("//table//tbody//tr")).size();
		
		for(int i =1;i<=rowSize;i++) {
			List<WebElement> row = driver.findElements(By.xpath("//table//tbody//tr["+i+"]//td"));
			List<String> allData = new ArrayList<>();
			for(WebElement r1 : row) {
				allData.add(r1.getText());
			}
			data.add(allData);
		}
		generateCSVFile(data);
		
		driver.quit();
	}
}
