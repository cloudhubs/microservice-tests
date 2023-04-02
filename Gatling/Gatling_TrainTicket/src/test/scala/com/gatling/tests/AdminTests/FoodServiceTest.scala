package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class FoodServiceTest extends Simulation {

  //Test scenario to check station food service endpoints
  val stationFoodService: ScenarioBuilder = scenario("Check Station Food Service")
    .exec(loginScenario)
    .exec(
      http("Get Station Food Service Welcome")
        .get("/api/v1/stationfoodservice/stationfoodstores/welcome")) //Get the station food service welcome
    .exec(
      http("Get Station Food Stores")
        .get("/api/v1/stationfoodservice/stationfoodstores")) //Get all food stores
    .exec(
      http("Get Station Food By ID") //Get food store by ID
        .get("/api/v1/stationfoodservice/stationfoodstores/3fd67b64-3c80-4b51-823f-f1219247827f")
        .headers(apiV1Header))
    .exec(
      http("Post Station Food Store")
        .post("/api/v1/stationfoodservice/stationfoodstores") //Add a food store
        .body(RawFileBody("com/gatling/tests/FoodService/station_food.json"))
        .headers(apiV1Header))
    .exec(
      http("Get Station Food Store By Store ID") //Get a food store by ID
        .get("/api/v1/stationfoodservice/stationfoodstores/bystoreid/e7431fd9-7202-4834-90d6-e33f045dfd1a"))

  //Test scenario to check train food service endpoints
  val trainFoodService: ScenarioBuilder = scenario("Check Train Food Service")
    .exec(
      http("Get Train Food Service Welcome")
        .get("/api/v1/trainfoodservice/trainfoods/welcome")) //Get train food service welcome
    .exec(
      http("Get Train Food")
        .get("/api/v1/trainfoodservice/trainfoods")) //Get all train foods
    .exec(
      http("Get Train Food by Trip ID")
        .get("/api/v1/trainfoodservice/trainfoods/G1234")) //Get train foods by ID

  //Test scenario to check food service endpoints
  val foodService: ScenarioBuilder = scenario("Check Food Service Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Food Service Welcome")
        .get("/api/v1/foodservice/welcome")) //Get food service welcome
    .exec(
      http("Get Food Service Test Delivery")
        .get("/api/v1/foodservice/test_send_delivery")) //Get food service test delivery
    .exec(
      http("Get Food Service Orders")
        .get("/api/v1/foodservice/orders")) //Get all food service orders
    .exec(
      http("Get Food Service Orders by Order ID")
        .get("/api/v1/foodservice/orders/a7e77fa2-8197-4682-9f6e-85e7d34d90a8")) //Get food service orders by ID
    .exec(
      http("Get Food Service Orders by Date") //Get food service orders by date
        .get("/api/v1/foodservice/foods/08:00-21:00/shanghai/taiyuan/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))
    .exec(
      http("Add Food Order")
        .post("/api/v1/foodservice/orders") //Add food service order
        .body(RawFileBody("com/gatling/tests/FoodService/food_service_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Add Food Order Batch")
        .post("/api/v1/foodservice/createOrderBatch") //Add food service order batch
        .body(RawFileBody("com/gatling/tests/FoodService/food_service_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Update Food Order")
        .put("/api/v1/foodservice/orders") //Update food service order
        .body(RawFileBody("com/gatling/tests/FoodService/food_service_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Delete Food Order") //Add food service order
        .delete("/api/v1/foodservice/orders/a7e77fa2-8197-4682-9f6e-85e7d34d90a8"))

  //Run the test simulation with the scenarios
  setUp(
    stationFoodService.inject(rampUsers(1).during(10)),
    trainFoodService.inject(rampUsers(1).during(10)),
    foodService.inject(rampUsers(1).during(10))
  ).protocols(httpProtocolTrainTicket)
}
