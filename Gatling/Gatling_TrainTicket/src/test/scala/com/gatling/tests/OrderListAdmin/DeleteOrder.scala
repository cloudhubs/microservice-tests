package com.gatling.tests.OrderListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class DeleteOrder extends Simulation {

  val users = scenario("Users Deleting Order").exec(adminOrderPage, deleteOrder, adminOrderPage)

  setUp(
    users.inject(rampUsers(20).during(15))
  ).protocols(httpProtocol)
}