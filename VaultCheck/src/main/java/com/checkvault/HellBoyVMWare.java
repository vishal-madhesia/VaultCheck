/**
 * 
 */
package com.checkvault;

import java.io.File;

/**
 * @author vmadhe01
 *
 */
public class HellBoyVMWare {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println((((Thread.currentThread().getContextClassLoader()).getResource("chromedriver.exe")).toString()).substring(6));
		
		
		System.out.println(System.getProperty("user.dir"));
	}

}
