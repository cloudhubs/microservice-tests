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

  //Scenario that tests adding route
  val routeAdd: ScenarioBuilder = scenario("Admins Adding Route")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminrouteservice/adminroute",
        "file_path" -> "RouteListAdmin/add_route_form.json")
      newSession
    }
    //Go to route page and complete add
    .exec(routePage, completeAction, routePage)
    .pause(1)

  //Scenario that tests deleting route
  val routeDelete: ScenarioBuilder = scenario("Admins Deleting Route")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "0b23bd3e-876a-4af3-b920-c50a90c90b04",
        "endpoint" -> "adminrouteservice/adminroute",
        "type" -> "Route")
      newSession
    }
    //Go to route page and delete route
    .exec(routePage, delete, routePage)
    .pause(1)

  //TODO: Add feeder for update files

  setUp(
    routeAdd.inject(rampUsers(20).during(15)),
    routeDelete.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}