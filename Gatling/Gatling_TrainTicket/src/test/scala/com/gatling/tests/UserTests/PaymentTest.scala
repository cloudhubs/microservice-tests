package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.httpProtocolTrainTicket
import io.gatling.http.Predef.*
import io.gatling.core.Predef.*
import com.gatling.tests.Modules.LoginModule.*
import io.gatling.core.structure.ScenarioBuilder

class PaymentTest extends Simulation {

  //Test scenario to check general payment endpoints
  val paymentGeneral: ScenarioBuilder = scenario("Check General Payment Endpoints")
    .exec(loginScenario)
    .exec(http("Get Inside Payment Welcome")
      .get("/api/v1/inside_pay_service/welcome") //Get inside pay service welcome
      .headers(apiV1Header))
    .exec(http("Get Payment Welcome")
      .get("/api/v1/paymentservice/welcome") //Get payment service welcome
      .headers(apiV1Header))

  //Test scenario to check get endpoints
  val paymentGet: ScenarioBuilder = scenario("Check Get Payment Endpoints")
    .exec(loginScenario)
    .exec(http("Get Inside Payment by User ID") //Get inside payment by user id
      .get("/api/v1/inside_pay_service/inside_payment/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f/10000.0")
      .headers(apiV1Header))
    .exec(http("Get Inside Payment")
      .get("/api/v1/inside_pay_service/inside_payment/payment") //Get inside payment
      .headers(apiV1Header))
    .exec(http("Get Inside Payment Account")
      .get("/api/v1/inside_pay_service/inside_payment/account") //Get inside payment account
      .headers(apiV1Header))
    .exec(http("Get Inside Payment Money")
      .get("/api/v1/inside_pay_service/inside_payment/money") //Get inside payment money
      .headers(apiV1Header))
    .exec(http("Get Payment")
      .get("/api/v1/paymentservice/payment") //Get payments
      .headers(apiV1Header))

  //Test scenario to check add (post) endpoints
  val paymentPost: ScenarioBuilder = scenario("Check Post Payment Endpoints")
    .exec(loginScenario)
    .exec(http("Post Inside Payment")
      .post("/api/v1/inside_pay_service/inside_payment") //Add inside payment
      .body(RawFileBody("com/gatling/tests/PaymentService/payment_form.json"))
      .headers(apiV1Header))
    .exec(http("Post Inside Payment Account")
      .post("/api/v1/inside_pay_service/inside_payment/account") //Add inside payment account
      .body(RawFileBody("com/gatling/tests/PaymentService/payment_form.json"))
      .headers(apiV1Header))
    .exec(http("Post Inside Payment Difference")
      .post("/api/v1/inside_pay_service/inside_payment/difference") //Add inside payment different
      .body(RawFileBody("com/gatling/tests/PaymentService/payment_form.json"))
      .headers(apiV1Header))
    .exec(http("Post Payment")
      .post("/api/v1/paymentservice/payment") //Add payment
      .body(RawFileBody("com/gatling/tests/PaymentService/payment_form.json"))
      .headers(apiV1Header))
    .exec(http("Post Payment Money")
      .post("/api/v1/paymentservice/payment/money") //Add payment money
      .body(RawFileBody("com/gatling/tests/PaymentService/payment_form.json"))
      .headers(apiV1Header))

  //Run the test simulation with the scenarios
  setUp(
    paymentGeneral.inject(rampUsers(1).during(15)),
    paymentGet.inject(rampUsers(1).during(10)),
    paymentPost.inject(rampUsers(1).during(15))
  ).protocols(httpProtocolTrainTicket)
}
