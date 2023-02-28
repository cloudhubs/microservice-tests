package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

import scala.util.Random
class LoginTest extends Simulation {

	//CSV file feeder that hold valid or invalid account information
	val validLoginFeeder = csv("data/valid_accounts.csv").circular
	val invalidLoginFeeder = csv("data/invalid_accounts.csv").random

	//Scenario that tests valid login
	val validUser: ScenarioBuilder = scenario("Valid Login Users")
		.repeat(1) {
			feed(validLoginFeeder) //Get valid account information from file

				//Login, go home, view cart, and logout
				.exec(loginScenario, homePage, viewCart, loginScenario)
				.pause(1)
		}

	//Scenario that tests invalid login information
	val invalidUser: ScenarioBuilder = scenario("Invalid Login Users")
		.exec {
			feed(invalidLoginFeeder) //Get invalid account information
				//Login with invalid credentials, go back to home page
				.exec(homePage, loginPage, login)
				.pause(1)
		}

	//Inject valid and invalid users into system
	setUp(
		validUser.inject(rampUsers(4).during(10)),
		invalidUser.inject(rampUsers(4).during(10))
	).protocols(httpProtocolEShop).assertions(global.failedRequests.count.is(0))
}