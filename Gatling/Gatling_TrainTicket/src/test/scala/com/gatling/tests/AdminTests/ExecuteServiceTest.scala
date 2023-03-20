package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.AdminModules.pricePage
import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.{apiV1Header, loginScenario}
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*

class ExecuteServiceTest extends Simulation {

  val executeGeneral: ScenarioBuilder = scenario("Check General Execute Service Endpoints")
    .exec(loginScenario)
    .exec(http("Get Execute Service Welcome")
      .get("/api/v1/executeservice/welcome")
      .headers(apiV1Header))
    .exec(http("Get Execute Service by Order ID")
      .get("/api/v1/executeservice/execute/execute/4220e6bf-7c4b-4b74-9a02-f448b28b79be")
      .headers(apiV1Header))
    .exec(http("Get Execute Service Welcome")
      .get("/api/v1/executeservice/execute/collected/4220e6bf-7c4b-4b74-9a02-f448b28b79be")
      .headers(apiV1Header))
    .pause(1)

  setUp(
    executeGeneral.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)

}
