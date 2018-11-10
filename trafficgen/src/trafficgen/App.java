package trafficgen;

import org.apache.log4j.BasicConfigurator;

public class App {

	public static void main(String[] args) throws Exception {
		BasicConfigurator.configure();

		 MyProducer producer= new MyProducer(1000);
		 producer.start();
		 Thread.sleep(1000);
//		new MyConsumer();

//		 try {
//		   if (args.length != 3)
//		    System.out
//		      .println("Please Provide the topic name,username,password!");
//		   
//		   
//		   MyMessagePublisherThread pub = new MyMessagePublisherThread(
//		     args[0], args[1], args[2]);
//		   MyMessageSubscriberThread sub = new MyMessageSubscriberThread(
//				     args[0], args[1], args[2]);
//		   pub.start();
//		   BufferedReader commandLine = new java.io.BufferedReader(
//		     new InputStreamReader(System.in));
//		   // closes the connection and exit the system when 'exit' enters in
//		   // the command line
//		   while (true) {
//		    String s = commandLine.readLine();
//		    if (s.equalsIgnoreCase("exit")) {
//		     pub.connection.close();
//		     sub.connection.close();
//		     System.exit(0);
//		    }
//		   }
//		  } catch (Exception e) {
//		   e.printStackTrace();
//		  }
//		 
//		 

//		 MyMessagePublisherThread m= new MyMessagePublisherThread();
//	        thread(new MyProducer(), false);
//	        thread(new MyProducer(), false);
//	        thread(new MyConsumer(), false);
//	        Thread.sleep(1000);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
//	        Thread.sleep(1000);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
//	        thread(new MyProducer(), false);
//	        Thread.sleep(1000);
//	        thread(new MyProducer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyConsumer(), false);
//	        thread(new MyProducer(), false);
	}

	public static void thread(Runnable runnable, boolean daemon) {
		Thread brokerThread = new Thread(runnable);
		brokerThread.setDaemon(daemon);
		brokerThread.start();
	}
}
