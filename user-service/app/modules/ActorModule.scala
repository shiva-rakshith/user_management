package modules

import com.google.inject.AbstractModule
import org.rakshith.actors.{TimeActor, UserActor}
import play.libs.akka.AkkaGuiceSupport

class ActorModule extends AbstractModule with AkkaGuiceSupport{
  override def configure(): Unit ={
    bindActor(classOf[TimeActor], "timeActor")
    bindActor(classOf[UserActor],"userActor")
  }
}
