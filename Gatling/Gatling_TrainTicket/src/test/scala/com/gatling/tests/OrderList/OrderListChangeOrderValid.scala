package com.gatling.tests.OrderList

import io.gatling.core.Predef.*

import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.NavigationModules.*

class OrderListChangeOrderValid extends Simulation {

	val users = scenario("Users Changing Order").exec(homePage, viewOrderListPage, changeOrder, homePage)

	setUp(
		users.inject(rampUsers(1000).during(30))
	).protocols(httpProtocolTrainTicket)
}