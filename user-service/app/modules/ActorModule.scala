package modules

import com.google.inject.AbstractModule
import play.libs.akka.AkkaGuiceSupport
import org.rakshith.actors.UserActor

class ActorModule extends AbstractModule with AkkaGuiceSupport{
  override def configure(): Unit ={
    bindActor(classOf[UserActor], "userActor")
  }
}
