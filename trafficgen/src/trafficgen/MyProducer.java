package trafficgen;

import java.time.Instant;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MyProducer extends Thread {
	private Session session;
	private MessageProducer producer;
	Connection connection;

	private int iterations;

	public MyProducer(int iterations) throws JMSException, NamingException {
		this.iterations = iterations;
		// Obtain a JNDI connection
		InitialContext jndi = new InitialContext();
		// Look up a JMS connection factory
		ConnectionFactory conFactory = (ConnectionFactory) jndi.lookup("connectionFactory");
//		  System.out.println(conFactory.toString()+"AAAAAA");

		// Getting JMS connection from the server and starting it
		connection = conFactory.createConnection();

		connection.start();
		// JMS messages are sent and received using a Session. We will
		// create here a non-transactional session object. If you want
		// to use transactions you should set the first parameter to 'true'
		session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = (Destination) jndi.lookup("MyQueue");
//		   System.out.println(destination.toString());
		// MessageProducer is used for sending messages (as opposed
		// to MessageConsumer which is used for receiving them)
		producer = session.createProducer(destination);
//		    producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

	}

	@Override
	public void run() {
		System.out.println("Producer thread " + this.getName() + " started at " + Instant.now());
		for (int i = 0; i < iterations; i++) {
			// We will send a small text message
			try {
				TextMessage message = session
						.createTextMessage("Hello World! from thread: " + this.getName() + " at " + System.nanoTime());
				// Here we are sending the message!
				producer.send(message);
				Thread.sleep(50);
				
			} catch (JMSException | InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		try {
		session.close();
		connection.close();
		}catch (JMSException e) {
			e.printStackTrace();
		}
		System.out.println("Producer thread " + this.getName() + " completed at " + Instant.now());
	}
}
