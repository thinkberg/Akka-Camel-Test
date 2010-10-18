package twimpact.akka.camel

import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.camel.{Oneway, Producer}

/**
 * >>Describe Class<<
 *
 * @author Matthias L. Jugel
 */

class CamelProducerOneWay(name: String, uri: String) extends Actor with Producer with Oneway {
  self.id = name
  def endpointUri = uri

  // @TODO: tip by martin krasser to overcome a local actor queue full exception (and use ref !! ...)
  override protected def receiveAfterProduce = {
    case msg => self.reply("OK")
  }
}