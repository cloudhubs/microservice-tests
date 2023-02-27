package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.NavigationModules.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class CollectTicket extends Simulation {

  val users = scenario("Users Collecting Ticket").exec(homePage, ticketPage, collectTicket)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}