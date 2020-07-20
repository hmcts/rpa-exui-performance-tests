package uk.gov.hmcts.reform.exui.performance.scenarios

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import uk.gov.hmcts.reform.exui.performance.scenarios.utils.Environment
import uk.gov.hmcts.reform.exui.performance.scenarios.utils.LoginHeader
import uk.gov.service.notify.NotificationClient

object EXUIMCLogin {

  //val BaseURL = Environment.baseURL
  val IdamUrl = Environment.idamURL
  val baseURL=Environment.baseURL
  //val loginFeeder = csv("OrgId.csv").circular


  val MinThinkTime = Environment.minThinkTime
  val MaxThinkTime = Environment.maxThinkTime

  //====================================================================================
  //Business process : Access Home Page by hitting the URL and relavant sub requests
  //below requests are Homepage and relavant sub requests as part of the login submission
  //=====================================================================================

  val manageCasesHomePage =
    tryMax(2) {

      exec(http("XUI${service}_010_005_Homepage")
           .get("/")
           .headers(LoginHeader.headers_0)
           .check(status.in(200,304))).exitHereIfFailed

        .exec(http("XUI${service}_010_010_Homepage")
<<<<<<< HEAD
              .get("/external/config/ui")
=======
              .get("/api/environment/config")
>>>>>>> origin/master
          .headers(LoginHeader.headers_0)
          .check(status.in(200,304)))

      .exec(http("XUI${service}_010_015_HomepageTCEnabled")
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_hometc)
            .check(status.is(200)))

      .exec(http("XUI${service}_010_020_HompageLoginPage")
            .get(IdamUrl + "/login?response_type=code&client_id=xuiwebapp&redirect_uri=" + baseURL + "/oauth2/callback&scope=profile%20openid%20roles%20manage-user%20create-user")
            .headers(LoginHeader.headers_login)
            .check(regex("Sign in"))
            .check(css("input[name='_csrf']", "value").saveAs("csrfToken"))
            .check(status.is(200)))
    }

    .pause( MinThinkTime, MaxThinkTime )


  val manageCasesHomePageGK =
    tryMax(2) {

      exec(http("XUI${service}_170_005_Homepage")
           .get("/")
           .headers(LoginHeader.headers_0)
           .check(status.in(200,304))).exitHereIfFailed

<<<<<<< HEAD
        .exec(http("XUI${service}_170_010_Homepage")
              .get("/external/config/ui")
=======
        .exec(http("XUI${service}_010_010_Homepage")
              .get("/api/environment/config")
>>>>>>> origin/master
              .headers(LoginHeader.headers_0)
              .check(status.in(200,304)))

      .exec(http("XUI${service}_170_015_HomepageTCEnabled")
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_hometc)
            .check(status.is(200)))

      .exec(http("XUI${service}_170_020_HompageLoginPage")
            .get(IdamUrl + "/login?response_type=code&client_id=xuiwebapp&redirect_uri=" + baseURL + "/oauth2/callback&scope=profile%20openid%20roles%20manage-user%20create-user")
            .headers(LoginHeader.headers_login)
            .check(regex("Sign in"))
            .check(css("input[name='_csrf']", "value").saveAs("csrfToken"))
            .check(status.is(200)))
    }

    .pause( MinThinkTime, MaxThinkTime )

  //==================================================================================
  //Business process : Enter the login details and submit
  //below requests are main login and relavant sub requests as part of the login submission
  //==================================================================================

  val manageCaseslogin =
    tryMax(2) {

      exec(http("XUI${service}_020_005_SignIn")
           .post(IdamUrl + "/login?response_type=code&client_id=xuiwebapp&redirect_uri=" + baseURL + "/oauth2/callback&scope=profile%20openid%20roles%20manage-user%20create-user")
           .formParam("username", "${user}")
           .formParam("password", "Pass19word")
           .formParam("save", "Sign in")
           .formParam("selfRegistrationEnabled", "false")
           .formParam("_csrf", "${csrfToken}")
           .headers(LoginHeader.headers_login_submit)
           .check(status.in(200, 304, 302))).exitHereIfFailed

      .exec(getCookieValue(
        CookieKey("__userid__").withDomain("manage-case.perftest.platform.hmcts.net").saveAs("myUserId")))

<<<<<<< HEAD
      .exec(http("XUI${service}_020_010_Homepage")
            .get("/external/config/ui")
            .headers(LoginHeader.headers_0)
            .check(status.in(200,304)))

        /*.exec(http("XUI${service}_020_010_Homepage")
              .get("/external/config/ui")
              .headers(LoginHeader.headers_0)
              .check(status.in(200,304)))*/

      .exec(http("XUI${service}_020_015_SignInTCEnabled")
=======
      .exec(http("XUI${service}_020_010_SignInTCEnabled")
>>>>>>> origin/master
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_38)
            .check(status.in(200, 304)))

<<<<<<< HEAD
     /* .exec(http("XUI${service}_020_020_SignInGetUserId")
            .get("/api/userTermsAndConditions/${myUserId}")
            .headers(LoginHeader.headers_tc))*/

     /* .exec(http("XUI${service}_020_025_SignInAcceptTCGet")
            .get("/accept-terms-and-conditions")
            .headers(LoginHeader.headers_tc_get)
            .check(status.in(200, 304)))*/

     /* .exec(http("XUI${service}_020_030_SignInTCEnabled")
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_tc))*/

      .repeat(1, "count") {
        exec(http("XUI${service}_020_020_AcceptT&CAccessJurisdictions${count}")
             .get("/aggregated/caseworkers/:uid/jurisdictions?access=read")
             .headers(LoginHeader.headers_access_read)
             .check(status.in(200, 304, 302)))
      }

        .exec(http("XUI${service}_020_025_GetWorkBasketInputs")
              .get("/data/internal/case-types/CARE_SUPERVISION_EPO/work-basket-inputs")
              .headers(LoginHeader.headers_17))

        .exec(http("XUI${service}_020_030_GetPaginationMetaData")
              .get("/data/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases/pagination_metadata?state=Open")
              .headers(LoginHeader.headers_0))

        .exec(http("XUI${service}_020_035_GetDefaultWorkBasketView")
              .get("/aggregated/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases?view=WORKBASKET&state=Open&page=1")
              .headers(LoginHeader.headers_0))



=======
      .exec(http("XUI${service}_020_015_SignInGetUserId")
            .get("/api/userTermsAndConditions/${myUserId}")
            .headers(LoginHeader.headers_tc))

      .exec(http("XUI${service}_020_020_SignInAcceptTCGet")
            .get("/accept-terms-and-conditions")
            .headers(LoginHeader.headers_tc_get)
            .check(status.in(200, 304)))

      .exec(http("XUI${service}_020_025_SignInTCEnabled")
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_tc))
>>>>>>> origin/master
    }

    .pause(MinThinkTime , MaxThinkTime)


<<<<<<< HEAD



=======
>>>>>>> origin/master
  //======================================================================================
  //Business process : Click on Terms and Conditions
  //below requests are Terms and Conditions page and relavant sub requests
  // ======================================================================================

  val termsnconditions=
    tryMax(2) {
      exec(http("XUI${service}_030_005_ConfirmT&C")
           .post("/api/userTermsAndConditions")
           .headers(LoginHeader.headers_tc)
           .body(StringBody("{\"userId\":\"${myUserId}\"}"))
           .check(status.in(200, 304, 302))).exitHereIfFailed

      .exec(http("XUI${service}_030_010_AcceptT&CEnabled")
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_hometc)
            .check(status.in(200, 304, 302)))

      .repeat(1, "count") {
        exec(http("XUI${service}_030_015_AcceptT&CAccessJurisdictions${count}")
             .get("/aggregated/caseworkers/:uid/jurisdictions?access=read")
             .headers(LoginHeader.headers_access_read)
             .check(status.in(200, 304, 302)))
      }

      .exec(http("XUI${service}_030_020_GetWorkBasketInputs")
            .get("/data/internal/case-types/CARE_SUPERVISION_EPO/work-basket-inputs")
            .headers(LoginHeader.headers_17))

      .exec(http("XUI${service}_030_025_GetPaginationMetaData")
            .get("/data/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases/pagination_metadata?state=Open")
            .headers(LoginHeader.headers_0))

      .exec(http("XUI${service}_030_030_GetDefaultWorkBasketView")
            .get("/aggregated/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases?view=WORKBASKET&state=Open&page=1")
            .headers(LoginHeader.headers_0))
    }

    .pause(MinThinkTime , MaxThinkTime )


  val managecasesadminlogin =
    tryMax(2) {

      exec(http("XUI${service}_020_005_SignIn")
           .post(IdamUrl + "/login?response_type=code&client_id=xuiwebapp&redirect_uri=" + baseURL + "/oauth2/callback&scope=profile%20openid%20roles%20manage-user%20create-user")
           .formParam("username", "fpla.admin@mailinator.com")
           .formParam("password", "Pass19word")
           .formParam("save", "Sign in")
           .formParam("selfRegistrationEnabled", "false")
           .formParam("_csrf", "${csrfToken}")
           .headers(LoginHeader.headers_login_submit)
           .check(status.in(200, 304, 302))).exitHereIfFailed

<<<<<<< HEAD
        .exec(http("XUI${service}_020_010_Homepage")
              .get("/external/config/ui")
              .headers(LoginHeader.headers_0)
              .check(status.in(200,304)))

      .exec(http("XUI${service}_020_015_SignInTCEnabled")
=======
      .exec(http("XUI${service}_020_010_SignInTCEnabled")
>>>>>>> origin/master
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_38)
            .check(status.in(200, 304)))

      .repeat(1, "count") {
<<<<<<< HEAD
        exec(http("XUI${service}_020_020_AcceptT&CAccessJurisdictions${count}")
=======
        exec(http("XUI${service}_030_015_AcceptT&CAccessJurisdictions${count}")
>>>>>>> origin/master
             .get("/aggregated/caseworkers/:uid/jurisdictions?access=read")
             .headers(LoginHeader.headers_access_read)
             .check(status.in(200, 304, 302)))
      }

<<<<<<< HEAD
      .exec(http("XUI${service}_020_025_GetWorkBasketInputs")
            .get("/data/internal/case-types/CARE_SUPERVISION_EPO/work-basket-inputs")
            .headers(LoginHeader.headers_17))

      .exec(http("XUI${service}_020_030_GetPaginationMetaData")
            .get("/data/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases/pagination_metadata?state=Open")
            .headers(LoginHeader.headers_0))

      .exec(http("XUI${service}_020_035_GetDefaultWorkBasketView")
=======
      .exec(http("XUI${service}_030_020_GetWorkBasketInputs")
            .get("/data/internal/case-types/CARE_SUPERVISION_EPO/work-basket-inputs")
            .headers(LoginHeader.headers_17))

      .exec(http("XUI${service}_030_025_GetPaginationMetaData")
            .get("/data/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases/pagination_metadata?state=Open")
            .headers(LoginHeader.headers_0))

      .exec(http("XUI${service}_030_030_GetDefaultWorkBasketView")
>>>>>>> origin/master
            .get("/aggregated/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases?view=WORKBASKET&state=Open&page=1")
            .headers(LoginHeader.headers_0))

    }

    .pause(MinThinkTime , MaxThinkTime)

  val managecasesgatekeeperlogin =
    tryMax(2) {

      exec(http("XUI${service}_180_005_SignIn")
           .post(IdamUrl + "/login?response_type=code&client_id=xuiwebapp&redirect_uri=" + baseURL + "/oauth2/callback&scope=profile%20openid%20roles%20manage-user%20create-user")
           .formParam("username", "fpla.gatekeeper@mailinator.com")
           .formParam("password", "Pass19word")
           .formParam("save", "Sign in")
           .formParam("selfRegistrationEnabled", "false")
           .formParam("_csrf", "${csrfToken}")
           .headers(LoginHeader.headers_login_submit)
           .check(status.in(200, 304, 302))).exitHereIfFailed

<<<<<<< HEAD
        .exec(http("XUI${service}_180_010_Homepage")
              .get("/external/config/ui")
              .headers(LoginHeader.headers_0)
              .check(status.in(200,304)))

      .exec(http("XUI${service}_180_015_SignInTCEnabled")
=======
      .exec(http("XUI${service}_0180_010_SignInTCEnabled")
>>>>>>> origin/master
            .get("/api/configuration?configurationKey=termsAndConditionsEnabled")
            .headers(LoginHeader.headers_38)
            .check(status.in(200, 304)))

      .repeat(1, "count") {
<<<<<<< HEAD
        exec(http("XUI${service}_180_020_AcceptT&CAccessJurisdictions${count}")
=======
        exec(http("XUI${service}_180_015_AcceptT&CAccessJurisdictions${count}")
>>>>>>> origin/master
             .get("/aggregated/caseworkers/:uid/jurisdictions?access=read")
             .headers(LoginHeader.headers_access_read)
             .check(status.in(200, 304, 302)))
      }

<<<<<<< HEAD
      .exec(http("XUI${service}_180_025_GetWorkBasketInputs")
            .get("/data/internal/case-types/CARE_SUPERVISION_EPO/work-basket-inputs")
            .headers(LoginHeader.headers_17))

      .exec(http("XUI${service}_180_030_GetPaginationMetaData")
            .get("/data/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases/pagination_metadata?state=Open")
            .headers(LoginHeader.headers_0))

      .exec(http("XUI${service}_180_035_GetDefaultWorkBasketView")
=======
      .exec(http("XUI${service}_180_020_GetWorkBasketInputs")
            .get("/data/internal/case-types/CARE_SUPERVISION_EPO/work-basket-inputs")
            .headers(LoginHeader.headers_17))

      .exec(http("XUI${service}_180_025_GetPaginationMetaData")
            .get("/data/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases/pagination_metadata?state=Open")
            .headers(LoginHeader.headers_0))

      .exec(http("XUI${service}_180_030_GetDefaultWorkBasketView")
>>>>>>> origin/master
            .get("/aggregated/caseworkers/:uid/jurisdictions/PUBLICLAW/case-types/CARE_SUPERVISION_EPO/cases?view=WORKBASKET&state=Open&page=1")
            .headers(LoginHeader.headers_0))

    }

    .pause(MinThinkTime , MaxThinkTime)

  //======================================================================================
  //Business process : Click on Terms and Conditions
  //below requests are Terms and Conditions page and relavant sub requests
  // ======================================================================================

  val manageCase_Logout =
    tryMax(2) {
<<<<<<< HEAD
      exec(http("XUI${service}_${SignoutNumber}_SignOut")
           .get("/api/logout")
           .headers(LoginHeader.headers_signout)
           .check(status.in(200, 304, 302)))
    }

  val manageCase_LogoutAdmin =
    tryMax(2) {
=======
>>>>>>> origin/master
      exec(http("XUI${service}_${SignoutNumberAdmin}_SignOut")
           .get("/api/logout")
           .headers(LoginHeader.headers_signout)
           .check(status.in(200, 304, 302)))
    }
<<<<<<< HEAD
  pause(105)//to be removed
=======
  pause(5)//to be removed
>>>>>>> origin/master

  val manageCase_LogoutGK =
    tryMax(2) {
      exec(http("XUI${service}_${SignoutNumberGK}_SignOut")
           .get("/api/logout")
           .headers(LoginHeader.headers_signout)
           .check(status.in(200, 304, 302)))
    }
  pause(5)//to be removed
}