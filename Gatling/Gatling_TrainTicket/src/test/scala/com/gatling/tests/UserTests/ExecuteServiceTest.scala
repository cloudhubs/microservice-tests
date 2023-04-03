package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*

class ExecuteServiceTest extends Simulation {

  //Test scenario to check general execute service endpoints
  val executeGeneral: ScenarioBuilder = scenario("Check General Execute Service Endpoints")
    .exec(loginScenario)
    .exec(http("Get Execute Service Welcome")
      .get("/api/v1/executeservice/welcome") //Get execute service welcome
      .headers(apiV1Header))
    .exec(ticketPage)
    .exec(http("Collect Ticket") //Collect ticket endpoint
      .get("/api/v1/executeservice/execute/collected/4220e6bf-7c4b-4b74-9a02-f448b28b79be")
      .headers(apiV1Header))
    .exec(stationPage)
    .exec(http("Enter Station") //Enter station endpoint
      .get("/api/v1/executeservice/execute/execute/4220e6bf-7c4b-4b74-9a02-f448b28b79be")
      .headers(apiV1Header))
    .pause(1)

  //Run the test simulation with the scenarios
  setUp(
    executeGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)

}
