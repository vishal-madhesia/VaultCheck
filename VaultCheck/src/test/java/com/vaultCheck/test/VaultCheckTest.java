package com.vaultCheck.test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
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
import com.checkvault.utility.DateAndTime;
import com.checkvault.utility.FileUtility;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VaultCheckTest {

	private WebDriver wd;
	private DateFormat dateFormat;

	@DataProvider(name = "data-provider")
	public String[] dataProviderMethod() {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(FileUtility.getPath("VaultServerList.properties")));
			String serverAddressasString[] = prop.getProperty("VaultServerList").split(",");
			String serverAddress[] = new String[serverAddressasString.length];
			System.out.println("Server List Check");
			for (int i = 0; i < serverAddressasString.length; i++) {
				serverAddress[i] = (new StringBuffer(serverAddressasString[i].trim())).toString();
				System.out.println("     " + serverAddress[i]);
			}
			System.out.println("\n");
			return serverAddress;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {

		}
		return null;

	}

	@BeforeClass
	public void initWebDriver() {
		dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		System.setProperty("webdriver.chrome.driver", ((FileUtility.getPath("chromedriver.exe")).toString()));
		wd = new ChromeDriver();
		wd.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}

	@Test(dataProvider = "data-provider")
	public void checkIndividualVaults(String s) {
		Date StartTime = new Date();
		Date EndTime;
		try {
			if (VaultCheck.showResult(this.wd, s) > 0) {
				System.out.println(s + " : Not Sealed");
				EndTime = new Date();
				System.out.println("\t\tExecution Time = " + dateFormat.format(StartTime) + " - "
						+ dateFormat.format(EndTime) + " = " + DateAndTime.dateDiff(StartTime, EndTime));
				Assert.assertTrue(true);
			} else if (VaultCheck.showResult(this.wd, s) < 0) {
				System.out.println(s + " : Sealed");
				VaultCheck.triggerNotify(s, " Vault Server Sealed");
				EndTime = new Date();
				System.out.println("\t\tExecution Time = " + dateFormat.format(StartTime) + " - "
						+ dateFormat.format(EndTime) + " = " + DateAndTime.dateDiff(StartTime, EndTime));
				Assert.assertFalse(true);
			} else {
				System.out.println(s + " : Unreachable");
				VaultCheck.triggerNotify(s, " Vault Server Unreachable");
				EndTime = new Date();
				System.out.println("\t\tExecution Time = " + dateFormat.format(StartTime) + " - "
						+ dateFormat.format(EndTime) + " = " + DateAndTime.dateDiff(StartTime, EndTime));
				Assert.assertFalse((s + " : Unreachable"), true);
				;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public void closeWebDriver() {
		this.wd.close();
	}

}
