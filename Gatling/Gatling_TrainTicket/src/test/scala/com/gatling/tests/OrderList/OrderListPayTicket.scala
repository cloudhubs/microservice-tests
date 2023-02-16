package com.gatling.tests.OrderList

import io.gatling.core.Predef._

import scala.concurrent.duration.*

import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.HeaderModules.*

class OrderListPayTicket extends Simulation {

	val viewUsers = scenario("Users Viewing List").exec(homePage, viewOrderListPage, payTicket, homePage)

	val payUsers = scenario("Users Paying").exec(homePage, viewOrderListPage, homePage)

	setUp(
		payUsers.inject(atOnceUsers(1))
	).protocols(httpProtocolTrainTicket)
}