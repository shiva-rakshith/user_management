package controllers

import org.specs2.mutable.Specification
import play.api.Application
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.test.FakeRequest
import play.api.test.Helpers.{OK, defaultAwaitTimeout, status}

class UserSpec extends Specification{
  def application: Application = GuiceApplicationBuilder().build()
  val controller = application.injector.instanceOf[controllers.UserController]

  "User Controller" should {
    "return success response for createUser API" in {
      val result = controller.createUser()(FakeRequest())
      status(result) must equalTo(OK)
    }

    "return success response for readUser API" in {
      val result = controller.readUser()(FakeRequest())
      status(result) must equalTo(OK)
    }

    "return success response for updateUser API" in {
      val result = controller.updateUser()(FakeRequest())
      status(result) must equalTo(OK)
    }

    "return success response for deleteUser API" in {
      val result = controller.deleteUser()(FakeRequest())
      status(result) must equalTo(OK)
    }
  }
}
