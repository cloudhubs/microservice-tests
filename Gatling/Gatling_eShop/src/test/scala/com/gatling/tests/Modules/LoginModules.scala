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
    .get("/Account/Register?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638127777014677575.OTVlNjljYzUtZTkxZi00ZDdlLTk1YjQtYTA2OGI4ZGYxMDFhYmFmOTE5YzQtODk2Yi00ZTg2LTlmYTgtYTRhZTMyMTlhMWRk%26state%3DCfDJ8MuPnMIYXd5ErJ4gKURjpuz4kZfeLtHsGoqrpiH48bNs6W8rlVw2vPh_4rGl-JosfTvd1lZhLzQnkNXC_ZQ0mNn7SEeoVEI6zkjwHTfW-VxaqvFkgb8pH5NeSCvol_mya5oRREfyvqlNNj0bMd-y0wV4jTq3UdCYJve-2HnFlKYTkUzGVVZrNZcuS5O5RmwjkJcqQsUJBm-Ugi6AX1rI6eGm_GLijxM6Jb9t1FRj8RV6H7MMWLK6Ioxxn1fwau9pmK3UX3KzGP3M6sbwErHtFUPIbR9UJ0xzBzB00UHQd54ObKrRdDgk8-s0SNSauXmCbkDKEB19wmt8hplduTy7A4c%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0"))
    .pause(4)

  //Submit registration form
  val submitRegistration = exec(http("Submit Request")
    .post("/Account/Register?returnurl=%2Fconnect%2Fauthorize%2Fcallback%3Fclient_id%3Dmvc%26redirect_uri%3Dhttp%253A%252F%252Fhost.docker.internal%253A5100%252Fsignin-oidc%26response_type%3Dcode%2520id_token%26scope%3Dopenid%2520profile%2520orders%2520basket%2520webshoppingagg%2520orders.signalrhub%26response_mode%3Dform_post%26nonce%3D638127777014677575.OTVlNjljYzUtZTkxZi00ZDdlLTk1YjQtYTA2OGI4ZGYxMDFhYmFmOTE5YzQtODk2Yi00ZTg2LTlmYTgtYTRhZTMyMTlhMWRk%26state%3DCfDJ8MuPnMIYXd5ErJ4gKURjpuz4kZfeLtHsGoqrpiH48bNs6W8rlVw2vPh_4rGl-JosfTvd1lZhLzQnkNXC_ZQ0mNn7SEeoVEI6zkjwHTfW-VxaqvFkgb8pH5NeSCvol_mya5oRREfyvqlNNj0bMd-y0wV4jTq3UdCYJve-2HnFlKYTkUzGVVZrNZcuS5O5RmwjkJcqQsUJBm-Ugi6AX1rI6eGm_GLijxM6Jb9t1FRj8RV6H7MMWLK6Ioxxn1fwau9pmK3UX3KzGP3M6sbwErHtFUPIbR9UJ0xzBzB00UHQd54ObKrRdDgk8-s0SNSauXmCbkDKEB19wmt8hplduTy7A4c%26x-client-SKU%3DID_NETSTANDARD2_0%26x-client-ver%3D6.10.0.0")
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
