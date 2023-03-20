package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import io.gatling.core.Predef.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

class OrderListUserTest extends Simulation {

  //View order list needs
  val account_file = "OrderListUser/account_request.json"

  //Scenario to test the order cancellation feature
  val orderCancel = scenario("Users Cancelling Order")
    .exec(loginScenario)
    .exec { session =>
      val newSession = session.setAll("account_file" -> s"${account_file}",
        "order_id" -> "cfd74f18-9135-422f-8c16-73aa8e019059",
        "login_id" -> "4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
      newSession
    }
    .exec(viewOrderListPage, cancelOrder, viewOrderListPage)
    .exec(
      http("Get Cancel Service Welcome")
      .get("/api/v1/cancelservice/welcome")
      .headers(apiV1Header))

  //Scenario to test the order change feature
  val orderChange = scenario("Users Changing Order")
    .exec(loginScenario)
    .exec { session =>
      val newSession = session.setAll("account_file" -> s"${account_file}",
        "select_trip_file" -> "OrderListUser/select_trip_form.json",
        "rebook_form" -> "OrderListUser/rebook_form.json")
      newSession
    }
    .exec(homePage, viewOrderListPage, changeOrder, homePage)

  //Scenario to test paying for an order/ticket
  val orderPay = scenario("Users Paying for Ticket")
    .exec(loginScenario)
    .exec { session =>
      val newSession = session.setAll("account_file" -> s"${account_file}",
        "payment_form" -> "OrderListUser/payment_info.json")
      newSession
    }
    .exec(homePage, viewOrderListPage, payTicket, homePage)

  //Scenario to update the consign of a ticket
  val consignUpdate: ScenarioBuilder = scenario("Users Updating Consign")
    .exec(loginScenario)
    .exec { session =>
      val newSession = session.setAll("account_file" -> s"${account_file}",
        "consign_id" -> "8c019509-7b40-44c2-803f-a15c17f83b1",
        "consign_form" -> "OrderListUser/update_consign_form.json")
      newSession
    }
    .exec(homePage, viewOrderListPage, viewConsign, updateConsign, homePage)

  setUp(
    orderCancel.inject(rampUsers(1).during(20)),
    orderChange.inject(rampUsers(1).during(20)),
    orderPay.inject(rampUsers(1).during(20)),
    consignUpdate.inject(rampUsers(1).during(20))
  ).protocols(httpProtocolTrainTicket)
}