package controllers

import akka.actor.ActorRef
import akka.pattern.ask
import org.rakshith.actors.getTimeZone
import play.api.mvc._

import javax.inject._
import scala.concurrent.duration.DurationInt
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class HomeController @Inject()(@Named("timeActor") timeActor: ActorRef, cc: ControllerComponents)(implicit exec: ExecutionContext) extends AbstractController(cc){

  def home : Action[AnyContent] = Action { implicit request =>
    Ok("Home Page")
  }

  def getTime() : Action[AnyContent] = Action.async { implicit request =>
    val body = request.body.asJson.get
    val timezone: String = (body \ "timezone").as[String]
    val result : Future[String] = ask(timeActor,getTimeZone(timezone))(5 second).mapTo[String]
    result.map(response => Ok(response).withHeaders(CONTENT_TYPE -> "application/json"))
  }

}