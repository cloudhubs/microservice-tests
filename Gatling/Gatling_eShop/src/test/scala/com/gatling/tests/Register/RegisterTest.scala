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

  //CSV files to hold valid and invalid registration information
  val validRegisterFeeder = csv("data/valid_registration.csv").random
  val invalidRegisterFeeder = csv("data/invalid_registration.csv").random

  //Scenario that tests valid registrations
  val validUser: ScenarioBuilder = scenario("Valid Registration")
    .repeat(1) {
      feed(validRegisterFeeder) //Get valid registration data from file
        //Register new account and go to login page
        .exec(homePage, loginPage, registerPage, submitRegistration, loginPage)
        .pause(1)
    }

  //Scenario that tests invalid registrations
  val invalidUser: ScenarioBuilder = scenario("Invalid Registration")
    .repeat(1) {
      feed(invalidRegisterFeeder) //Get invalid registration info
        //Attempt to create a new account with invalid data
        .exec(homePage, loginPage, registerPage, submitRegistration)
        .pause(1)
    }

  //Inject valid and invalid registrations into system
  setUp(
    validUser.inject(rampUsers(5000).during(30)),
    invalidUser.inject(rampUsers(5000).during(30))
  ).protocols(httpProtocolEShop)
}