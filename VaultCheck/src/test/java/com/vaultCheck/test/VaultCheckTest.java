package com.vaultCheck.test;

import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.checkvault.VaultCheck;
import com.checkvault.utility.FileUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VaultCheckTest {

	private WebDriver wd;
	
	@DataProvider(name = "data-provider")
    public String[] dataProviderMethod() {
		return new String[] { "ATL20DS6MNOVT01:8200", "ATL20DS6MNOVT02:8200", "ATL20DS1MNOVT01:8200", "ATL20DS1MNOVT02:8200", "10.0.21.90:8200" };    }
	
	@BeforeClass
	public void initWebDriver() {
		
		System.setProperty("webdriver.chrome.driver", 
				((FileUtility.getPath("chromedriver.exe")).toString()));
		wd = new ChromeDriver();
		wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	@Test(dataProvider = "data-provider")
	public void checkIndividualVaults(String s) {
		try {
			if(VaultCheck.showResult(this.wd,s)) {
				System.out.println(s + " : Not Sealed");
				Assert.assertTrue(true);
			}
			else {
				System.out.println(s + " : Sealed");
				VaultCheck.triggerNotify(s);
				Assert.assertFalse(true);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@AfterClass
	public void closeWebDriver() {
		this.wd.close();
	}

}
