package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ScenarioBuilder

class PreserveTest extends Simulation {

  //Test scenario to check general preserve service endpoints
  val preserveGeneral: ScenarioBuilder = scenario("Check General Preserve Endpoints")
    .exec(loginScenario)
    .exec(http("Get Preserve Welcome")
      .get("/api/v1/preserveservice/welcome") //Get preserve service welcome
      .headers(apiV1Header))
    .exec(http("Get Preserve Other Welcome")
      .get("/api/v1/preserveotherservice/welcome") //Get preserve other service welcome
      .headers(apiV1Header))

  //Test scenario to check add (post) endpoints
  val preservePost: ScenarioBuilder = scenario("Check Preserve Post Endpoints")
    .exec(loginScenario)
    .exec(http("Post Preserve Service")
      .post("/api/v1/preserveservice/preserve") //Add preserve service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/Booking/booking_1st_class_form.json")))
    .exec(http("Post Preserve Other Service")
      .post("/api/v1/preserveotherservice/preserveOther") //Add preserve other service
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/Booking/booking_1st_class_form.json")))

  //Run the test simulation with the scenarios
  setUp(
    preservePost.inject(rampUsers(1).during(10)),
    preserveGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)

}
