package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
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
    .exec(configPage, action, configPage)
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

  /**TODO: Same process as add just different file*/

  setUp(
    configAdd.inject(rampUsers(10).during(10)),
    configDelete.inject(rampUsers(10).during(10))
  ).protocols(httpProtocolTrainTicket)
}