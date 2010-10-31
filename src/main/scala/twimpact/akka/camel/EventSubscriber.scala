package twimpact.akka.camel

import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.camel.{Message, Consumer}

/**
 * >>Describe Class<<
 *
 * @author Matthias L. Jugel
 */

class EventSubscriber(name: String, uri: String) extends Actor with Consumer {
  self.id = name
  def endpointUri = uri

  override def blocking = true

  val EVENT_SKIP = 1000
  var eventCounter: Long = 0
  var eventTime: Long = System.currentTimeMillis

  val workers = Map(
    0 -> Actor.actorOf[EventConsumerWorker].start,
    1 -> Actor.actorOf[EventConsumerWorker].start,
    2 -> Actor.actorOf[EventConsumerWorker].start,
    3 -> Actor.actorOf[EventConsumerWorker].start)

  var counter = 0

  protected def receive = {
    case message: Message =>
      message.body match {
        case ("MessageEvent", message: String) =>
          eventCounter = eventCounter + 1
          if (counter > 3) counter = 0
          try {workers(counter) ! message} catch {
            case e: IllegalStateException => println("\nThrottling ..."); Thread.sleep(2000)
          }
          counter = counter + 1
          if (eventCounter % EVENT_SKIP == 0) {
            val speed = EVENT_SKIP / ((System.currentTimeMillis - eventTime + 1) / 1000.0)
            print("\rCONSUMER: %d (%.0f events/s) %s".format(eventCounter, speed, message))
            eventTime = System.currentTimeMillis
          }
        case _ =>
          log.error("%s: unknown message received: %s".format(self.id, message))
      }
    case _ =>
      log.error("%s: ignoring unexpected message: %s".format(self.id, (_: Object).toString))
  }
}