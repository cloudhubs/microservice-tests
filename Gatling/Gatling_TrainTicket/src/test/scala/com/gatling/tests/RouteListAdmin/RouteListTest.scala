package com.gatling.tests.RouteListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class RouteListTest extends Simulation {

  /** operation = Add, file_path = , endpoint = adminrouteservice/adminroute */
  val routeAdd = scenario("Users Adding Route").exec(routePage, action, routePage)

  /** delete_id = 0b23bd3e-876a-4af3-b920-c50a90c90b04, type = Route, endpoint = adminrouteservice/adminroute */
  val routeDelete = scenario("Users Deleting Route").exec(routePage, delete, routePage)

  /** operation = Add, file_path = , endpoint = adminrouteservice/adminroute */
  val routeUpdate = scenario("Users Updating Route").exec(routePage, action, routePage)

  setUp(
    routeAdd.inject(rampUsers(20).during(15)),
    routeDelete.inject(rampUsers(20).during(15)),
    routeUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}