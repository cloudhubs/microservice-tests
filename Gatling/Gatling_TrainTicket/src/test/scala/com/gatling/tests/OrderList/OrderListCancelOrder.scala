package com.gatling.tests.OrderList

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.NavigationModules.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

class OrderListCancelOrder extends Simulation {

  val viewUsers = scenario("Users Viewing Page").exec(homePage, viewOrderListPage, homePage)

  val cancelUsers = scenario("Users Cancelling Order").exec(homePage, viewOrderListPage, cancelOrder, viewOrderListPage, homePage)

  setUp(
    viewUsers.inject(rampUsers(60).during(25)),
    cancelUsers.inject(rampUsers(30).during(20))
  ).protocols(httpProtocolTrainTicket)
}