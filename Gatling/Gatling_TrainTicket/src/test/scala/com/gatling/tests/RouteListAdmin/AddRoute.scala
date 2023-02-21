package com.gatling.tests.RouteListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.AdminModules.*
import com.gatling.tests.Modules.HeaderModules.*

class AddRoute extends Simulation {

	val users = scenario("Users Adding Route").exec(routePage, addRoute, routePage)

	setUp(
		users.inject(rampUsers(20).during(15))
	).protocols(httpProtocolTrainTicket)
}