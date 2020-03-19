package com.test;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class TestSendMail
{
	public static void main( String [] args )
	{
		JavaMailSenderImpl clsMail = new JavaMailSenderImpl();
		clsMail.setHost( "test.com" );
		clsMail.setPort( 587 );
		clsMail.setUsername( "user_id" );
		clsMail.setPassword( "user_password" );
		
		SimpleMailMessage clsMessage = new SimpleMailMessage();
		clsMessage.setFrom( "from@test.com" );
		clsMessage.setTo( "to@test.com" );
		clsMessage.setSubject( "제목" );
		clsMessage.setText( "본문" );
		
		clsMail.send( clsMessage );
	}
}
