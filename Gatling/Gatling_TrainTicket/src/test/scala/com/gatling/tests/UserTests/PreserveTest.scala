package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class PreserveTest extends Simulation {

  val preserveGeneral = scenario("Check General Preserve Endpoints")
    .exec(loginScenario)
    .exec(http("Get Preserve Welcome")
      .get("/api/v1/preserveservice/welcome")
      .headers(apiV1Header))
    .exec(http("Get Preserve Other Welcome")
      .get("/api/v1/preserveotherservice/welcome")
      .headers(apiV1Header))

  val preservePost = scenario("Check Preserve Post Endpoints")
    .exec(loginScenario)
    .exec(http("Post Preserve Service")
      .post("/api/v1/preserveservice/preserve")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/Booking/booking_1st_class_form.json")))
    .exec(http("Post Preserve Other Service")
      .post("/api/v1/preserveotherservice/preserveOther")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/Booking/booking_1st_class_form.json")))

  setUp(
    preservePost.inject(rampUsers(1).during(10)),
    preserveGeneral.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)

}
