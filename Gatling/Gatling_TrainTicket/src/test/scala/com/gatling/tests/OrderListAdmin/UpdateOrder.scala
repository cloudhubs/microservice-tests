package com.gatling.tests.OrderListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class UpdateOrder extends Simulation {

  val users = scenario("Users Updating Order").exec(adminOrderPage, addOrderAdmin, adminOrderPage)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocol)
}