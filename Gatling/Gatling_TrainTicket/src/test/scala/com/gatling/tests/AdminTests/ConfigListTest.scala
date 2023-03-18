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
    .exec(
      http("Add Config")
      .post("/api/v1/configservice/configs")
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json"))
      .headers(apiV1Header))
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
    .exec(
      http("Delete Config")
        .delete("/api/v1/configservice/configs/DirectTicketAllocationProportion")
        .headers(apiV1Header))
    .pause(1)

  val configUpdate: ScenarioBuilder = scenario("Admins Updating Config")
    .exec(adminLoginScenario, configPage) //Log into system as admin
    .exec(
      http("Add Config")
        .put("/api/v1/configservice/configs")
        .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json"))
        .headers(apiV1Header))
    .pause(1)

  val configGeneral: ScenarioBuilder = scenario("Check General Config Service Endpoints")
    .exec (
      http("Get Configs Manually")
        .get("/api/v1/configservice/configs"))
    .pause(1)
    .exec(
      http("Get Configs By Name")
        .get("/api/v1/configservice/configs/DirectTicketAllocationProportion"))
    .exec(
      http("Get Config Service Welcome")
        .get("/api/v1/configservice/welcome"))

  setUp(
    configAdd.inject(rampUsers(1).during(15)),
    configDelete.inject(rampUsers(1).during(10)),
    configUpdate.inject(rampUsers(1).during(10)),
    configGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}