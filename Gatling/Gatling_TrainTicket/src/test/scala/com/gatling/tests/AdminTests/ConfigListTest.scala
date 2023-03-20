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
    .exec(loginScenario, configPage) //Log into system as admin
    //Go to configuration page and complete add
    .exec(http("Add Config (Admin)")
      .post("/api/v1/adminbasicservice/adminbasic/configs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .exec(http("Add Config")
      .post("/api/v1/configservice/configs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .pause(1)

  //Scenario that tests deleting configuration
  val configDelete: ScenarioBuilder = scenario("Admins Deleting Config")
    .exec(loginScenario, configPage)
    //Go to configuration page and delete configuration
    .exec(http("Delete Config (Admin)")
      .delete("/api/v1/adminbasicservice/adminbasic/configs/DirectTicketAllocationProportion")
      .headers(apiV1Header))
    .exec(http("Delete Config")
      .delete("/api/v1/configservice/configs/DirectTicketAllocationProportion")
      .headers(apiV1Header))
    .pause(1)

  val configUpdate: ScenarioBuilder = scenario("Admins Update Config")
    .exec(loginScenario, configPage) //Log into system as admin
    //Go to configuration page and complete add
    .exec(http("Update Config (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/configs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .exec(http("Update Config")
      .put("/api/v1/configservice/configs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .pause(1)

  val configGeneral: ScenarioBuilder = scenario("Check General Config Endpoints")
    .exec (
      http("Get Configs Manually")
        .get("/api/v1/configservice/configs"))
    .exec(
      http("Get Configs By Name")
        .get("/api/v1/configservice/configs/DirectTicketAllocationProportion"))
    .exec(
      http("Config Service Welcome")
        .get("/api/v1/configservice/welcome"))

  setUp(
    configAdd.inject(rampUsers(1).during(15)),
    configDelete.inject(rampUsers(1).during(10)),
    configUpdate.inject(rampUsers(1).during(10)),
    configGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}