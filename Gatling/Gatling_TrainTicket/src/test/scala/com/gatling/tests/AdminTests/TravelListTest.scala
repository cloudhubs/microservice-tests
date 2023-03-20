package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class TravelListTest extends Simulation {

  //Scenario that tests adding travel
  val travelAdd: ScenarioBuilder = scenario("Admins Adding Travel")
    .exec(loginScenario, travelPage) //Log into system as admin
    .exec(http("Add Travel")
      .post("/api/v1/admintravelservice/admintravel")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_invalid.json")))
    .pause(1)

  //Scenario that tests deleting travel
  val travelDelete: ScenarioBuilder = scenario("Admins Deleting Travel")
    .exec(loginScenario, travelPage)
    .exec(http("Delete Travel")
      .delete("/api/v1/admintravelservice/admintravel/Z1235")
      .headers(apiV1Header))
    .pause(1)

  val travelUpdate: ScenarioBuilder = scenario("Admins Updating Travel")
    .exec(loginScenario, travelPage) //Log into system as admin
    .exec(
      http("Update Travel")
        .put("/api/v1/admintravelservice/admintravel")
        .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_invalid.json"))
        .headers(apiV1Header))
    .pause(1)

  val travelGeneral = scenario("Check General Travel Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Admin Travel Service Welcome")
        .get("/api/v1/admintravelservice/welcome")
        .headers(apiV1Header))

  setUp(
    travelAdd.inject(rampUsers(1).during(15)),
    travelDelete.inject(rampUsers(1).during(15)),
    travelUpdate.inject(rampUsers(1).during(15)),
    travelGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}