package trafficgen;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class MyConsumer implements MessageListener {
	// URL of the JMS server
	private static String url = ActiveMQConnection.DEFAULT_BROKER_URL;

	// Name of the queue we will receive messages from
	private static String subject = "test.MyQueue";
	private MessageConsumer consumer;
	private Connection connection;

	public MyConsumer() throws JMSException {
		// Getting JMS connection from the server
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
		connection = connectionFactory.createConnection();
		connection.start();
		System.out.println(url);
		// Creating session for sending messages
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		// Getting the queue
		Destination destination = session.createQueue(subject);
		// MessageConsumer is used for receiving (consuming) messages
		consumer = session.createConsumer(destination);
		consumer.setMessageListener(this);
	}

	@Override
	public void onMessage(Message message) {
		System.out.println(message);

	}
	public static void main(String[] args) {
		try {
			new MyConsumer();
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}
