package com.hrious.store.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

public class MailUtils {
	
	private static final String ACCOUNT = "admin@hrious.com";
	private static final String PASSWORD = "admin";
	private static final String URL = "http://www.hrious.com:8080/store/user?method=active&code=";
	
	private MailUtils() {}
	
	public static int sendMail(String email, String msg) { 
		// 1_创建程序与邮件服务器会话对象
		Session session = Session.getInstance(new Properties(), new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// 设置发送人的账号和密码
				return new PasswordAuthentication(ACCOUNT, PASSWORD);
			}
			
		});
		// 2_创建文件内容
		MimeMessage message = new MimeMessage(session);
		
		try {
			// 设置发送者IP
			message.setFrom(new InternetAddress(ACCOUNT));
			
			// 设置发送方式和接受者
			message.setRecipient(RecipientType.TO, new InternetAddress(email));
			
			// 设置邮件主题
			message.setSubject("用户激活");
			
			String newUrl = URL + msg,
				   content = "<h1>XXX网上购物欢迎你!激活请点击以下链接!</h1><h3><a href='"+newUrl+"'>"+newUrl+"</a></h3>";
			
			// 设置邮件内容
			message.setContent(content, "text/html;charset=utf-8");
			
			// 3_发送邮件
			Transport.send(message);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}
}
