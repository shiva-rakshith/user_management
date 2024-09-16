package controllers

import akka.actor.ActorRef
import akka.pattern.ask
import org.rakshith.actors.{getTimeZone, user}
import play.api.mvc._
import javax.inject._
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class UserController @Inject()(@Named("userActor") userActor: ActorRef, cc: ControllerComponents)(implicit exec: ExecutionContext) extends AbstractController(cc){

  def createUser(): Action[AnyContent] = Action.async { implicit request =>
      val body = request.body.asJson.get
      val result : Future[String] = ask(userActor,user("create",body))(5 second).mapTo[String]
      result.map(response => Ok(response).withHeaders(CONTENT_TYPE -> "application/json"))
  }

  def updateUser(): Action[AnyContent] = Action.async { implicit request =>
    val body = request.body.asJson.get
    val result : Future[String] = ask(userActor,user("update",body))(5 second).mapTo[String]
    result.map(response => Ok(response).withHeaders(CONTENT_TYPE -> "application/json"))
  }

  def deleteUser(): Action[AnyContent] = Action.async { implicit request =>
    val body = request.body.asJson.get
    val result : Future[String] = ask(userActor,user("delete",body))(5 second).mapTo[String]
    result.map(response => Ok(response).withHeaders(CONTENT_TYPE -> "application/json"))
  }

  def readUser(): Action[AnyContent] = Action.async { implicit request =>
    val body = request.body.asJson.get
    val result : Future[String] = ask(userActor,user("read",body))(20 second).mapTo[String]
    result.map(response => Ok(response).withHeaders(CONTENT_TYPE -> "application/json"))
  }

}
