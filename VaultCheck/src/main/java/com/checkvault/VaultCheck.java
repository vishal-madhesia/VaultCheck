package com.checkvault;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VaultCheck {

	private static ArrayList<String> arrList = new ArrayList<String>();

	public static void triggerNotify(String s, String msg) throws FileNotFoundException, IOException {
		Email.sendEMailThroughOUTLOOK(s,msg);
	}

	public static int showResult(WebDriver wd, String s) throws InterruptedException {

		wd.get("http://" + s);

		// Check if Status is Present on WebPage
		try {
			//wd.findElement(By.xpath("//div[@class=\"status-menu-label\"]"));
			Thread.sleep(15000);
			if (wd.getCurrentUrl().contains("/ui/vault/auth?with=token"))
				return 1;
			else if (wd.getCurrentUrl().contains("ui/vault/unseal")) {
				return -1;	
			}else
				return 0;
			
		} catch (NoSuchElementException ne) {
			
		}
		return 0;
	}

	public static ArrayList<String> serverListString2serverArrayList(String args[]) {

		// Convert Parameter List to Array List of Servers

		for (String s : args) {
			if (!(s.isEmpty()))
				VaultCheck.arrList.add(s);
		}
		return VaultCheck.arrList;
	}

	public static void main(String[] args) {

		SpringApplication.run(VaultCheck.class, args);

	}

}
