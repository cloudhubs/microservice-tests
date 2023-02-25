package com.gatling.tests.PriceListAdmin

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.HeaderModules.*
import com.gatling.tests.Modules.AdminModules.*

class PriceListTest extends Simulation {

  /** operation = Add, file_path = , endpoint = adminbasicservice/adminbasic/prices */
  val priceAdd = scenario("Users Adding Price").exec(pricePage, action, pricePage)

  /** delete_id = dd0e572e-7443-420c-8280-7d8215636069, type = Price, endpoint = adminbasicservice/adminbasic/prices */
  val priceDelete = scenario("Users Deleting Price").exec(pricePage, delete, pricePage)

  /**operation = Update, file_path = , endpoint = adminbasicservice/adminbasic/prices */
  val priceUpdate = scenario("Users Updating Price").exec(pricePage, action, pricePage)

  setUp(
    priceAdd.inject(rampUsers(20).during(15)),
    priceDelete.inject(rampUsers(20).during(15)),
    priceUpdate.inject(rampUsers(20).during(15))
  ).protocols(httpProtocolTrainTicket)
}