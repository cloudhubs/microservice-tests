package com.gatling.tests.FilterAndNavigation

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.ScenarioBuilder

class FilterNavigationTest extends Simulation {

	//CSV contains all combinations of filtering patterns
	val feeder = csv("data/filter_test.csv").random

	//Scenario will sort on random parameters within the csv file
	val usersFiltering: ScenarioBuilder = scenario("Users Filtering")
		.repeat(1) {
			feed(feeder)
				.exec { session =>
					println("Filter1: " + session("filter_1_name").as[String])
					println("Filter2: " + session("filter_2_name").as[String])
					println("Page Number: " + session("pageNumber").as[String])
					session
				}
				//Virtual users will load the home page, filter the items, navigate to a random page, then filter again
				.exec(homePage, filterItems, navigateHomePage, filterItems)
				.pause(1)
		}

	setUp(
		usersFiltering.inject(rampUsers(100).during(15))
	).protocols(httpProtocolEShop).assertions(global.failedRequests.count.is(0))
}