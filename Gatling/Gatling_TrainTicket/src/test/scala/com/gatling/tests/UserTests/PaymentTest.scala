package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*
import com.gatling.tests.Modules.LoginModule.*

class PaymentTest extends Simulation {

  val paymentGeneral = scenario("Check General Payment Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Inside Payment Welcome")
        .get("/api/v1/inside_pay_service/welcome")
        .headers(apiV1Header))
    .exec(
      http("Get Payment Welcome")
        .get("/api/v1/paymentservice/welcome")
        .headers(apiV1Header))

  val paymentGet = scenario("Check Get Payment Endpoints")
    .exec(loginScenario)
    .exec(
      http("Get Inside Payment by User ID")
        .get("/api/v1/inside_pay_service/inside_payment/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f/10000.0")
        .headers(apiV1Header))
    .exec(
      http("Get Inside Payment")
        .get("/api/v1/inside_pay_service/inside_payment/payment")
        .headers(apiV1Header))
    .exec(
      http("Get Inside Payment Account")
        .get("/api/v1/inside_pay_service/inside_payment/account")
        .headers(apiV1Header))
    .exec(
      http("Get Inside Payment Money")
        .get("/api/v1/inside_pay_service/inside_payment/money")
        .headers(apiV1Header))
    .exec(
      http("Get Payment")
        .get("/api/v1/paymentservice/payment")
        .headers(apiV1Header))

  val paymentPost = scenario("Check Post Payment Endpoints")
    .exec(loginScenario)
    .exec(
      http("Post Inside Payment")
        .post("/api/v1/inside_pay_service/inside_payment")
        .body(RawFileBody("com/gatling/tests/payment_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Inside Payment Account")
        .post("/api/v1/inside_pay_service/inside_payment/account")
        .body(RawFileBody("com/gatling/tests/payment_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Inside Payment Difference")
        .post("/api/v1/inside_pay_service/inside_payment/difference")
        .body(RawFileBody("com/gatling/tests/payment_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Payment")
        .post("/api/v1/paymentservice/payment")
        .body(RawFileBody("com/gatling/tests/payment_form.json"))
        .headers(apiV1Header))
    .exec(
      http("Post Payment Money")
        .post("/api/v1/paymentservice/payment/money")
        .body(RawFileBody("com/gatling/tests/payment_form.json"))
        .headers(apiV1Header))

  setUp(
    paymentGeneral.inject(rampUsers(1).during(15)),
    paymentGet.inject(rampUsers(1).during(10)),
    paymentPost.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}
