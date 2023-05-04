package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*

import com.gatling.tests.Modules.Protocols.*

object LoginModules {
  //Accessing the home page of the application
  val homePage = exec(http("Home Page")
    .get("/"))
    .pause(3)

  //Execution of accessing sign in page of application
  val loginPage = exec(http("Sign In Page")
    .get("/Account/SignIn"))
    .pause(4)

  //Execution of signing into application
  val login = exec(http("Submit Sign In")
    .post("http://host.docker.internal:5105/Account/Login")
    .headers(formHeader)
    .silent
    .formParam("Email", "${email}")
    .formParam("Password", "${password}")
    .formParam("RememberMe", "false"))
    .pause(5)

  //Logout of application
  val logout = exec(http("Logout")
    .post("/Account/SignOut"))
    .pause(2)

  //Go to registration page of application
  val registerPage = exec(http("Registration Page")
    .get("/Account/Register"))
    .pause(4)

  //Submit registration form
  val submitRegistration = exec(http("Submit Request")
    .post("/Account/Register")
    .headers(formHeader)
    .formParam("User.Name", "${username}")
    .formParam("User.LastName", "${lastname}")
    .formParam("User.Street", "${street}")
    .formParam("User.City", "${city}")
    .formParam("User.State", "${state}")
    .formParam("User.Country", "${country}")
    .formParam("User.ZipCode", "${zipcode}")
    .formParam("User.PhoneNumber", "${phoneNum}")
    .formParam("User.CardNumber", "${cardNum}")
    .formParam("User.CardHolderName", "${cardHolder}")
    .formParam("User.Expiration", "${cardExp}")
    .formParam("User.SecurityNumber", "${securityNum}")
    .formParam("Email", "${email}")
    .formParam("Password", "${password}")
    .formParam("ConfirmPassword", "${confirmPass}"))

  //Login scenario that can be used in other tests
  val loginScenario: ChainBuilder = exec(homePage, loginPage, login)

  //General logout scenario
  val logoutScenario: ChainBuilder = exec(logout, homePage)

}
