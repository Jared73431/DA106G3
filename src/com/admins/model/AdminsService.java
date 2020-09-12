package com.admins.model;

import java.io.File;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class AdminsService {
	private AdminsDAO_interface dao;
	
	public AdminsService() {
		dao = new AdminsDAO();
	}
	
	public AdminsVO addAdmin(String admin_account,String admin_password,String admin_name,byte[] admin_photo,Integer admin_status) {
		AdminsVO adminsVO = new AdminsVO();
		
		adminsVO.setAdmin_account(admin_account);
		adminsVO.setAdmin_password(admin_password);
		adminsVO.setAdmin_name(admin_name);
		adminsVO.setAdmin_photo(admin_photo);
		adminsVO.setAdmin_status(admin_status);
		
		dao.insert(adminsVO);
		
		return adminsVO;
	}
	
	public AdminsVO updateAdmin(String admin_no,String admin_account,String admin_password,String admin_name,byte[] admin_photo,Integer admin_status) {
		AdminsVO adminsVO = new AdminsVO();
		
		adminsVO.setAdmin_no(admin_no);
		adminsVO.setAdmin_account(admin_account);
		adminsVO.setAdmin_password(admin_password);
		adminsVO.setAdmin_name(admin_name);
		adminsVO.setAdmin_photo(admin_photo);
		adminsVO.setAdmin_status(admin_status);
		
		dao.update(adminsVO);
		
		return adminsVO;
		
	}
	
	public void delete(String admin_no) {
		dao.delete(admin_no);
	}
	
	public AdminsVO getOneAdmin(String admin_no) {
		return dao.findByPrimaryKey(admin_no);
	}
	
	public List<AdminsVO> getAll(){
		return dao.getAll();
	}
	
	public byte[] getPicture(String admin_no) {
		return dao.getPicture(admin_no);
	}
	
	public int checkAccount(String admin_account) {
		return dao.checkAccount(admin_account);
	}
	
	public void sendMail(String to, String subject, String messageText) {
		
		   try {
			   // 設定使用SSL連線至 Gmail smtp Server
			   Properties props = new Properties();
			   props.put("mail.smtp.host", "smtp.gmail.com");
			   props.put("mail.smtp.socketFactory.port", "465");
			   props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
			   props.put("mail.smtp.auth", "true");
			   props.put("mail.smtp.port", "465");

	       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
	       // ●須將myGmail的【安全性較低的應用程式存取權】打開
		     final String myGmail = "bunny0083@gmail.com";
		     final String myGmail_password = "qhhmfkarokaixiij";
			   Session session = Session.getInstance(props, new Authenticator() {
				   protected PasswordAuthentication getPasswordAuthentication() {
					   return new PasswordAuthentication(myGmail, myGmail_password);
				   }
			   });

			   Message message = new MimeMessage(session);
			   message.setFrom(new InternetAddress(myGmail));
			   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
			  
			   //設定信中的主旨  
			   message.setSubject(subject);
			   //設定信中的內容 
			   message.setText(messageText);

			   Transport.send(message);
			   System.out.println("傳送成功!");
	     }catch (MessagingException e){
		     System.out.println("傳送失敗!");
		     e.printStackTrace();
	     }
	   }

}
