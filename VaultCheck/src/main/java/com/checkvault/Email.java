/**
 * 
 */
package com.checkvault;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.checkvault.utility.FileUtility;

public class Email {
	/**
	 * @throws IOException 
	 * @throws FileNotFoundException 
	* 
	 * 
	 */
	public static void sendEMailThroughOUTLOOK(String serverAddress) throws FileNotFoundException, IOException {
		System.out.println("Inside sendemail");
		try {

			Properties SmtpProps = new Properties();
			SmtpProps.load(new FileInputStream(FileUtility.getPath("smtpClient.properties")));		
			
			Session session = Session.getInstance(SmtpProps);

			MimeMessage msg = new MimeMessage(session);
			Transport transport = session.getTransport("smtp");
			transport.connect();

			Properties EmailProps = new Properties();
			EmailProps.load(new FileInputStream(FileUtility.getPath("emailList.properties")));
			
			msg.setFrom(new InternetAddress(EmailProps.getProperty("mailFrom")));

			String sendTo = EmailProps.getProperty("sendTo");

			String[] sendToStringList = sendTo.split(",");
			
			ArrayList<Address> sendToList = new ArrayList<Address>();
			
			for (int i = 0; i < sendToStringList.length; i++) {
				System.out.println("*******TO : " + sendToStringList[i].trim());
				sendToList.add(InternetAddress.parse(sendToStringList[i].trim())[0]) ;
			}
								
			msg.addRecipients(Message.RecipientType.TO, sendToList.toArray(new Address[sendToStringList.length]));
			

			String sendCC = EmailProps.getProperty("sendCC");

			String[] sendCCStringList = sendCC.split(",");

			ArrayList<Address> sendCCList = new ArrayList<Address>();
			
			for (int i = 0; i < sendCCStringList.length; i++) {
				System.out.println("*******CC : " + sendCCStringList[i].trim());
				sendCCList.add(InternetAddress.parse(sendCCStringList[i].trim())[0]) ;
			}
			
			msg.addRecipients(Message.RecipientType.CC, sendCCList.toArray(new Address[sendCCStringList.length]));
			
			String subject = "Alert Vault Server Sealed " + serverAddress;
			
			msg.setSubject(subject);
			msg.setSentDate(new Date());

			Multipart multipart = new MimeMultipart();

			MimeBodyPart htmlPart = new MimeBodyPart();

			String htmlContent = subject;

			htmlPart.setContent(htmlContent, "text/html");
			multipart.addBodyPart(htmlPart);

			msg.setContent(multipart);
			Transport.send(msg);

			System.out.println("Message sent successfully..................");
			
		} catch (MessagingException e) {
			System.out.println("Message sending encountered an Error.......");
			throw new RuntimeException(e);
		}

	}

	public static String getHtmlString(String serverAddress) {

		StringBuilder HtmlBody = new StringBuilder();

		try {
			HtmlBody.append("HellBoy Active on Server @ " + serverAddress);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		} finally {

		}
		return HtmlBody.toString();

	}

}
