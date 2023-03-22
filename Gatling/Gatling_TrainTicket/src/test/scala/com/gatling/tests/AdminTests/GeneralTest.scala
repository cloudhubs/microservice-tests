package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class GeneralTest extends Simulation {

  val seatService = scenario("Check Seat Service Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Seat Service Welcome")
        .get("/api/v1/seatservice/welcome"))
    .exec(
      http("Add Seat Service Seats")
        .post("/api/v1/seatservice/seats")
        .body(RawFileBody("com/gatling/tests/FoodService/station_food.json"))
        .headers(apiV1Header))
    .exec(
      http("Add Seat Service Left Tickets")
        .post("/api/v1/seatservice/seats/left_tickets")
        .body(RawFileBody("com/gatling/tests/FoodService/station_food.json"))
        .headers(apiV1Header))

  val ticketInfoService = scenario("Check Ticket Info Service Endpoints")
    .exec(
      http("Get Ticket Service by Name")
        .get("/api/v1/ticketinfoservice/ticketinfo/test"))
    .exec(
      http("Add Ticket Info Service")
        .post("/api/v1/ticketinfoservice/ticketinfo")
        .body(RawFileBody("com/gatling/tests/FoodService/station_food.json")))

  setUp(
    seatService.inject(rampUsers(1).during(10)),
    ticketInfoService.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)

}
