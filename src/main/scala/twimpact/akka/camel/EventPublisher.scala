package twimpact.akka.camel

import se.scalablesolutions.akka.actor.Actor

/**
 * Test Event Producer, sending messages to an activemq topic
 *
 * @author Matthias L. Jugel
 */

class EventPublisher(name: String, uri: String) extends Actor {
  self.id = name
  lazy val producer = Actor.actorOf(new CamelProducerOneWay(name, uri)).start

  override def init = {
    log.info("Starting publisher '%s' on '%s'.", name, uri)
    self ! "start"
  }

  val EVENT_SKIP = 1000
  var servedEvents = 0L
  var eventTime = System.currentTimeMillis

  override def receive = {
    case "start" =>
      while(true) {
        producer !! (("MessageEvent", "Message Event #%d".format(servedEvents)))
        servedEvents = servedEvents + 1
        if (servedEvents % EVENT_SKIP == 0) {
          val speed = EVENT_SKIP / ((System.currentTimeMillis - eventTime + 1) / 1000.0)
          print("\rPRODUCER: %d (%.0f events/s)".format(servedEvents, speed))
          eventTime = System.currentTimeMillis
        }
      }
    case _ =>
      log.error("unknown message received, send 'start' to begin producing")

  }
}