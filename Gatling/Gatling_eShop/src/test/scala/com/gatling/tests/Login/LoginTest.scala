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

	val validLoginFeeder = csv("data/valid_accounts.csv").random
	val invalidLoginFeeder = csv("data/invalid_accounts.csv").random

	val validUser: ScenarioBuilder = scenario("Valid Login Users")
		.repeat(1) {
			feed(validLoginFeeder)
				.exec { session =>
					println("Email: " + session("email").as[String])
					println("Password: " + session("password").as[String])
					session
				}
				//Login scenario
				loginScenario
				//Go to home page and view cart
				.exec(homePage, viewCart)
				//Logout scenario
				logoutScenario
				.pause(1)
		}

	val invalidUser: ScenarioBuilder = scenario("Invalid Login Users")
		.repeat(1) {
			feed(invalidLoginFeeder)
				.exec { session =>
					println("Email: " + session("email").as[String])
					println("Password: " + session("password").as[String])
					session
				}
				//Scenario 2: Login with invalid credentials, go back to home page
				.exec(homePage, loginPage, login)
				.pause(1)
		}

	setUp(
		validUser.inject(rampUsers(4).during(10)),
		invalidUser.inject(rampUsers(4).during(10))
	).protocols(httpProtocolEShop).assertions(global.failedRequests.count.is(0))
}