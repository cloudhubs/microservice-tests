package com.gatling.tests.PriceListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class UpdatePrice extends Simulation {

	val users = scenario("Users Updating Price").exec(pricePage, updatePrice, pricePage)

	setUp(
		users.inject(rampUsers(20).during(15))
	).protocols(httpProtocol)
}