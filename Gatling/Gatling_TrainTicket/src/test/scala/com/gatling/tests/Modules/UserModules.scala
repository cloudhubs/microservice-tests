package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import HeaderModules.*
import io.gatling.core.structure.ChainBuilder
import com.gatling.tests.Modules.LoginModule.*

object UserModules {

  //View order list page
  val viewOrderListPage: ChainBuilder = exec(http("View Order List")
    .get("/client_order_list.html")
    .headers(apiV1Header)
    .resources(http("Refresh Page")
      .post("/api/v1/orderservice/order/refresh") //Refresh page
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/${account_file}")),
      http("Refresh Page")
        .post("/api/v1/orderOtherService/orderOther/refresh") //Refresh page
        .headers(apiV1Header)
        .body(RawFileBody("com/gatling/tests/${account_file}"))))
    .pause(8)

  //Pay ticket using given payment form
  val payTicket: ChainBuilder = exec(http("Confirm Payment")
    .post("/api/v1/inside_pay_service/inside_payment")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${payment_form}")))
    .pause(4)

  //Cancel a given order
  val cancelOrder: ChainBuilder = exec(http("Select Cancel Order")
    .get("/api/v1/cancelservice/cancel/refound/${order_id}")
    .headers(apiV1Header))
    .pause(4)
    .exec(http("View Cancellation Message")
      .get("/api/v1/cancelservice/cancel/${order_id}/${login_id}") //Cancel order
      .headers(apiV1Header))
    .pause(2)

  //Change the order of a given ticket
  val changeOrder: ChainBuilder = exec(http("Select Change Order")
    .post("/api/v1/travelservice/trips/left")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${select_trip_file}")))
    .pause(4)
    .exec(http("Confirm Rebook")
      .post("/api/v1/rebookservice/rebook") //Confirm rebook
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/${rebook_form}")))
    .pause(7)

  //View given consign using consign id
  val viewConsign: ChainBuilder = exec(http("View Consign")
    .get("/api/v1/consignservice/consigns/order/${consign_id}")
    .headers(apiV1Header))
    .pause(6)

  //Update given consign using json file
  val updateConsign: ChainBuilder = exec(http("Update Consign")
    .put("/api/v1/consignservice/consigns")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${consign_form}")))
    .pause(3)

  //Search for given trip using parameters
  val searchTrip: ChainBuilder = exec(http("Search for Trip")
    .post("/api/v1/travel2service/trips/left")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${search_file}")))
    .pause(6)

  //Select trip and get needs resources
  val selectTrip: ChainBuilder = exec(http("Select Trip")
    .get("/client_ticket_book.html?tripId=${trip_id}&from=${from}&to=${to}&seatType=${seat_type}&seat_price=${seat_price}&date=${date}")
    .headers(apiV1Header)
    .resources(http("Get Contacts")
        .get("/api/v1/contactservice/contacts/account/${login_id}")
        .headers(apiV1Header),
      http("Get Food Service Options")
        .get("/api/v1/foodservice/foods/${date}/${from}/${to}/${trip_id}")
        .headers(apiV1Header)))
    .pause(12)

  //Submit the booking info
  val submitTripBooking: ChainBuilder = exec(http("Submit Booking")
    .post("/api/v1/preserveservice/preserve")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${submit_file}")))
    .pause(5)

  //Visit ticket list page
  val ticketPage: ChainBuilder = exec(http("Ticket Page")
    .get("/client_ticket_collect.html"))
    .pause(3)

  //Visit the station list page
  val stationPage: ChainBuilder = exec(http("Station Page")
    .get("/client_enter_station.html")
    .headers(mainPageHeader))
    .pause(5)
}
