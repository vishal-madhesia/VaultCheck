/**
 * 
 */
package com.checkvault.utility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * @author vmadhe01
 *
 */
public class FileUtility {
	
	public static boolean filePresentAtRoot(String fileName) {
		String projectRoot = System.getProperty("user.dir");
		System.out.println(projectRoot);
		projectRoot = projectRoot.substring(0, projectRoot.lastIndexOf("\\"));
		System.out.println(projectRoot);
		FileReader fr = null;
		try {
			fr = new FileReader(new File(projectRoot + "\\" + fileName));
			fr.close();
			
		}catch(FileNotFoundException fnfe) {
			//fnfe.printStackTrace();
			return false;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally {
			
		}
		return true;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stu
		System.out.println(FileUtility.filePresentAtRoot("emailList.properties"));
	}

}
