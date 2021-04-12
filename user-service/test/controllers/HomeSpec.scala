package controllers

import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers.{OK, defaultAwaitTimeout, status}

@RunWith(classOf[JUnitRunner])
class HomeSpec extends Specification{

  def application: Application = GuiceApplicationBuilder().build()

  "Home Controller" should {
    "return success response for getTime API" in {
      val controller = application.injector.instanceOf[controllers.HomeController]
      val result = controller.getTime()(FakeRequest())
      status(result) must equalTo(OK)
        }
      }

}



