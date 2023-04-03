package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class GeneralTest extends Simulation {

  //Test scenario to check seat service endpoints
  val seatService: ScenarioBuilder = scenario("Check Seat Service Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Seat Service Welcome") //Get seat service welcome
        .get("/api/v1/seatservice/welcome"))
    .exec(
      http("Add Seat Service Seats")
        .post("/api/v1/seatservice/seats") //Add seat using seat service
        .body(RawFileBody("com/gatling/tests/FoodService/station_food.json"))
        .headers(apiV1Header))
    .exec(
      http("Add Seat Service Left Tickets")
        .post("/api/v1/seatservice/seats/left_tickets") //Add left over tickets
        .body(RawFileBody("com/gatling/tests/FoodService/station_food.json"))
        .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    seatService.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)

}
