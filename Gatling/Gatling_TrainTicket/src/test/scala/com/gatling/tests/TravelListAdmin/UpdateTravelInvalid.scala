package com.gatling.tests.TravelListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class UpdateTravelInvalid extends Simulation {

  /** operation = Update, file_path = , endpoint = admintravelservice/admintravel */
  val users = scenario("Users Updating Travel").exec(travelPage, action, travelPage)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}