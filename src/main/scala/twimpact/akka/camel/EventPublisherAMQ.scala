package twimpact.akka.camel

import org.apache.activemq.ActiveMQConnectionFactory
import javax.jms.Session
import java.io.Serializable

/**
 * This is a testing producer that directly interfaces ActiveMQ to ensure
 * we do not have problems with ActiveMQ directly.
 *
 * @author Matthias L. Jugel
 */

object EventPublisherAMQ extends Application {
  val factory = new ActiveMQConnectionFactory("tcp://localhost:61616")
  val connection = factory.createTopicConnection()
  connection.start()
  val session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE)
  val destination = session.createTopic("akka-camel-test")
  val publisher = session.createPublisher(destination)

  val EVENT_SKIP = 1000
  var servedEvents = 0L
  var eventTime = System.currentTimeMillis

  while (true) {
    val content = (("MessageEvent", "Message Event #%d".format(servedEvents)))
    val message = session.createObjectMessage(content.asInstanceOf[Serializable])
    publisher.send(message)
    servedEvents = servedEvents + 1
    if (servedEvents % EVENT_SKIP == 0) {
      val speed = EVENT_SKIP / ((System.currentTimeMillis - eventTime + 1) / 1000.0)
      print("\rPRODUCER: %d (%.0f events/s)".format(servedEvents, speed))
      eventTime = System.currentTimeMillis
    }
  }
}