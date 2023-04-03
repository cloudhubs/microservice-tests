package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class NotifyServiceTest extends Simulation {

  //Test scenario to check general notify service endpoints
  val notifyGeneral: ScenarioBuilder = scenario("Check General Notify Endpoints")
    .exec(
      http("Get Notify Service Welcome") //Get notify service welcome
        .get("/api/v1/notifyservice/welcome"))

  //Test scenario to check add (post) notify service endpoints
  val notifyPost: ScenarioBuilder = scenario("Check Notify Post Endpoints")
    .exec(loginScenario)
    .exec(
      http("Post Preserve Success")
        .post("/api/v1/notifyservice/notification/preserve_success") //Post preserve success
        .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Create Success")
        .post("/api/v1/notifyservice/notification/order_create_success") //Post order create success
        .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Changed Success")
        .post("/api/v1/notifyservice/notification/order_changed_success") //Post order changed success
        .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Cancel Success")
        .post("/api/v1/notifyservice/notification/order_cancel_success") //Post order cancel success
        .body(RawFileBody("com/gatling/tests/SecurityService/security_config.json"))
        .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    notifyGeneral.inject(rampUsers(1).during(10)),
    notifyPost.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
