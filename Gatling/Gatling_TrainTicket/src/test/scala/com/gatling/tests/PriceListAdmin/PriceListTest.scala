package com.gatling.tests.PriceListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class PriceListTest extends Simulation {

  val priceAdd = scenario("Users Adding Price").exec(pricePage, addPrice, pricePage)

  val priceDelete = scenario("Users Deleting Price").exec(pricePage, deletePrice, pricePage)

  val priceUpdate = scenario("Users Updating Price").exec(pricePage, updatePrice, pricePage)

  setUp(
    priceAdd.inject(rampUsers(20).during(15)),
    priceDelete.inject(rampUsers(20).during(15)),
    priceUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}