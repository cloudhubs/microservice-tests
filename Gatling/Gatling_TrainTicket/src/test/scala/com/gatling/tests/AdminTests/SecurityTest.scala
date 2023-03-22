package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*

class SecurityTest extends Simulation {

  val securityGeneral = scenario("Checking General Security Endpoints")
    .exec(loginScenario)
    .exec(http("Get Security Service Welcome")
      .get("/api/v1/securityservice/welcome")
      .headers(apiV1Header))
    .exec(http("Get Security Service Configs")
      .get("/api/v1/securityservice/securityConfigs")
      .headers(apiV1Header)
      .check(jsonPath("$.data[0].id").saveAs("id")))
    .exec(http("Get Security Service Configs by ID")
      .get("/api/v1/securityservice/securityConfigs/#{id}")
      .headers(apiV1Header))


  val securityAdd = scenario("Admin Adding Security Config")
    .exec(loginScenario)
    .exec(http("Add Security Config")
      .post("/api/v1/securityservice/securityConfigs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json")))

  val securityDelete = scenario("Admin Deleting Security Config")
    .exec(loginScenario)
    .exec(http("Get Security Service Configs")
      .get("/api/v1/securityservice/securityConfigs")
      .headers(apiV1Header)
      .check(bodyString.saveAs("Body"))
      .check(jsonPath("$.data[0].id").saveAs("id")))
    .exec(http("Delete Security Config")
      .delete("/api/v1/securityservice/securityConfigs/#{id}")
      .headers(apiV1Header))
    .exec(session => {
      println("Body= " + session("Body").as[String])
      println("ID= " + session("id").as[String])
      session
    })

  val securityUpdate = scenario("Admin Updating Security Config")
    .exec(loginScenario)
    .exec(http("Update Security Config")
      .put("/api/v1/securityservice/securityConfigs")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json")))

  setUp(
    securityGeneral.inject(rampUsers(1).during(10)),
    securityAdd.inject(rampUsers(1).during(10)),
    securityDelete.inject(rampUsers(1).during(10)),
    securityUpdate.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
