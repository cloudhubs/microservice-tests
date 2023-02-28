package com.gatling.tests.Checkout

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.core.session.Session.*
import io.gatling.commons.validation.Validation.*

class CompleteOrderTest extends Simulation {

  //CSV file that holds account information
  val validLoginFeeder = csv("data/valid_accounts.csv").random

  //CSV file that holds information used at checkout
  val validCheckoutFeeder = csv("data/valid_checkout.csv").random

  val userValid: ScenarioBuilder = scenario("Users Checking Out")
    .exec {
      feed(validLoginFeeder)
        //Login scenario, go to home page and view cart
        .exec(loginScenario, homePage, viewCart)
    }
    .exec {
      feed(validCheckoutFeeder) //Get checkout information from file
        //Complete checkout actions
        .exec(selectCheckout, submitCheckout, viewPastOrders)
    }
    //Randomly either cancel order to view order details
    .randomSwitch(
      50.0 -> exec(cancelOrder),
      50.0 -> exec(viewOrderDetails, viewPastOrders)
    )
    //Logout scenario
    .exec(logoutScenario)
    .pause(1)

   //TODO: Add in invalid checkout form

  setUp(
    userValid.inject(rampUsers(5).during(15)),
  ).protocols(httpProtocolEShop)
}