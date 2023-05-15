package com.gatling.tests.Login

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

class LoginTest extends Simulation {

	//CSV file feeder that hold valid or invalid account information
	val validLoginFeeder = csv("data/valid_accounts.csv").circular
	val invalidLoginFeeder = csv("data/invalid_accounts.csv").random

	//Scenario that tests valid login
	val validUser: ScenarioBuilder = scenario("Valid Login Users")
		.exec {
			feed(validLoginFeeder) //Get valid account information from file
				//Login, go home, and logout
				.exec(loginScenario, homePage, logoutScenario)
				.pause(1)
		}

	//Scenario that tests invalid login information
	val invalidUser: ScenarioBuilder = scenario("Invalid Login Users")
		.exec {
			feed(invalidLoginFeeder) //Get invalid account information
				//Login with invalid credentials, go back to home page
				.exec(loginScenario)
				.pause(1)
		}

	//Inject valid and invalid users into system
	setUp(
		validUser.inject(rampUsers(5000).during(30)),
		invalidUser.inject(rampUsers(5000).during(30))
	).protocols(httpProtocolEShop)
}