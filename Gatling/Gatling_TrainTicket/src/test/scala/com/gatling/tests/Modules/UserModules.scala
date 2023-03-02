package com.gatling.tests.Modules

import io.gatling.core.Predef.*
import io.gatling.http.Predef.*
import io.gatling.jdbc.Predef.*
import HeaderModules.*
import io.gatling.core.structure.ChainBuilder

object UserModules {

  //View order list page
  val viewOrderListPage = exec(http("View Order List")
    .get("/client_order_list.html")
    .headers(orderListHeader)
    .resources(http("Refresh Page Test")
      .post("/api/v1/orderservice/order/refresh")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/${account_file}")),
      http("Refresh Page")
        .post("/api/v1/orderOtherService/orderOther/refresh")
        .headers(apiV1Header)
        .body(RawFileBody("com/gatling/tests/${account_file}"))))
    .pause(8)

  //Pay ticket using given payment form
  val payTicket = exec(http("Confirm Payment")
    .post("/api/v1/inside_pay_service/inside_payment")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${payment_form}")))
    .pause(4)

  //Cancel a given order
  val cancelOrder = exec(http("Select Cancel Order")
    .get("/api/v1/cancelservice/cancel/refound/${order_id}")
    .headers(apiV1Header))
    .pause(4)
    .exec(http("View Cancellation Message")
      .get("/api/v1/cancelservice/cancel/${order_id}/${login_id}")
      .headers(apiV1Header))
    .pause(2)

  //Change the order of a given ticket
  val changeOrder = exec(http("Select Change Order")
    .post("/api/v1/travelservice/trips/left")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${select_trip_file}")))
    .pause(4)
    .exec(http("Confirm Rebook")
      .post("/api/v1/rebookservice/rebook")
      .headers(apiV1Header)
      .body(RawFileBody("com/gatling/tests/${rebook_form}")))
    .pause(7)

  //View given consign using consign id
  val viewConsign = exec(http("View Consign")
    .get("/api/v1/consignservice/consigns/order/${consign_id}")
    .headers(apiV1Header))
    .pause(6)

  //Update given consign using json file
  val updateConsign = exec(http("Update Consign")
    .put("/api/v1/consignservice/consigns")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${consign_form}")))
    .pause(3)

  //Search for given trip using parameters
  val searchTrip = exec(http("Search for Trip")
    .post("/api/v1/travel2service/trips/left")
    .headers(searchTripHeader)
    .body(RawFileBody("com/gatling/tests/${search_file}")))
    .pause(6)

  //Break this up into defined parts: assurance, contacts, food service
  /** Custom Contact Steps
   * exec(http("Custom Contact Form")
   * .post("/api/v1/contactservice/contacts")
   * .headers(headers_9)
   * .body(RawFileBody("com/gatling/tests/Booking/booking1stclass2/custom_contact_form.json"))
   * .resources(http("Custom Contact Form")
   * .get("/api/v1/contactservice/contacts/account/4d2a46c7-71cb-4cf1-b5bb-b68406d9da6f")
   * .headers(headers_6)))
   */
  //Select trip and get needs resources
  val selectTrip = exec(http("Select Trip") /** change seat_price to 22.5 for economy*/
    .get("/client_ticket_book.html?tripId=${trip_id}&from=${from}&to=${to}&seatType=${seat_type}&seat_price=${seat_price}&date=${date}")
    .headers(selectTripHeader)
    .resources(http("Get Assurance Types")
      .get("/api/v1/assuranceservice/assurances/types")
      .headers(apiV1Header),
      http("Get Contacts")
        .get("/api/v1/contactservice/contacts/account/${login_id}")
        .headers(apiV1Header),
      http("Get Food Service Options")
        .get("/api/v1/foodservice/foods/${date}/${from}/${to}/${trip_id}")
        .headers(apiV1Header)))
    .pause(12)

  //Submit the booking info
  val submitTripBooking = exec(http("Submit Booking")
    .post("/api/v1/preserveservice/preserve")
    .headers(apiV1Header)
    .body(RawFileBody("com/gatling/tests/${submit_file}")))
    .pause(5)

  //Visit ticket list page
  val ticketPage = exec(http("Ticket Page")
    .get("/client_ticket_collect.html"))
    .pause(3)

  //Collect a given ticket
  val collectTicket = exec(http("Collect Ticket")
    .get("/api/v1/executeservice/execute/collected/${ticket_id}")
    .headers(apiV1Header))
    .pause(5)

  //Visit the station list page
  val stationPage = exec(http("Station Page")
    .get("/client_enter_station.html")
    .headers(mainPageHeader))
    .pause(5)

  //Enter the station with a given ticket id
  val enterStation = exec(http("Enter Station")
    .get("/api/v1/executeservice/execute/execute/${ticket_id}")
    .headers(apiV1Header))
    .pause(4)
}
