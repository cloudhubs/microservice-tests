package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*

class SecurityTest extends Simulation {

  val securityGeneral = scenario("Checking General Security Endpoints")
    .exec(adminLoginScenario)
    .exec(http("Get Security Service Welcome")
      .get("/api/v1/securityservice/welcome")
      .headers(apiV1Header))
    .exec(http("Get Security Service Configs")
      .get("/api/v1/securityservice/securityConfigs")
      .headers(apiV1Header))
    .exec(http("Get Security Service Configs by ID")
      .get("/api/v1/securityservice/securityConfigs/9933e4d8-1d9d-41a4-b2cf-e468640cf106")
      .headers(apiV1Header))

  val securityAdd = scenario("Admin Adding Security Config")
    .exec(adminLoginScenario)
    .exec(http("Add Security Config")
      .post("/api/v1/securityservice/securityConfigs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/security_config.json")))

  val securityDelete = scenario("Admin Deleting Security Config")
    .exec(adminLoginScenario)
    .exec(http("Delete Security Config")
      .delete("/api/v1/securityservice/securityConfigs/9933e4d8-1d9d-41a4-b2cf-e468640cf106")
      .headers(apiV1Header))

  val securityUpdate = scenario("Admin Updating Security Config")
    .exec(adminLoginScenario)
    .exec(http("Update Security Config")
      .put("/api/v1/securityservice/securityConfigs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/security_config.json")))

  setUp(
    securityGeneral.inject(rampUsers(1).during(10)),
    securityAdd.inject(rampUsers(1).during(10)),
    securityDelete.inject(rampUsers(1).during(10)),
    securityUpdate.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
