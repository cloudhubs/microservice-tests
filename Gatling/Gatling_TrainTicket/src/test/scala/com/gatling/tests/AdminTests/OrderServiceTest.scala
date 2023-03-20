package com.gatling.tests.AdminTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*

class OrderServiceTest extends Simulation {

  val orderOtherGeneral = scenario("Check General Order Other Service Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Get Order Other Service Welcome")
        .get("/api/v1/orderOtherService/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Date and Train")
        .get("/api/v1/orderOtherService/orderOther/2022-10-01 00:00:00/K1235")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Order ID")
        .get("/api/v1/orderOtherService/orderOther/price/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Order ID")
        .get("/api/v1/orderOtherService/orderOther/orderPay/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Order ID")
        .get("/api/v1/orderOtherService/orderOther/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Status")
        .get("/api/v1/orderOtherService/orderOther/status/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28/1")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service by Account")
        .get("/api/v1/orderOtherService/orderOther/security/2022-10-01 00:00:00/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header))
    .exec(
      http("Get Order Other Service")
        .get("/api/v1/orderOtherService/orderOther")
        .headers(apiV1Header))

  val orderOtherPost = scenario("Check Post Order Other Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Post Other Order Ticket")
        .post("/api/v1/orderOtherService/orderOther/tickets")
        .body(RawFileBody("com/gatling/tests/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order")
        .post("/api/v1/orderOtherService/orderOther")
        .body(RawFileBody("com/gatling/tests/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order Admin")
        .post("/api/v1/orderOtherService/orderOther/admin")
        .body(RawFileBody("com/gatling/tests/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order Query")
        .post("/api/v1/orderOtherService/orderOther/query")
        .body(RawFileBody("com/gatling/tests/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Other Order Refresh")
        .post("/api/v1/orderOtherService/orderOther/refresh")
        .body(RawFileBody("com/gatling/tests/other_order_service.json"))
        .headers(apiV1Header))

  val orderOtherUpdate = scenario("Check Update Order Other Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Update Other Order")
        .put("/api/v1/orderOtherService/orderOther")
        .body(RawFileBody("com/gatling/tests/other_order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Update Other Order Admin")
        .put("/api/v1/orderOtherService/orderOther/admin")
        .body(RawFileBody("com/gatling/tests/other_order_service.json"))
        .headers(apiV1Header))

  val orderOtherDelete = scenario("Check Delete Order Other Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Delete Other Order")
        .delete("/api/v1/orderOtherService/orderOther/0e29cc0d-fefb-4175-b8c5-bea1ea6aea28")
        .headers(apiV1Header))

  val orderGeneral = scenario("Check General Order Service Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Get Order  Service Welcome")
        .get("/api/v1/orderservice/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Date and Train")
        .get("/api/v1/orderservice/order/2022-10-01 00:00:00/G1237")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Order ID")
        .get("/api/v1/orderservice/order/price/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Order ID")
        .get("/api/v1/orderservice/order/orderPay/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Order ID")
        .get("/api/v1/orderservice/order/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Status")
        .get("/api/v1/orderservice/order/status/6279c919-e6e3-4d2b-8f72-81e06971490e/1")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service by Account")
        .get("/api/v1/orderservice/order/security/2022-10-01 00:00:00/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
        .headers(apiV1Header))
    .exec(
      http("Get Order Service")
        .get("/api/v1/orderservice/order")
        .headers(apiV1Header))

  val orderPost = scenario("Check Post Order Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Post Order Ticket")
        .post("/api/v1/orderservice/order/tickets")
        .body(RawFileBody("com/gatling/tests/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order")
        .post("/api/v1/orderservice/order")
        .body(RawFileBody("com/gatling/tests/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Admin")
        .post("/api/v1/orderservice/order/admin")
        .body(RawFileBody("com/gatling/tests/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Query")
        .post("/api/v1/orderservice/order/query")
        .body(RawFileBody("com/gatling/tests/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Order Refresh")
        .post("/api/v1/orderservice/order/refresh")
        .body(RawFileBody("com/gatling/tests/order_service.json"))
        .headers(apiV1Header))

  val orderUpdate = scenario("Check Update Order Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Update Order")
        .put("/api/v1/orderservice/order")
        .body(RawFileBody("com/gatling/tests/order_service.json"))
        .headers(apiV1Header))
    .exec(
      http("Update Order Admin")
        .put("/api/v1/orderservice/order/admin")
        .body(RawFileBody("com/gatling/tests/order_service.json"))
        .headers(apiV1Header))

  val orderDelete = scenario("Check Delete Order Endpoints")
    .exec(adminLoginScenario)
    .exec(
      http("Delete Order")
        .delete("/api/v1/orderservice/order/6279c919-e6e3-4d2b-8f72-81e06971490e")
        .headers(apiV1Header))

  setUp(
    orderOtherGeneral.inject(rampUsers(1).during(15)),
    orderOtherPost.inject(rampUsers(1).during(15)),
    orderOtherUpdate.inject(rampUsers(1).during(15)),
    orderOtherDelete.inject(rampUsers(1).during(15)),
    orderGeneral.inject(rampUsers(1).during(15)),
    orderPost.inject(rampUsers(1).during(15)),
    orderUpdate.inject(rampUsers(1).during(15)),
    orderDelete.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}
