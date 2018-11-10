package trafficgen;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.InitialContext;

public class MyMessageSubscriberThread extends Thread implements MessageListener {
	TopicConnection connection;

	public MyMessageSubscriberThread(String topicName, String username, String password) throws Exception {
		InitialContext jndi = new InitialContext();
		TopicConnectionFactory conFactory = (TopicConnectionFactory) jndi.lookup("topicConnectionFactry");
		connection = conFactory.createTopicConnection(username, password);
		TopicSession subSession = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic chatTopic = new Topic() {

			@Override
			public String getTopicName() throws JMSException {
				// TODO Auto-generated method stub
				return "test.MyTopic";
			}
		};
//		Topic chatTopic = (Topic) jndi.lookup(topicName);
		TopicSubscriber subscriber = subSession.createSubscriber(chatTopic);
		subscriber.setMessageListener(this);
		connection.start();

	}

	@Override
	public void onMessage(Message message) {

		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);

		} catch (JMSException jmse) {
			jmse.printStackTrace();
		}

	}

}
