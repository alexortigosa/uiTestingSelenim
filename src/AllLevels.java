
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;

import io.github.bonigarcia.wdm.WebDriverManager;


public class AllLevels {
	
	private static WebDriver driver = null;
	private By startButton = By.id("start_button");
	private By levelTitle=By.cssSelector("h1");
	private By input = By.id("input");
	private By nextButtonFilter = By.id("next");

	@BeforeAll
	public static void setUpBeforeClass() throws Exception {
		
  		WebDriverManager.chromedriver().setup();
  		ArrayList<String> optionsList = new ArrayList<String>();
		ChromeOptions chromeOptions = new ChromeOptions();
		optionsList.add("--start-maximized");
		optionsList.add("--incognito");
		optionsList.add("disable-notifications");
		chromeOptions.addArguments(optionsList);
		chromeOptions.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
  		chromeOptions.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
		
		
		driver = new ChromeDriver(chromeOptions);
		
	}

	@AfterAll
	public static void tearDownAfterClass() throws Exception {
		//Close browser
		driver.close();
	}

	@BeforeEach
	public void setUp() throws Exception {
	}

	@AfterEach
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws InterruptedException {
		
		//Open URL
		driver.get("http://pruebaselenium.serviciosdetesting.com/");
		WebElement levelTitleElement=driver.findElement(levelTitle);
		
		//Level 1
		assertEquals(levelTitleElement.getText(),"Práctica Selenium");
		WebElement startButtonElement=driver.findElement(startButton);
		startButtonElement.click();
		levelTitleElement=driver.findElement(levelTitle);
		assertEquals(levelTitleElement.getText(),"Level 2");
		//Useful to debug by waiting some miliseconds
		sleep();	
		//Level 2
		assertEquals(level2(),"Level 3");
		sleep();
		assertEquals(level3(),"Level 4");
		sleep();
		assertEquals(level4(),"Level 5");
		sleep();
		assertEquals(level5(),"Level 6");
		sleep();
		level6();
		level7();
		sleep();
		level8();
		sleep();
		assertEquals(level9(),"Level 10");
		sleep();
		assertEquals(level10(),"¡Enhorabuena! Has llegado al final de la práctica");
		sleep();	
	}
	
	private String level10() { 		
		WebElement From=driver.findElement(By.xpath("//*[@id=\"source\"]"));		
        WebElement To=driver.findElement(By.xpath("//*[@id=\"target\"]"));						
        Actions act=new Actions(driver);					
        act.dragAndDrop(From, To).build().perform();
        return getTitle(levelTitle);
	}
	
	private String level9() {
		String MainWindow=driver.getWindowHandle();					
        Set<String> s1=driver.getWindowHandles();
        Iterator<String> i1=s1.iterator();
        String password = "";
		
        while(i1.hasNext())			
        {		
            String ChildWindow=i1.next();		
            if(!MainWindow.equalsIgnoreCase(ChildWindow))			
            {    		
                    driver.switchTo().window(ChildWindow);	                                                                                                           
                    password = getTitle(By.xpath("//*[@id=\"pass\"]"));
                    driver.close();		
            }		
        }
        driver.switchTo().window(MainWindow);
        setInput(input,password);
		clickButton(nextButtonFilter);
		return getTitle(levelTitle);
	}
	
	private void level8() throws InterruptedException{
		Alert javascriptAlert = driver.switchTo().alert();
		javascriptAlert.sendKeys("9");
	    javascriptAlert.accept();
		
	}
	
	private void level7() throws InterruptedException{
		/**
		 * clicar enlace
		 */
		acceptDialog();
	}
	
	private void level6() throws InterruptedException{
		/**
		 * clicar enlace
		 */
		By filter = By.id("hidden\"");
		executeScript(filter,"arguments[0].style.display='block'");
		clickButton(filter);
	}
	
	private String level5() throws InterruptedException{
		/*
		 * clicar enlace
		 */
		clickButton(By.xpath("//*[@id=\"main\"]/div/div/div/span/table/tbody/tr/td/a"));
		return getTitle(levelTitle);
	}
	
	private String level4() throws InterruptedException{
		/*
		 * clicar boton 1
		 * clicar boton 2
		 * clicar boton 3
		 * clicar boton 4
		 */
		clickButton(By.xpath("//*[@id=\"main\"]/div/a[1]"));
		clickButton(By.xpath("//*[@id=\"main\"]/div/a[2]"));
		clickButton(By.xpath("//*[@id=\"main\"]/div/a[3]"));
		clickButton(By.xpath("//*[@id=\"main\"]/div/a[4]"));
		return getTitle(levelTitle);
		
	}
	
	private String level3() throws InterruptedException{
		/*
		 *  Recoger el texto de la etiqueta
		 *  Escribirlo en el inbput
		 *  clical el siguiente
		 */
		String labelText = getTitle(By.className("custom_dummy_label"));
		setInput(input,labelText);
		clickButton(nextButtonFilter);
		return getTitle(levelTitle);
	}
	
	private String level2() throws InterruptedException{
		/*
		 * Modificar el input
		 * Clicar en siguiente
		 * Verificar que hemos pasado de pantalla
		 */
		String expectedText = "selenium";
		setInput(input,expectedText);
		clickButton(nextButtonFilter);
		return getTitle(levelTitle);
			
	}
	
	private String getTitle(By filter) {
		WebElement h1Element = driver.findElement(filter);
		return h1Element.getText();
	}
	
	private void clickButton(By filter) {
		WebElement nextButton = driver.findElement(filter);
		nextButton.click();
	}
	
	private void setInput(By filter,String text) {
		WebElement inputText = driver.findElement(filter);
		inputText.sendKeys(text);
	}
	
	private void executeScript(By filter, String script) {
		WebElement element = driver.findElement(filter);
		((JavascriptExecutor)driver).executeScript(script, element);
	}
	
	private void acceptDialog() {
		Alert javascriptAlert = driver.switchTo().alert();
	    javascriptAlert.accept();
	}
	
	private void sleep() throws InterruptedException {
		Thread.sleep(2000);
	}
	

}
