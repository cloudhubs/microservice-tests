package com.gatling.tests.RouteListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class RouteListTest extends Simulation {

  /**Change json file for invalid add*/
  val routeAdd = scenario("Users Adding Route").exec(routePage, addRoute, routePage)

  val routeDelete = scenario("Users Deleting Route").exec(routePage, deleteRoute, routePage)

  val routeUpdate = scenario("Users Updating Route").exec(routePage, updateRoute, routePage)

  setUp(
    routeAdd.inject(rampUsers(20).during(15)),
    routeDelete.inject(rampUsers(20).during(15)),
    routeUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}