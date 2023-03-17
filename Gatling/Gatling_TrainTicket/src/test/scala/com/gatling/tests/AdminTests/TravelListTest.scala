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

  //Scenario that tests adding travel
  val travelAdd: ScenarioBuilder = scenario("Admins Adding Travel")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "admintravelservice/admintravel",
        "file_path" -> "TravelListAdmin/update_travel_invalid.json")
      newSession
    }
    //Go to travel page and complete add
    .exec(travelPage, completeAction, travelPage)
    .pause(1)

  //Scenario that tests deleting travel
  val travelDelete: ScenarioBuilder = scenario("Admins Deleting Travel")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "Z1235",
        "endpoint" -> "admintravelservice/admintravel",
        "type" -> "Travel")
      newSession
    }
    //Go to travel page and delete travel
    .exec(travelPage, delete, travelPage)
    .pause(1)

  //TODO: Add feeder for update files

  setUp(
    travelAdd.inject(rampUsers(1).during(15)),
    travelDelete.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}