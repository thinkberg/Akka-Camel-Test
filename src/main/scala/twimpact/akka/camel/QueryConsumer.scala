package twimpact.akka.camel

import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.camel.{Message, Consumer}

/**
 * >>Describe Class<<
 *
 * @author Matthias L. Jugel
 */

class QueryConsumer extends Actor with Consumer {
  def endpointUri = "activemq:queue:akka-camel-query-test"

  protected def receive = {
    case message: Message =>
      log.debug("received: %s".format(message))
      message.body match {
        case ("QUERY", "long") =>
          Thread.sleep(20000)
          self.reply(Some("RESPONSE long"))
        case ("QUERY", "short") =>
          Thread.sleep(1000)
          self.reply(Some("RESPONSE short"))
        case _ =>
          log.error("%s: unknown message received: %s".format(self.id, message))
      }
    case _ =>
      log.error("%s: ignoring unexpected message: %s".format(self.id, (_: Object).toString))
      self.reply_?(None)

  }
}
