package com.gatling.tests.UpdateCart

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

import scala.util.Random

class UpdateCartTest extends Simulation {

  //CSV file  holds account information
  val validLoginFeeder = csv("data/valid_accounts.csv").circular

  //CSV file that contains item information
  val itemFeeder = csv("data/item_info.csv").random

  //Scenario to test updating a users cart
  val user: ScenarioBuilder = scenario("Users Updating Cart")
    .repeat(1) {
      feed(validLoginFeeder)
        //Login scenario, go to home page and view cart
        .exec(loginScenario, homePage, viewCart)
    }
    .repeat(2) {
      feed(itemFeeder) //Update the cart with a random item
        .exec(updateCart)
    }
    //Logout scenario
    .exec(logoutScenario)
    .pause(1)

  setUp(
    user.inject(rampUsers(4).during(10))
  ).protocols(httpProtocolEShop).assertions(global.failedRequests.count.is(0))
}