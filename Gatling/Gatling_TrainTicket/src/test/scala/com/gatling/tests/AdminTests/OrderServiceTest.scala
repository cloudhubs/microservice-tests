package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder

class OrderServiceTest extends Simulation {

  //Test scenario to check general order other endpoints
  val orderOtherGeneral: ScenarioBuilder = scenario("Check General Order Other Service Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Order Other Service Welcome")
        .get("/api/v1/orderOtherService/welcome") //Get order other service welcome
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Date and Train")
        .get("/api/v1/orderOtherService/orderOther/2022-10-01 00:00:00/K1235") //Get other order by date and train
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Order ID") //Get other order price by id
        .get("/api/v1/orderOtherService/orderOther/price/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Order ID") //Get other order pay by id
        .get("/api/v1/orderOtherService/orderOther/orderPay/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Order ID") //Get other order by id
        .get("/api/v1/orderOtherService/orderOther/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Status") //Get other order by status
        .get("/api/v1/orderOtherService/orderOther/status/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28/1")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Account") //Get other order by account id
        .get("/api/v1/orderOtherService/orderOther/security/2022-10-01 00:00:00/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service")
        .get("/api/v1/orderOtherService/orderOther") //Get all other orders
        .headers(apiV1Header))

  //Test scenario to check add (post) scenarios
  val orderOtherPost: ScenarioBuilder = scenario("Check Post Order Other Endpoints")
    .exec(loginScenario)
    .exec(
      http("Post Other Order Ticket")
        .post("/api/v1/orderOtherService/orderOther/tickets") //Add other order ticket
        .body(RawFileBody("com/gatling/tests/OrderService/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order")
        .post("/api/v1/orderOtherService/orderOther") //Add other order
        .body(RawFileBody("com/gatling/tests/OrderService/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order Admin")
        .post("/api/v1/orderOtherService/orderOther/admin") //Add admin other order
        .body(RawFileBody("com/gatling/tests/OrderService/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order Query")
        .post("/api/v1/orderOtherService/orderOther/query") //Add other order query
        .body(RawFileBody("com/gatling/tests/OrderService/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order Refresh")
        .post("/api/v1/orderOtherService/orderOther/refresh") //Add other order refresh
        .body(RawFileBody("com/gatling/tests/OrderService/other_order_service.json"))
        .headers(apiV1Header))

  //Test scenario to check update (put) endpoints
  val orderOtherUpdate: ScenarioBuilder = scenario("Check Update Order Other Endpoints")
    .exec(loginScenario)
    .exec(
      http("Update Other Order")
        .put("/api/v1/orderOtherService/orderOther") //Update other order
        .body(RawFileBody("com/gatling/tests/OrderService/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Update Other Order Admin")
        .put("/api/v1/orderOtherService/orderOther/admin") //Update admin other order
        .body(RawFileBody("com/gatling/tests/OrderService/other_order_service.json"))
        .headers(apiV1Header))

  //Test scenario to check deletion endpoints
  val orderOtherDelete: ScenarioBuilder = scenario("Check Delete Order Other Endpoints")
    .exec(loginScenario)
    .exec(
      http("Delete Other Order") //Delete other order by id
        .delete("/api/v1/orderOtherService/orderOther/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))

  //Test scenario to check general order service endpoints
  val orderGeneral: ScenarioBuilder = scenario("Check General Order Service Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Order  Service Welcome")
        .get("/api/v1/orderservice/welcome") //Get order service welcome
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Date and Train")
        .get("/api/v1/orderservice/order/2022-10-01 00:00:00/G1237") //Get order by date and train
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Order ID")
        .get("/api/v1/orderservice/order/price/6279c919-e6e3-4d2b-8f72-81e06971490e") //Get order price by id
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Order ID")
        .get("/api/v1/orderservice/order/orderPay/6279c919-e6e3-4d2b-8f72-81e06971490e") //Get order pay by id
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Order ID")
        .get("/api/v1/orderservice/order/6279c919-e6e3-4d2b-8f72-81e06971490e") //Get order by id
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Status")
        .get("/api/v1/orderservice/order/status/6279c919-e6e3-4d2b-8f72-81e06971490e/1") //Get order by status
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Account") //Get order by account
        .get("/api/v1/orderservice/order/security/2022-10-01 00:00:00/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service")
        .get("/api/v1/orderservice/order") //Get all orders
        .headers(apiV1Header))

  //Test scenario to check add (post) scenarios
  val orderPost: ScenarioBuilder = scenario("Check Post Order Endpoints")
    .exec(loginScenario)
    .exec(
      http("Post Order Ticket")
        .post("/api/v1/orderservice/order/tickets") //Add order tickets
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order")
        .post("/api/v1/orderservice/order") //Add order
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Admin")
        .post("/api/v1/orderservice/order/admin") //Add admin order
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Query")
        .post("/api/v1/orderservice/order/query") //Add order query
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Refresh")
        .post("/api/v1/orderservice/order/refresh") //Add order refresh
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))

  //Test scenario to check update (put) endpoints
  val orderUpdate: ScenarioBuilder = scenario("Check Update Order Endpoints")
    .exec(loginScenario)
    .exec(
      http("Update Order")
        .put("/api/v1/orderservice/order") //Update order
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Update Order Admin")
        .put("/api/v1/orderservice/order/admin") //Update admin order
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))

  //Test scenario to check deletion endpoints
  val orderDelete: ScenarioBuilder = scenario("Check Delete Order Endpoints")
    .exec(loginScenario)
    .exec(
      http("Delete Order") //Delete order by id
        .delete("/api/v1/orderservice/order/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))

  //Test scenario to check wait order service
  val waitOrderService: ScenarioBuilder = scenario("Check Wait Order Service Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Wait Order Service Welcome")
        .get("/api/v1/waitorderservice/welcome") //Get wait order welcome
        .headers(apiV1Header))
    .exec(
      http("Get Wait Order Service Orders")
        .get("/api/v1/waitorderservice/orders") //Get wait orders
        .headers(apiV1Header))
    .exec(
      http("Get Wait Order Service Waitlist")
        .get("/api/v1/waitorderservice/waitlistorders") //Get wait order wait list
        .headers(apiV1Header))
    .exec(
      http("Update Wait Order")
        .post("/api/v1/waitorderservice/order") //Update wait order
        .body(RawFileBody("com/gatling/tests/OrderService/order_service.json"))
        .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    orderOtherGeneral.inject(rampUsers(5).during(30)),
    orderOtherPost.inject(rampUsers(5).during(30)),
    orderOtherUpdate.inject(rampUsers(5).during(30)),
    orderOtherDelete.inject(rampUsers(5).during(30)),
    orderGeneral.inject(rampUsers(5).during(30)),
    orderPost.inject(rampUsers(5).during(30)),
    orderUpdate.inject(rampUsers(5).during(30)),
    orderDelete.inject(rampUsers(5).during(30)),
    waitOrderService.inject(rampUsers(5).during(30))
  ).protocols(httpProtocolTrainTicket)
}
