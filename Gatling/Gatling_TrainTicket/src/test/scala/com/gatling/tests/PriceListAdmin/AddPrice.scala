package com.gatling.tests.PriceListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class AddPrice extends Simulation {

  val users = scenario("Users Adding Price").exec(pricePage, addPrice, pricePage)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocol)
}