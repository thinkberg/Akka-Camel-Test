package twimpact.akka.camel

import se.scalablesolutions.akka.camel.Producer
import se.scalablesolutions.akka.actor.Actor

/**
 * >>Describe Class<<
 *
 * @author Matthias L. Jugel
 */

class QueryProducer extends Actor with Producer {
  self.timeout = 5 * 60 * 1000
  def endpointUri = "activemq:queue:akka-camel-query-test?requestTimeout=300000"
}