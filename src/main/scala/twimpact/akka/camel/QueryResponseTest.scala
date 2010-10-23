package twimpact.akka.camel

import grizzled.slf4j.Logger
import se.scalablesolutions.akka.actor.Actor
import org.springframework.context.support.ClassPathXmlApplicationContext

/**
 * >>Describe Class<<
 *
 * @author Matthias L. Jugel
 */

object QueryResponseTest {
  lazy val log = Logger(getClass)

  def main(args: Array[String]): Unit = {
    val context = new ClassPathXmlApplicationContext("/publisher-context.xml")

    val producer = Actor.actorOf[QueryProducer].start
    val consumer = Actor.actorOf[QueryConsumer].start

    log.debug("ready, sending message")
    val response = producer !! (("QUERY", "long"), 30000)
    log.debug("received response: %s".format(response))
  }
}