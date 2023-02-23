package com.gatling.tests.Checkout

import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.Protocols.*
import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

class CheckPastOrders extends Simulation {

	//Scenario 1: login, add item, view cart, go to checkout, checkout, view past order, view order details
	//Scenario 2: login, add item, view cart, go to checkout, checkout, cancel order
	val users1 = scenario("Users1").exec(homePage, viewPastOrders, viewOrderDetails, viewPastOrders)

	setUp(
		users1.inject(rampUsers(400).during(10))
	).protocols(httpProtocolEShop)
}