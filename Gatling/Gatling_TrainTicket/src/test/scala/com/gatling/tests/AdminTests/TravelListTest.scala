package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.adminLoginScenario
import io.gatling.core.Predef.*
import io.gatling.core.structure.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class TravelListTest extends Simulation {

  val travelAdd: ScenarioBuilder = scenario("Admins Adding Travel")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "admintravelservice/admintravel",
        "file_path" -> "TravelListAdmin/update_travel_invalid.json")
      newSession
    }
    //Go to home page and view cart
    .exec(travelPage, action, travelPage)
    .pause(1)

  val travelDelete: ScenarioBuilder = scenario("Admins Deleting Travel")
    .exec(adminLoginScenario)
    .exec { session =>
      val newSession = session.setAll("delete_id" -> "Z1235",
        "endpoint" -> "admintravelservice/admintravel",
        "type" -> "Travel")
      newSession
    }
    //Go to home page and view cart
    .exec(travelPage, delete, travelPage)
    .pause(1)

  //TODO: Add feeder for update files

  setUp(
    travelAdd.inject(rampUsers(20).during(15)),
    travelDelete.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}