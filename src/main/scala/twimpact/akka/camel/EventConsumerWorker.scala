package twimpact.akka.camel

import se.scalablesolutions.akka.actor.Actor

/**
 * >>Describe Class<<
 *
 * @author Matthias L. Jugel
 */

class EventConsumerWorker extends Actor {
  protected def receive = {
    case _ =>
      Thread.sleep(2000)
  }
}