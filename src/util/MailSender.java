package util;

import java.util.ArrayList;

import javax.servlet.ServletContext;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClient;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;

public class MailSender
{
	private AmazonSimpleEmailServiceClient client;
	private String defaultEmail;
	
	public MailSender(ServletContext sc)
	{
		String key = sc.getInitParameter("aws_key_id");
		String secret = sc.getInitParameter("aws_secret_key");
		defaultEmail = sc.getInitParameter("default_email");
		
		client = new AmazonSimpleEmailServiceClient(new BasicAWSCredentials (key, secret));
	}

	public String sendEmail(String to, String subject, String body)
	{
		ArrayList<String> toList = new ArrayList<String>();
		toList.add(to);
		
		return sendEmail(defaultEmail, toList, subject, body);
	}

	public String sendEmail(String from, String to, String subject, String body)
	{
		ArrayList<String> toList = new ArrayList<String>();
		toList.add(to);
		
		return sendEmail(from, toList, subject, body);
	}
	
	public String sendEmail(ArrayList<String> to, String subject, String body)
	{
		return sendEmail(defaultEmail, to, subject, body);
	}
	
	public String sendEmail(String from, ArrayList<String> to, String subject, String body)
	{
		Destination destination = new Destination(to);
		Message msg = new Message(new Content(subject),new Body(new Content(body)));

		SendEmailRequest request = new SendEmailRequest(from, destination, msg);
		SendEmailResult result = client.sendEmail(request);

		return result.getMessageId();
	}
}