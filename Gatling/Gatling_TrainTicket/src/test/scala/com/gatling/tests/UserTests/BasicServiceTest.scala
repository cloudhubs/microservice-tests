package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ScenarioBuilder

class BasicServiceTest extends Simulation {

  //Test scenario to check general service endpoints
  val generalService: ScenarioBuilder = scenario("Check Basic Service Endpoints")
    .exec(loginScenario)
    .exec(http("Get Basic Service Welcome")
      .get("/api/v1/basicservice/welcome")) //Get basic service welcome
    .exec(http("Get Admin Basic Service Welcome")
      .get("/api/v1/adminbasicservice/welcome") //Get admin basic service welcome
      .headers(apiV1Header))
    .exec(http("Get Basic Service by Station Name")
      .get("/api/v1/basicservice/basic/nanjing")) //Get basic service by station name

  //Test scenario to check add (post) endpoints
  val postService: ScenarioBuilder = scenario("Check Post Basic Service Endpoints")
    .exec(loginScenario)
    .exec(http("Post Travel")
      .post("/api/v1/basicservice/basic/travel") //Add travel using basic service
      .body(RawFileBody("com/gatling/tests/basic_service.json"))
      .headers(apiV1Header))
    .exec(http("Post Travels")
      .post("/api/v1/basicservice/basic/travels") //Add travels using basic service
      .body(RawFileBody("com/gatling/tests/basic_service.json"))
      .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    generalService.inject(rampUsers(1).during(15)),
    postService.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
