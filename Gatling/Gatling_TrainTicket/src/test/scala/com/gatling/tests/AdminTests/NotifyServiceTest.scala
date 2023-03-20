package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class NotifyServiceTest extends Simulation {

  val notifyGeneral = scenario("Check General Notify Endpoints")
    .exec(
      http("Get Notify Service Welcome")
        .get("/api/v1/notifyservice/welcome"))

  val notifyPost = scenario("Check Notify Post Endpoints")
    .exec(
      http("Post Preserve Success")
        .post("/api/v1/notifyservice/notification/preserve_success")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Post Order Create Success")
        .post("/api/v1/notifyservice/notification/order_create_success")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Post Order Changed Success")
        .post("/api/v1/notifyservice/notification/order_changed_success")
        .body(RawFileBody("com/gatling/tests/station_food.json")))
    .exec(
      http("Post Order Cancel Success")
        .post("/api/v1/notifyservice/notification/order_cancel_success")
        .body(RawFileBody("com/gatling/tests/station_food.json")))

  setUp(
    notifyGeneral.inject(rampUsers(1).during(10)),
    notifyPost.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
