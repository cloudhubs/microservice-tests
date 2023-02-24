package com.gatling.tests.Register

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import scala.concurrent.duration.*
import com.gatling.tests.Modules.Protocols.*
import com.gatling.tests.Modules.NavigationModules.*
import com.gatling.tests.Modules.LoginModules.*
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}

class RegisterTest extends Simulation {

  val validRegisterFeeder = csv("data/valid_registration.csv").random
  val invalidRegisterFeeder = csv("data/invalid_registration.csv").random

  val validUser: ScenarioBuilder = scenario("Valid Registration")
    .repeat(1) {
      feed(validRegisterFeeder)
        //Go to home page and view cart
        .exec(homePage, loginPage, registerPage, submitRegistration, loginPage)
        .pause(1)
    }

  val invalidUser: ScenarioBuilder = scenario("Invalid Registration")
    .repeat(1) {
      feed(invalidRegisterFeeder)
        //Scenario 2: Login with invalid credentials, go back to home page
        .exec(homePage, loginPage, registerPage, submitRegistration)
        .pause(1)
    }

  setUp(
    validUser.inject(rampUsers(10).during(10)),
    invalidUser.inject(rampUsers(10).during(10))
  ).protocols(httpProtocolEShop).assertions(global.failedRequests.count.is(0))
}