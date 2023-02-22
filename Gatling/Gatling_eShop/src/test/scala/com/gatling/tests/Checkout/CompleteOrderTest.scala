package com.gatling.tests.Checkout

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*

class CompleteOrderTest extends Simulation {

  val users1 = scenario("Users1").exec(homePage, viewCart, selectCheckout, submitCheckout, viewPastOrders)

  setUp(
    users1.inject(rampUsers(50).during(15)),
  ).protocols(httpProtocolEShop)
}