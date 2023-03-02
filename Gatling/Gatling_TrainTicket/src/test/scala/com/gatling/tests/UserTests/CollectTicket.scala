package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class CollectTicket extends Simulation {

  val users = scenario("Users Collecting Ticket")
    .exec(userLoginScenario)
    .exec { session =>
      val newSession = session.setAll("ticket_id" -> "4220e6bf-7c4b-4b74-9a02-f448b28b79be")
      newSession
    }
    .exec(ticketPage, collectTicket)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}