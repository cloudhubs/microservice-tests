package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*

class ConsignServiceTest extends Simulation {

  val consignPriceGeneral = scenario("Check General Consign Price Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Consign Price Welcome")
        .get("/api/v1/consignpriceservice/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Consign Price by Weight")
        .get("/api/v1/consignpriceservice/consignprice/1.0/2.0")
        .headers(apiV1Header))
    .exec(
      http("Get Consign Price")
        .get("/api/v1/consignpriceservice/consignprice/price")
        .headers(apiV1Header))
    .exec(
      http("Get Consign Price Configs")
        .get("/api/v1/consignpriceservice/consignprice/config")
        .headers(apiV1Header))
    .exec(
      http("Get Consign Price Service")
        .get("/api/v1/consignpriceservice/consignprice")
        .headers(apiV1Header))

  val consignGeneral = scenario("Check General Consign Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Consign Welcome")
        .get("/api/v1/consignservice/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Consign by Account ID")
        .get("/api/v1/consignservice/consigns/account/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header))
    .exec(
      http("Get Consign by Order ID")
        .get("/api/v1/consignservice/consigns/order/301f39ba-f31d-4795-bac2-cbc8909a7e97")
        .headers(apiV1Header))
    .exec(
      http("Get Consign by Consignee")
        .get("/api/v1/consignservice/consigns/fdse_microservice")
        .headers(apiV1Header))

  val consignAdd = scenario("Add Consign Scenario")
    .exec(loginScenario)
    .exec(
      http("Add Consign Price")
        .post("/api/v1/consignpriceservice/consignprice")
        .body(RawFileBody("com/gatling/tests/ConsignService/consign_price.json"))
        .headers(apiV1Header))
    .exec(
      http("Add Consign")
        .post("/api/v1/consignservice/consigns")
        .body(RawFileBody("com/gatling/tests/ConsignService/consign_form.json"))
        .headers(apiV1Header))

  val consignUpdate = scenario("Update Consign Scenario")
    .exec(loginScenario)
    .exec(
      http("Update Consign")
        .post("/api/v1/consignservice/consigns")
        .body(RawFileBody("com/gatling/tests/ConsignService/consign_price.json"))
        .headers(apiV1Header))

  setUp(
    consignGeneral.inject(rampUsers(1).during(10)),
    consignPriceGeneral.inject(rampUsers(1).during(10)),
    consignAdd.inject(rampUsers(1).during(10)),
    consignUpdate.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
