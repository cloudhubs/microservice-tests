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

  //Test scenario to check add (post) order endpoints
  val orderAdd: ScenarioBuilder = scenario("Admins Adding Order")
    .exec(loginScenario, adminHomePage) //Log into system as admin
    .exec(http("Add Order")
      .post("/api/v1/adminorderservice/adminorder") //Add order
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/OrderListAdmin/add_order_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val orderDelete: ScenarioBuilder = scenario("Admins Deleting Order")
    .exec(loginScenario, adminHomePage)
    .exec(http("Delete Order") //Delete order by ID
      .delete("/api/v1/adminorderservice/adminorder/301f39ba-f31d-4795-bac2-cbc8909a7e97/G1237")
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check update (put) endpoints
  val orderUpdate: ScenarioBuilder = scenario("Admins Updating Order")
    .exec(loginScenario, adminHomePage) //Log into system as admin
    .exec(
      http("Update Order")
        .put("/api/v1/adminorderservice/adminorder") //Update order
        .body(RawFileBody("com/gatling/tests/OrderListAdmin/update_order_form.json"))
        .headers(apiV1Header))
    .pause(1)

  //Test scenario to check general order endpoints
  val orderGeneral: ScenarioBuilder = scenario("Check General Admin Order Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Admin Order Welcome")
      .get("/api/v1/adminorderservice/welcome") //Get admin order service welcome
      .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    orderAdd.inject(rampUsers(1).during(15)),
    orderDelete.inject(rampUsers(1).during(15)),
    orderUpdate.inject(rampUsers(1).during(15)),
    orderGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}