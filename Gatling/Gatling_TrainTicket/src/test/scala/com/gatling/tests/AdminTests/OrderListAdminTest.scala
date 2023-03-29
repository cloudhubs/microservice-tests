package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class OrderListAdminTest extends Simulation {

  //Scenario that tests adding order
  val orderAdd: ScenarioBuilder = scenario("Admins Adding Order")
    .exec(loginScenario, adminHomePage) //Log into system as admin
    //Go to order page and complete add
    .exec(http("Add Order")
      .post("/api/v1/adminorderservice/adminorder")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/OrderListAdmin/add_order_form.json")))
    .pause(1)

  //Scenario that tests deleting contact
  val orderDelete: ScenarioBuilder = scenario("Admins Deleting Order")
    .exec(loginScenario, adminHomePage)
    .exec(http("Delete Order")
      .delete("/api/v1/adminorderservice/adminorder/301f39ba-f31d-4795-bac2-cbc8909a7e97/G1237")
      .headers(apiV1Header))
    .pause(1)

  val orderUpdate: ScenarioBuilder = scenario("Admins Updating Order")
    .exec(loginScenario, adminHomePage) //Log into system as admin
    .exec(
      http("Update Order")
        .put("/api/v1/adminorderservice/adminorder")
        .body(RawFileBody("com/gatling/tests/OrderListAdmin/update_order_form.json"))
        .headers(apiV1Header))
    .pause(1)

  val orderGeneral = scenario("Check General Admin Order Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Admin Order Welcome")
      .get("/api/v1/adminorderservice/welcome")
      .headers(apiV1Header))

  setUp(
    orderAdd.inject(rampUsers(1).during(15)),
    orderDelete.inject(rampUsers(1).during(15)),
    orderUpdate.inject(rampUsers(1).during(15)),
    orderGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}