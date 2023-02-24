package com.gatling.tests.AddItem

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.ScenarioBuilder

import scala.util.Random

class AddItemTest extends Simulation {

  //CSV file that holds account information
  val feeder = csv("data/valid_accounts.csv").random

  val itemFeeder = csv("data/item_info.csv").random

  //Scenario will sort on random parameters within the csv file
  val users: ScenarioBuilder = scenario("Users Adding Items")
    .exec {
      feed(feeder)
        .exec(loginScenario)
    }
    .repeat(2) {
      feed(itemFeeder)
        //go to home page, login, add random items (1-14), view cart
        .exec(addItem)
    }
    .exec(viewCart)
    .exec(logoutScenario)

  setUp(
    users.inject(rampUsers(20).during(10))
  ).protocols(httpProtocolEShop)
}