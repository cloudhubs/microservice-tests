package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class BasicServiceTest extends Simulation {

  val generalTest = scenario("Check Basic Tests")
    .exec(loginScenario)
    .exec(
      http("Get Basic Service Welcome")
        .get("/api/v1/basicservice/welcome"))
    .exec(
      http("Get Admin Basic Service Welcome")
        .get("/api/v1/adminbasicservice/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Basic Service by Station Name")
        .get("/api/v1/basicservice/basic/nanjing"))

  val postTest = scenario("Check Post Basic Tests")
    .exec(loginScenario)
    .exec(
      http("Post Travel")
        .post("/api/v1/basicservice/basic/travel")
        .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Travels")
        .post("/api/v1/basicservice/basic/travels")
        .body(RawFileBody("com/gatling/tests/TravelListAdmin/update_travel_form.json"))
        .headers(apiV1Header))

  setUp(
    generalTest.inject(rampUsers(1).during(15)),
    postTest.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
