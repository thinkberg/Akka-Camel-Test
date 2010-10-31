package twimpact.akka.camel

import org.springframework.context.support.ClassPathXmlApplicationContext
import grizzled.slf4j.Logger
import se.scalablesolutions.akka.actor.Actor

/**
 * A test for akka-camel to get a hand on how certain conditions are handled.
 *
 * @author Matthias L. Jugel
 */

object AkkaCamelTest {
  lazy val log = Logger(getClass)

  def main(args: Array[String]): Unit = {
    args(0) match {
      case "publisher" =>
        val context = new ClassPathXmlApplicationContext("/publisher-context.xml")
        val eventPublisher = Actor.actorOf(new EventPublisher("akka-camel-test", "activemq:topic:akka-camel-test"))
        eventPublisher.start

      case "subscriber" =>
        val context = new ClassPathXmlApplicationContext("/subscriber-context.xml")
        val eventSubscriber = Actor.actorOf(new EventSubscriber("akka-camel-test", "activemq:topic:akka-camel-test"))
        eventSubscriber.start
    }

  }
}