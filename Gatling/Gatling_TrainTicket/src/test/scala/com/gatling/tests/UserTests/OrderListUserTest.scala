package com.gatling.tests.UserTests

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.LoginModule.*
import com.gatling.tests.Modules.UserModules.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

class OrderListUserTest extends Simulation {

  val orderCancel = scenario("Users Cancelling Order").exec(homePage, viewOrderListPage, cancelOrder, viewOrderListPage, homePage)

  val orderChange = scenario("Users Changing Order").exec(homePage, viewOrderListPage, changeOrder, homePage)

  val orderPay = scenario("Users Paying for Ticket").exec(homePage, viewOrderListPage, payTicket, homePage)

  val consignUpdate = scenario("Users Updating Consign").exec(homePage, viewOrderListPage, viewConsign, updateConsign, homePage)

  setUp(
    orderCancel.inject(rampUsers(30).during(20)),
    orderChange.inject(rampUsers(30).during(20)),
    orderPay.inject(rampUsers(30).during(20)),
    consignUpdate.inject(rampUsers(30).during(20))
  ).protocols(httpProtocolTrainTicket)
}