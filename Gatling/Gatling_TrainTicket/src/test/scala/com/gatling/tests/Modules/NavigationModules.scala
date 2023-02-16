package com.gatling.tests.Modules

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

import HeaderModules.*

object NavigationModules {

  val homePage = exec(http("Home Page")
    .get("/index.html")
    .headers(mainPageHeader))
    .pause(2)

  val viewOrderListPage = exec(http("View Order List")
    .get("/client_order_list.html")
    .headers(orderListHeader)
    .resources(http("Refresh Page Test")
      .post("/api/v1/orderservice/order/refresh")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/OrderList/orderlistpayticket/account_request.json")),
      http("Refresh Page")
        .post("/api/v1/orderOtherService/orderOther/refresh")
        .headers(apiV1Header)
        .body(RawFileBody("com/gatling/tests/OrderList/orderlistpayticket/account_request.json"))))
    .pause(8)

  val payTicket = exec(http("Confirm Payment")
    .post("/api/v1/inside_pay_service/inside_payment")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/OrderList/orderlistpayticket/payment_request.json")))
    .pause(4)

  val cancelOrder = exec(http("Select Cancel Order")
    .get("/api/v1/cancelservice/cancel/refound/cfd74f18-9135-422f-8c16-73aa8e019059")
    .headers(apiV1Header))
    .pause(4)
    .exec(http("View Cancellation Message")
      .get("/api/v1/cancelservice/cancel/cfd74f18-9135-422f-8c16-73aa8e019059/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
      .headers(apiV1Header))
    .pause(2)
  
  val changeOrder = exec(http("Select Change Order")
    .post("/api/v1/travelservice/trips/left")
    .headers(headers_6)
    .body(RawFileBody("com/gatling/tests/orderlistchangeordervalid/0008_request.json")))
    .pause(4)
    .exec(http("Confirm Rebook")
      .post("/api/v1/rebookservice/rebook")
      .headers(headers_6)
      .body(RawFileBody("com/gatling/tests/orderlistchangeordervalid/0009_request.json")))
    .pause(7)
}
