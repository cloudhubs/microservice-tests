package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ScenarioBuilder

class TravelServiceTest extends Simulation {

  //Test scenario to check general travel endpoints
  val travelGeneral: ScenarioBuilder = scenario("Check General Travel Service Endpoints")
    .exec(http("Get Travel Service Welcome")
      .get("/api/v1/travelservice/welcome")) //Get travel service welcome
    .exec(http("Get Train Types by ID")
      .get(s"/api/v1/travelservice/train_types/G1234")) //Get train types by trip id
    .exec(http("Get Routes by ID")
      .get(s"/api/v1/travelservice/routes/G1234")) //Get routes by trip id
    .exec(http("Get Trips by ID")
      .get(s"/api/v1/travelservice/trips/G1234")) //Get trips by trip id
    .exec(http("Get Travel Service Trips")
      .get("/api/v1/travelservice/trips")) //Get all trips
    .exec(http("Get Travel Service Admin Trips")
      .get("/api/v1/travelservice/admin_trip")) //Get all admin trips

  //Test scenario to check add (post) endpoints
  val travelPost: ScenarioBuilder = scenario("Check Post Travel Endpoints")
    .exec(loginScenario)
    .exec(http("Post Travel Routes")
      .post("/api/v1/travelservice/trips/routes") //Add travel route
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_types.json"))
      .headers(apiV1Header))
    .exec(http("Post Travel Trips")
      .post("/api/v1/travelservice/trips") //Add travel trips
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_types.json"))
      .headers(apiV1Header))
    .exec(http("Post Travel Left Parallel")
      .post("/api/v1/travelservice/trips/left_parallel") //Add travel left parallel
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_form.json"))
      .headers(apiV1Header))
    .exec(http("Post Travel Details")
      .post("/api/v1/travelservice/trip_detail") //Add trip details
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_types.json"))
      .headers(apiV1Header))

  //Test scenario to check other travel endpoints
  val travelOther: ScenarioBuilder = scenario("Check Other Travel Endpoints")
    .exec(loginScenario)
    .exec(http("Put Travel Routes")
      .put("/api/v1/travelservice/trips") //Update travel route
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_form.json"))
      .headers(apiV1Header))
    .exec(http("Delete Travel")
      .delete("/api/v1/travelservice/trips/G1236") //Delete travel route
      .headers(apiV1Header))

  //Test scenario to check general travel 2 endpoints
  val travel2General: ScenarioBuilder = scenario("Check General Travel 2 Service Endpoints")
    .exec(
      http("Get Travel 2 Service Welcome")
        .get("/api/v1/travel2service/welcome")) //Get travel 2 service welcome
    .exec(
      http("Get Train Types by ID")
        .get(s"/api/v1/travel2service/train_types/Z1234")) //Get travel 2 train types by id
    .exec(
      http("Get Routes by ID")
        .get(s"/api/v1/travel2service/routes/Z1234")) //Get travel 2 routes by id
        .exec(
      http("Get Trips by ID")
        .get(s"/api/v1/travel2service/trips/Z1234")) //Get travel 2 trips by id
    .exec(
      http("Get Travel Service Trips")
        .get("/api/v1/travel2service/trips")) //Get travel 2 trips
    .exec(
      http("Get Travel Service Admin Trips")
        .get("/api/v1/travel2service/admin_trip")) //Get travel 2 admin trips

  //Test scenario to check add (post) endpoints
  val travel2Post: ScenarioBuilder = scenario("Check Post Travel 2 Endpoints")
    .exec(loginScenario)
    .exec(http("Post Travel 2 Routes")
      .post("/api/v1/travel2service/trips/routes") //Add travel 2 routes
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_routes.json"))
      .headers(apiV1Header))
    .exec(http("Post Travel 2 Trips")
      .post("/api/v1/travel2service/trips") //Add travel 2 trips
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_trips.json"))
      .headers(apiV1Header))
    .exec(http("Post Travel 2 Details")
      .post("/api/v1/travel2service/trip_detail") //Add travel 2 trip details
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_form.json"))
      .headers(apiV1Header))

  //Test scenario to check other endpoints
  val travel2Other: ScenarioBuilder = scenario("Check Other Travel 2 Endpoints")
    .exec(loginScenario)
    .exec(http("Post Travel 2 Routes")
      .put("/api/v1/travel2service/trips") //Update travel 2 routes
      .body(RawFileBody("com/gatling/tests/TravelService/travel_service_trips.json"))
      .headers(apiV1Header))
    .exec(http("Delete Travel 2")
      .delete("/api/v1/travel2service/trips/Z1234") //Delete travel 2 route
      .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    travelGeneral.inject(rampUsers(1).during(15)),
    travelPost.inject(rampUsers(1).during(15)),
    travelOther.inject(rampUsers(1).during(15)),
    travel2General.inject(rampUsers(1).during(15)),
    travel2Post.inject(rampUsers(1).during(15)),
    travel2Other.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}
