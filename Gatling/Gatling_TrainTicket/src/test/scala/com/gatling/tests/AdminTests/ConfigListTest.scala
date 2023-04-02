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

  //Test scenario to check add (post) configuration endpoints
  val configAdd: ScenarioBuilder = scenario("Admins Adding Config")
    .exec(loginScenario, configPage) //Log into system as admin
    .exec(http("Add Config (Admin)")
      .post("/api/v1/adminbasicservice/adminbasic/configs") //Add config using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .exec(http("Add Config")
      .post("/api/v1/configservice/configs") //Add config using main service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val configDelete: ScenarioBuilder = scenario("Admins Deleting Config")
    .exec(loginScenario, configPage)
    .exec(http("Delete Config (Admin)")
      .delete("/api/v1/adminbasicservice/adminbasic/configs/DirectTicketAllocationProportion")
      .headers(apiV1Header)) //Delete configuration using admin service
    .exec(http("Delete Config")
      .delete("/api/v1/configservice/configs/DirectTicketAllocationProportion")
      .headers(apiV1Header)) //Delete config using main service
    .pause(1)

  //Test scenario to check update (put) endpoints
  val configUpdate: ScenarioBuilder = scenario("Admins Update Config")
    .exec(loginScenario, configPage) //Log into system as admin
    .exec(http("Update Config (Admin)")
      .put("/api/v1/adminbasicservice/adminbasic/configs")
      .headers(apiV1Header) //Update config using admin service
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .exec(http("Update Config")
      .put("/api/v1/configservice/configs")
      .headers(apiV1Header) //Update config using main service
      .body(RawFileBody("com/gatling/tests/ConfigListAdmin/add_config_form.json")))
    .pause(1)

  //Test scenario to check general config endpoints
  val configGeneral: ScenarioBuilder = scenario("Check General Config Endpoints")
    .exec (http("Get Configs Manually")
        .get("/api/v1/configservice/configs")) //Get all configs
    .exec(http("Get Configs By Name")
        .get("/api/v1/configservice/configs/DirectTicketAllocationProportion")) //Get one config by name
    .exec(http("Config Service Welcome")
        .get("/api/v1/configservice/welcome")) //Get config service welcome

  //Run the test simulation with the scenarios
  setUp(
    configAdd.inject(rampUsers(1).during(15)),
    configDelete.inject(rampUsers(1).during(10)),
    configUpdate.inject(rampUsers(1).during(10)),
    configGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}