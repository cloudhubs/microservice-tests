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
  val accountFeeder = csv("data/valid_accounts.csv").random

  //CSV file that holds item information
  val itemFeeder = csv("data/item_info.csv").random

  //Scenario will sort on random parameters within the csv file
  val users: ScenarioBuilder = scenario("Users Adding Items")
    .exec {
      //Feeder to give account information
      feed(accountFeeder)
        .exec(loginScenario) //Log into system
        .repeat(2){
          feed(itemFeeder) //Feeder to provide item information
            .exec(addItem)
        }
    }
    .exec(viewCart, logoutScenario)

  //Run the test by ramping up the virtual users
  setUp(
    users.inject(rampUsers(100).during(30))
  ).protocols(httpProtocolEShop)
}