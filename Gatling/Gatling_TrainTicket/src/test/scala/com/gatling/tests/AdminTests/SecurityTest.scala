package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*

class SecurityTest extends Simulation {

  //Test scenario to check general security service endpoints
  val securityGeneral: ScenarioBuilder = scenario("Checking General Security Endpoints")
    .exec(loginScenario)
    .exec(http("Get Security Service Welcome")
      .get("/api/v1/securityservice/welcome") //Get security service welcome
      .headers(apiV1Header))
    .exec(http("Get Security Service Configs")
      .get("/api/v1/securityservice/securityConfigs") //Get security configs
      .headers(apiV1Header)
      .check(jsonPath("$.data[0].id").saveAs("id")))
    .exec(http("Get Security Service Configs by ID")
      .get("/api/v1/securityservice/securityConfigs/#{id}") //Get security config by id
      .headers(apiV1Header))

  //Test scenario to check add (put) endpoints
  val securityAdd: ScenarioBuilder = scenario("Admin Adding Security Config")
    .exec(loginScenario)
    .exec(http("Add Security Config")
      .post("/api/v1/securityservice/securityConfigs") //Add security config
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json")))

  //Test scenario to check deletion endpoints
  val securityDelete: ScenarioBuilder = scenario("Admin Deleting Security Config")
    .exec(loginScenario)
    .exec(http("Get Security Service Configs")
      .get("/api/v1/securityservice/securityConfigs") //Get security configs
      .headers(apiV1Header)
      .check(bodyString.saveAs("Body"))
      .check(jsonPath("$.data[0].id").saveAs("id")))
    .exec(http("Delete Security Config")
      .delete("/api/v1/securityservice/securityConfigs/#{id}") //Delete security config by id
      .headers(apiV1Header))

  //Test scenario to check update (put) endpoints
  val securityUpdate: ScenarioBuilder = scenario("Admin Updating Security Config")
    .exec(loginScenario)
    .exec(http("Update Security Config")
      .put("/api/v1/securityservice/securityConfigs") //Update security config
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json")))

  //Run the test simulation with the scenarios
  setUp(
    securityGeneral.inject(rampUsers(1).during(10)),
    securityAdd.inject(rampUsers(1).during(10)),
    securityDelete.inject(rampUsers(1).during(10)),
    securityUpdate.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
