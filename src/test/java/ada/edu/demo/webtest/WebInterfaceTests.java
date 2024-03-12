package ada.edu.demo.webtest;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class WebInterfaceTests {

	@Autowired
	private WebDriver webDriver;

	@LocalServerPort
	private int port;

	@Test
	@Order(1)
	@DisplayName("Create a user")
	public void CreateUser() {
		webDriver.get("http://localhost:"+port+"/student/new");

		WebElement studentIdInput = webDriver.findElement(By.id("studentId"));
		WebElement firstNameInput = webDriver.findElement(By.id("firstName"));
		WebElement lastNameInput = webDriver.findElement(By.id("lastName"));
		WebElement emailInput = webDriver.findElement(By.id("email"));

		// Check if such a field exists
		assertNotNull(firstNameInput);

		try {
			studentIdInput.sendKeys("2");
			Thread.sleep(2000);
			firstNameInput.sendKeys("Tural");
			Thread.sleep(2000);
			lastNameInput.sendKeys("Alizada");
			Thread.sleep(2000);
			emailInput.sendKeys("talizada12103@ada.edu.az");
			Thread.sleep(2000);
		}
		catch (Exception ex) {
			System.out.println(ex);
		}

		// Find and submit the form (assuming there's a submit button with a specific attribute)
		WebElement submitButton = webDriver.findElement(By.id("submit"));
		submitButton.click();
	}

	@Test
	@Order(2)
	@DisplayName("Check the created user")
	public void CheckUser() {
		// Check if the student is added
		webDriver.get("http://localhost:"+port+"/student/list");
		List<WebElement> bodyElementFName = webDriver.findElements(By.xpath("//*[contains(text(), 'Tural')]"));
		List<WebElement> bodyElementLName = webDriver.findElements(By.xpath("//*[contains(text(), 'Alizada')]"));
		System.out.println("Element result"+bodyElementLName);

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}

		// Check if the text "Tural" is present in the page content
		assert(bodyElementFName.size() == 1);
		assert(bodyElementLName.size() == 1);
	}
	@Test
	@Order(3)
	@DisplayName("Select all courses for student")
	public void SelectAllCoursesForStudent() {
		webDriver.get("http://localhost:"+port+"/student/new");

		WebElement studentIdInput = webDriver.findElement(By.id("studentId"));
		WebElement firstNameInput = webDriver.findElement(By.id("firstName"));
		WebElement lastNameInput = webDriver.findElement(By.id("lastName"));
		WebElement emailInput = webDriver.findElement(By.id("email"));

		// Check if such a field exists
		assertNotNull(firstNameInput);

		try {
			studentIdInput.sendKeys("3");
			Thread.sleep(2000);
			firstNameInput.sendKeys("Yumito");
			Thread.sleep(2000);
			lastNameInput.sendKeys("Karato");
			Thread.sleep(2000);
			emailInput.sendKeys("yumka@ada.edu.az");
			Thread.sleep(2000);
		}
		catch (Exception ex) {
			System.out.println(ex);
		}

		// Find and submit the form (assuming there's a submit button with a specific attribute)
		WebElement submitButton = webDriver.findElement(By.id("submit"));
		submitButton.click();

		// Redirect to the update page for student with id = 3
		webDriver.get("http://localhost:"+port+"/student/update?id=3");

		WebDriverWait wait = new WebDriverWait(webDriver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("courses")));

		// Find all the course checkboxes
		List<WebElement> courseCheckboxes = webDriver.findElements(By.name("courses"));

		// Check if checkboxes are found
		assertNotNull(courseCheckboxes);
		Assertions.assertFalse(courseCheckboxes.isEmpty(), "No course checkboxes found.");

		// Select all courses by clicking on each checkbox
		for (WebElement checkbox : courseCheckboxes) {
			if (!checkbox.isSelected()) {
				checkbox.click();
			}
		}

		// Submit the form, change to a more general approach in case the ID is not correct
		WebElement saveButton = webDriver.findElement(By.cssSelector("input[type='submit'],button[type='submit']"));
		assertNotNull(saveButton, "Save button not found.");
		saveButton.click();
	}
}