package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.adminLoginScenario
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class RouteListTest extends Simulation {

  val routeAdd: ScenarioBuilder = scenario("Admins Adding Route")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminrouteservice/adminroute",
        "file_path" -> "RouteListAdmin/add_route_form.json")
      newSession
    }
    //Go to home page and view cart
    .exec(routePage, action, routePage)
    .pause(1)

  val routeDelete: ScenarioBuilder = scenario("Admins Deleting Route")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("delete_id" -> "0b23bd3e-876a-4af3-b920-c50a90c90b04",
        "endpoint" -> "adminrouteservice/adminroute",
        "type" -> "Route")
      newSession
    }
    //Go to home page and view cart
    .exec(routePage, delete, routePage)
    .pause(1)

  //TODO: Add feeder for update files

  setUp(
    routeAdd.inject(rampUsers(20).during(15)),
    routeDelete.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}