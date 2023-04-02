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

  //Test scenario to check add (post) endpoints
  val travelAdd: ScenarioBuilder = scenario("Admins Adding Travel")
    .exec(loginScenario, travelPage) //Log into system as admin
    .exec(http("Add Travel")
      .post("/api/v1/admintravelservice/admintravel") //Add travel using admin service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json")))
    .pause(1)

  //Test scenario to check deletion endpoints
  val travelDelete: ScenarioBuilder = scenario("Admins Deleting Travel")
    .exec(loginScenario, travelPage)
    .exec(http("Delete Travel")
      .delete("/api/v1/admintravelservice/admintravel/Z1235") //Delete travel using id
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check update (put) endpoints
  val travelUpdate: ScenarioBuilder = scenario("Admins Updating Travel")
    .exec(loginScenario, travelPage) //Log into system as admin
    .exec(http("Update Travel")
      .put("/api/v1/admintravelservice/admintravel") //Update travel using admin service
      .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json"))
      .headers(apiV1Header))
    .pause(1)

  //Test scenario to check general travel service endpoints
  val travelGeneral: ScenarioBuilder = scenario("Check General Travel Endpoints")
    .exec(loginScenario)
    .exec(http("Get Admin Travel Service Welcome")
      .get("/api/v1/admintravelservice/welcome") //Get admin travel service welcome
      .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    travelAdd.inject(rampUsers(100).during(15)),
    travelDelete.inject(rampUsers(100).during(15)),
    travelUpdate.inject(rampUsers(100).during(15)),
    travelGeneral.inject(rampUsers(100).during(15))
  ).protocols(httpProtocolTrainTicket)
}