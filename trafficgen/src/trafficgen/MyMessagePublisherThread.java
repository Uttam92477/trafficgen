package trafficgen;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.InitialContext;

public class MyMessagePublisherThread extends Thread {

	private TopicSession pubSession;
	private TopicPublisher publisher;
	TopicConnection connection;
	private String username;

	public MyMessagePublisherThread(String topicName, String username, String password) throws Exception {
//		Properties props= new Properties();
//		InputStream inputStream= new FileInputStream("jndi.properties");
//		props.load(inputStream);
//		

		this.username = username;
		// Obtain a JNDI connection
		InitialContext jndi = new InitialContext();
		// Look up a JMS connection factory
		TopicConnectionFactory conFactory = (TopicConnectionFactory) jndi.lookup("topicConnectionFactry");
		// Create a JMS connection
		connection = conFactory.createTopicConnection(username, password);
		// Create JMS session objects for publisher and subscriber
		pubSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
//		  TopicSession subSession = connection.createTopicSession(false,
//		    Session.AUTO_ACKNOWLEDGE);
		// Look up a JMS topic
		Topic chatTopic = new Topic() {

			@Override
			public String getTopicName() throws JMSException {
				// TODO Auto-generated method stub
				return "test.MyTopic";
			}
		};
//		  Topic chatTopic = (Topic) jndi.lookup("topic.MyTopic");
		// Create a JMS publisher
		publisher = pubSession.createPublisher(chatTopic);

		// Start the JMS connection; allows messages to be delivered
		connection.start();
		// Create and send message using topic publisher

	}

	@Override
	public void run() {
		try {
			for (;;) {
				TextMessage message = pubSession.createTextMessage();
				message.setText(username + ": Howdy Friends!" + System.nanoTime());
				publisher.publish(message);
				Thread.sleep(100);
			}
		} catch (JMSException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}
