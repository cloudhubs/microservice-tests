package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class ConfigListTest extends Simulation {

  //Scenario that tests adding configurations
  val configAdd: ScenarioBuilder = scenario("Admins Adding Config")
    .exec(adminLoginScenario) //Log into system as admin
    .exec { session =>
      val newSession = session.setAll("operation" -> "Add", //Set up general session info
        "endpoint" -> "adminbasicservice/adminbasic/configs",
        "file_path" -> "ConfigListAdmin/add_config_form.json")
      newSession
    }
    //Go to configuration page and complete add
    .exec(configPage, completeAction, configPage)
    .pause(1)

  //Scenario that tests deleting configuration
  val configDelete: ScenarioBuilder = scenario("Admins Deleting Config")
    .exec(adminLoginScenario)
    .exec { session => //Set up session information
      val newSession = session.setAll("delete_id" -> "DirectTicketAllocationProportion",
        "endpoint" -> "adminbasicservice/adminbasic/configs",
        "type" -> "Config")
      newSession
    }
    //Go to configuration page and delete configuration
    .exec(configPage, delete, configPage)
    .pause(1)

  val configGet: ScenarioBuilder = scenario("Get Configs")
    .exec (
      http("Get Configs Manually")
        .get("/api/v1/configservice/configs"))
    .pause(1)
    .exec(
      http("Config Service Welcome")
        .get("/api/v1/configservice/welcome"))

  setUp(
    configAdd.inject(rampUsers(1).during(15)),
    configDelete.inject(rampUsers(1).during(10)),
    configGet.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}