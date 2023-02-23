package com.gatling.tests.AddItem

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.ScenarioBuilder

class AddItemTest extends Simulation {

  //CSV file that holds account information
  val feeder = csv("data/valid_accounts.csv").random

  //Scenario will sort on random parameters within the csv file
  val users: ScenarioBuilder = scenario("Users Adding Items")
    .repeat(1) {
      feed(feeder)
        .exec { session =>
          println(s"Test: ${itemArr(random.nextInt(itemArr.length))}")
          session
        }
        loginScenario
        //go to home page, login, add random items (1-14), view cart
        .exec(addItem, viewCart)
        logoutScenario
        .pause(1)
    }

  setUp(
    users.inject(rampUsers(5).during(5))
  ).protocols(httpProtocolEShop)
}