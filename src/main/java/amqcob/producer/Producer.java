package amqcob.producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;


public class Producer {
    public static void main(String[] args) throws Exception{
        Connection connection = null;
        try{
            Topic topic = ActiveMQJMSClient.createTopic("exampleTopic");
            ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://localhost:61616");
            connection = cf.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();
            MessageProducer producer = session.createProducer(topic);

            final int numMessages = 10;

            for (int i = 0; i < numMessages; i++) {
                TextMessage message = session.createTextMessage("This is text message " + i);
                producer.send(message);
                System.out.println("Sent message: " + message.getText());
            }
        }finally {
            if(connection != null){
                connection.close();
            }
        }
    }
}

