package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.LoginModule.*

class RouteListTest extends Simulation {

  //Test scenario to check add (put) endpoints
  val routeAdd: ScenarioBuilder = scenario("Admins Adding Route")
    .exec(loginScenario, routePage) //Log into system as admin
    .exec(http("Add Route (Admin)")
      .post("/api/v1/adminrouteservice/adminroute") //Add route using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/RouteListAdmin/add_route_form.json")))
    .exec(
      http("Add Route")
        .post("/api/v1/routeservice/routes") //Add route using main service
        .body(RawFileBody("com/gatling/tests/RouteListAdmin/add_route_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Add Route by IDs")
        .post("/api/v1/routeservice/routes/byIds") //Add routes by id
        .body(RawFileBody("com/gatling/tests/RouteListAdmin/add_route_form.json"))
        .headers(apiV1Header))
    .pause(1)

  //Test scenario to check deletion endpoints
  val routeDelete: ScenarioBuilder = scenario("Admins Deleting Route")
    .exec(loginScenario, routePage)
    .exec(http("Delete Route (Admin)") //Delete route using admin service
      .delete("/api/v1/adminrouteservice/adminroute/0b23bd3e-876a-4af3-b920-c50a90c90b04")
      .headers(apiV1Header))
    .exec(http("Delete Route") //Delete route using main service
      .delete("/api/v1/routeservice/routes/d693a2c5-ef87-4a3c-bef8-600b43f62c68")
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check general route service endpoints
  val routeGeneral: ScenarioBuilder = scenario("Check General Route Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Admin Route Service Welcome")
        .get("/api/v1/adminrouteservice/welcome") //Get admin route service welcome
        .headers(apiV1Header))
    .exec(
      http("Get Route Service Welcome")
        .get("/api/v1/routeservice/welcome") //Get route service welcome
        .headers(apiV1Header))
    .exec(
      http("Get Routes")
        .get("/api/v1/routeservice/routes") //Get all routes
        .headers(apiV1Header))
    .exec(
      http("Get Routes by Route ID")
        .get("/api/v1/routeservice/routes/d693a2c5-ef87-4a3c-bef8-600b43f62c68") //Get routes by id
        .headers(apiV1Header))
    .exec(
      http("Get Routes by Start and End")
        .get("/api/v1/routeservice/routes/taiyuan/shanghai") //Get routes by start and end
        .headers(apiV1Header))
    .exec(
      http("Get Routes by Start and Terminal")
        .get("/api/v1/routeservice/routes/taiyuan/shanghai") //Get routes by start and terminal
        .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    routeAdd.inject(rampUsers(1).during(15)),
    routeDelete.inject(rampUsers(1).during(15)),
    routeGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}