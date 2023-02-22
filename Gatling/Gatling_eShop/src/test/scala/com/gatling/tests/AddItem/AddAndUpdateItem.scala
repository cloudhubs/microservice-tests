package com.gatling.tests.AddItem

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*

import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.Protocols.*

class AddAndUpdateItem extends Simulation {

  val users1 = scenario("Users1").exec(homePage, addItem, viewCart)

  val users2 = scenario("Users2").exec(homePage, addItem, viewCart, updateCart, homePage)

  setUp(
    users1.inject(rampUsers(700).during(20)),
    users2.inject(rampUsers(500).during(30))
  ).protocols(httpProtocolEShop)
}