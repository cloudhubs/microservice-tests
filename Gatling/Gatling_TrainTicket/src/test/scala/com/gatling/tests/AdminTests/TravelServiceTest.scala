package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*

class TravelServiceTest extends Simulation {

  val tripId = "G1236"
  val tripId2 = "Z1234"

  val travelGeneral = scenario("Check General Travel Service Endpoints")
    .exec(
      http("Get Travel Service Welcome")
        .get("/api/v1/travelservice/welcome"))
    .exec(
      http("Get Train Types by ID")
        .get(s"/api/v1/travelservice/train_types/${tripId}"))
    .exec(
      http("Get Routes by ID")
        .get(s"/api/v1/travelservice/routes/${tripId}"))
    .exec(
      http("Get Trips by ID")
        .get(s"/api/v1/travelservice/trips/${tripId}"))
    .exec(
      http("Get Travel Service Trips")
        .get("/api/v1/travelservice/trips"))
    .exec(
      http("Get Travel Service Admin Trips")
        .get("/api/v1/travelservice/admin_trip"))

  val travelPost = scenario("Check Post Travel Endpoints")
    .exec(
      http("Post Travel Routes")
        .post("/api/v1/travelservice/trips/routes")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Post Travel Trips")
        .post("/api/v1/travelservice/trips")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Post Travel Left")
        .post("/api/v1/travelservice/left")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Post Travel Left Parallel")
        .post("/api/v1/travelservice/trips/left_parallel")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Post Travel Details")
        .post("/api/v1/travelservice/trip_detail")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))

  val travelOther = scenario("Check Other Travel Endpoints")
    .exec(
      http("Post Travel Routes")
        .put("/api/v1/travelservice/trips")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Delete Travel")
        .delete("/api/v1/travelservice/trips/G1236"))

  val travel2General = scenario("Check General Travel 2 Service Endpoints")
    .exec(
      http("Get Travel 2 Service Welcome")
        .get("/api/v1/travel2service/welcome"))
    .exec(
      http("Get Train Types by ID")
        .get(s"/api/v1/travel2service/train_types/${tripId2}"))
    .exec(
      http("Get Routes by ID")
        .get(s"/api/v1/travel2service/routes/${tripId2}"))
    .exec(
      http("Get Trips by ID")
        .get(s"/api/v1/travel2service/trips/${tripId2}"))
    .exec(
      http("Get Travel Service Trips")
        .get("/api/v1/travel2service/trips"))
    .exec(
      http("Get Travel Service Admin Trips")
        .get("/api/v1/travel2service/admin_trip"))

  val travel2Post = scenario("Check Post Travel 2 Endpoints")
    .exec(
      http("Post Travel 2 Routes")
        .post("/api/v1/travel2service/trips/routes")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Post Travel 2 Trips")
        .post("/api/v1/travel2service/trips")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Post Travel 2 Left")
        .post("/api/v1/travel2service/left")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Post Travel 2 Details")
        .post("/api/v1/travel2service/trip_detail")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))

  val travel2Other = scenario("Check Other Travel 2 Endpoints")
    .exec(
      http("Post Travel 2 Routes")
        .put("/api/v1/travel2service/trips")
        .body(RawFileBody("com/gatling/tests/travel_service_form.json")))
    .exec(
      http("Delete Travel")
        .delete("/api/v1/travel2service/trips/Z1234"))

  setUp(
    travelGeneral.inject(rampUsers(1).during(15)),
    travelPost.inject(rampUsers(1).during(15)),
    travelOther.inject(rampUsers(1).during(15)),
    travel2General.inject(rampUsers(1).during(15)),
    travel2Post.inject(rampUsers(1).during(15)),
    travel2Other.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}
