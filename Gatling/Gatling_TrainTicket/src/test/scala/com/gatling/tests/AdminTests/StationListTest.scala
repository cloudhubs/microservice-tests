package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.adminLoginScenario
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*


class StationListTest extends Simulation {

  //Scenario that tests adding station
  val stationAdd: ScenarioBuilder = scenario("Admins Adding Station")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add",
        "endpoint" -> "adminbasicservice/adminbasic/stations",
        "file_path" -> "StationListAdmin/add_station_form.json")
      newSession
    }
    //Go to station page and complete add
    .exec(stationPage, action, stationPage)
    .pause(1)

  //Scenario that tests deleting station
  val stationDelete: ScenarioBuilder = scenario("Admins Deleting Station")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "5307f68c-dc6d-4461-a262-354be961827f",
        "endpoint" -> "adminbasicservice/adminbasic/stations",
        "type" -> "Station")
      newSession
    }
    //Go to station page and delete station
    .exec(stationPage, delete, stationPage)
    .pause(1)

  //TODO: Add feeder for update files

  setUp(
    stationAdd.inject(rampUsers(20).during(15)),
    stationDelete.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}