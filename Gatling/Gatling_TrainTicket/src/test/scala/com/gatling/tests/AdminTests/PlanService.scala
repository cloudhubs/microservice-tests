package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class PlanService extends Simulation {

  //Test scenario to check route plan service endpoints
  val routePlan: ScenarioBuilder = scenario("Check Route Plan Endpoints")
    .exec(loginScenario) //Log into system as admin
    .exec(http("Add Cheapest Route")
      .post("/api/v1/routeplanservice/routePlan/cheapestRoute") //Add cheapest route
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/RouteListAdmin/add_route_form.json")))
    .exec(http("Add Quickest Route")
      .post("/api/v1/routeplanservice/routePlan/quickestRoute") //Add quickest route
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/RouteListAdmin/add_route_form.json")))
    .exec(http("Add Min Stop Stations")
      .post("/api/v1/routeplanservice/routePlan/minStopStations") //Add min stop station route
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/RouteListAdmin/add_route_form.json")))
    .exec(
      http("Get Route Plan Service Welcome")
        .get("/api/v1/routeplanservice/welcome")) //Get route plan service welcome

  //Test scenario to check travel plan service endpoints
  val travelPlan: ScenarioBuilder = scenario("Check Travel Plan Endpoints")
    .exec(loginScenario) //Log into system as admin
    .exec(http("Add Cheapest Travel Plan")
      .post("/api/v1/travelplanservice/travelPlan/cheapest") //Add cheapest travel plan
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json")))
    .exec(http("Add Quickest Travel Plan")
      .post("/api/v1/travelplanservice/travelPlan/quickest") //Add quickest travel plan
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json")))
    .exec(http("Add Min Stations Travel Plan")
      .post("/api/v1/travelplanservice/travelPlan/minStation") //Add min station travel plan
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json")))
    .exec(http("Add Transfer Result Travel Plan")
      .post("/api/v1/travelplanservice/travelPlan/transferResult") //Add transfer result travel plan
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json")))
    .exec(
      http("Get Travel Plan Service Welcome")
        .get("/api/v1/travelplanservice/welcome")) //Get travel plan service welcome

  //Run the test simulation with the scenarios
  setUp(
    routePlan.inject(rampUsers(1).during(10)),
    travelPlan.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
