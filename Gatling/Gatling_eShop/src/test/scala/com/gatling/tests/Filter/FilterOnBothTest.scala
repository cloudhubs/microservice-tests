package com.gatling.tests.Filter

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*

class FilterOnBothTest extends Simulation {

	val users1 = scenario("Users1").exec(homePage, filterItems)

	val users2 = scenario("Users2").exec(homePage, filterItems)

	setUp(
		users1.inject(rampUsers(1500).during(20)),
		users2.inject(rampUsers(1000).during(30))
	).protocols(httpProtocolEShop)
}