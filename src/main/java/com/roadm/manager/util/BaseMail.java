package com.roadm.manager.util;


import org.apache.log4j.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class BaseMail {

	private final Logger log = Logger.getLogger(getClass());
	private int count = 1;
	Transport transport = null;

	/**
	 * 发送邮件
	 * @param smtpServer
	 * @param name
	 * @param password
	 * @param meMail
	 * @param toMail
	 * @param mailTitle
	 * @param mailText
	 * @param fileName
	 */

	public BaseMail(String smtpServer, String name, String password, String meMail, String toMail, String mailTitle, String mailText, String fileName) {

		this.sendMail(smtpServer, name, password, meMail, toMail, mailTitle, mailText, fileName);

	}

	public boolean sendMail(String smtpServer, String name, String password, String meMail, String toMail, String mailTitle, String mailText, String fileName) {

		log.info("============================= > send Mail");
		long begin = System.currentTimeMillis();
		Properties props = System.getProperties();
		// 设置smtp服务器
		props.setProperty("mail.smtp.host", smtpServer);
		// 现在的大部分smpt都需要验证了
		props.put("mail.smtp.auth", "true");

		Session s = Session.getInstance(props);
		// 为了查看运行时的信息
		// s.setDebug(true);
		// 由邮件会话新建一个消息对象
		MimeMessage message = new MimeMessage(s);
		try {
			// 发件人
			InternetAddress from = new InternetAddress(meMail);
			message.setFrom(from);
			new InternetAddress();
			// 收件人
			InternetAddress[] to = InternetAddress.parse(toMail);
			message.setRecipients(Message.RecipientType.TO, to);
			// 邮件标题
			message.setSubject(mailTitle);
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			String charset = "GBK";
			messageBodyPart.setText(mailText + "[" + mailTitle + "]", charset);
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);
			messageBodyPart = new MimeBodyPart();
			if (null != fileName) {
				DataSource source = new FileDataSource(fileName);
				messageBodyPart.setDataHandler(new DataHandler(source));
				messageBodyPart.setFileName(fileName.substring(fileName.lastIndexOf("/") + 1));
				multipart.addBodyPart(messageBodyPart);
			}
			message.setContent(multipart);
			message.saveChanges();
			transport = s.getTransport("smtp");
			// smtp验证，就是你用来发邮件的邮箱用户名密码
			transport.connect(smtpServer, name, password);
			// 发送
			transport.sendMessage(message, message.getAllRecipients());
			log.info("-------------------------------- > 发送文件 : " + fileName);
			log.info("-------------------------------- > 发送成功 : " + toMail);
			log.info("本次耗费的时间:" + (System.currentTimeMillis() - begin) + "/毫秒");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			log.info("=========================== > error :" + e);
			log.info("-------------------------------- > 发送失败 : " + toMail	+ "----" + count + "次!");
			if (5 > count) {
				count++;
				this.sendMail(smtpServer, name, password, meMail, toMail, mailTitle, mailText, fileName);
			}
		} finally {
			try {
				transport.close();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

}
