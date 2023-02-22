package com.gatling.tests.PastOrders

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*

class CheckPastOrders extends Simulation {

	val users1 = scenario("Users1").exec(homePage, viewPastOrders, viewOrderDetails, viewPastOrders)

	setUp(
		users1.inject(rampUsers(400).during(10))
	).protocols(httpProtocolEShop)
}