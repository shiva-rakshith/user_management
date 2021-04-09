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
class HomeController @Inject()(@Named("userActor") userActor: ActorRef, cc: ControllerComponents)(implicit exec: ExecutionContext) extends AbstractController(cc){

  def home : Action[AnyContent] = Action { implicit request =>
    Ok("Home Page")
  }

  def getTime(timeZone : String) : Action[AnyContent] = Action.async { implicit request =>
   val result : Future[String] = ask(userActor,getTimeZone(timeZone))(5 second).mapTo[String]
    result.map(response => Ok(response).withHeaders(CONTENT_TYPE -> "application/json"))
  }

}