package com.gatling.tests.OrderList

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.HeaderModules.*

class OrderListUpdateConsign extends Simulation {

	val viewUsers = scenario("Users Viewing List").exec(homePage, viewOrderListPage, viewConsign, homePage)

	val updateUsers = scenario("Users Updating").exec(homePage, viewOrderListPage, viewConsign, updateConsign, homePage)

	setUp(
		viewUsers.inject(rampUsers(20).during(10)),
		updateUsers.inject(rampUsers(15).during(10))
	).protocols(httpProtocolTrainTicket)
}