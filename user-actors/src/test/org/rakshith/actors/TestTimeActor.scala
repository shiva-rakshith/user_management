package org.rakshith.actors

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import java.text.SimpleDateFormat
import java.util.{Calendar, TimeZone}

class TestTimeActor extends TestKit(ActorSystem("UserActorTests")) with ImplicitSender with DefaultTimeout with WordSpecLike with BeforeAndAfterAll with Matchers{
   def getTime(timezone : String): String ={
     val cal = Calendar.getInstance()
     TimeZone.setDefault(TimeZone.getTimeZone(timezone))
     cal.setTimeZone(TimeZone.getTimeZone(timezone))
     val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
     val currentTime = cal.getTime
     val dateFormat = formatter.format(currentTime)
     dateFormat
   }
  "Sending a timezone message to UserActor" should {
    "display the IST time when timezone is IST" in {
      val testActor = TestActorRef[TimeActor]
      testActor ! getTimeZone("IST")
      val time = getTime("IST")
      expectMsg("{"+ "\""+"Current Time"+"\"" +":"+ "{"+ "\""+"time"+"\""+":"+"\""+s"$time"+"\""+"," +"\""+"timezone"+"\""+":"+ "\""+"IST"+"\""+"}}"
      )
    }

    "display the EST time when timezone is EST" in {
      val testActor = TestActorRef[TimeActor]
      testActor ! getTimeZone("EST")
      val time = getTime("EST")
      expectMsg("{"+ "\""+"Current Time"+"\"" +":"+ "{"+ "\""+"time"+"\""+":"+"\""+s"$time"+"\""+"," +"\""+"timezone"+"\""+":"+ "\""+"EST"+"\""+"}}"
      )
    }

    "display the GMT time when timezone is GMT" in {
      val testActor = TestActorRef[TimeActor]
      testActor ! getTimeZone("GMT")
      val time = getTime("GMT")
      expectMsg("{"+ "\""+"Current Time"+"\"" +":"+ "{"+ "\""+"time"+"\""+":"+"\""+s"$time"+"\""+"," +"\""+"timezone"+"\""+":"+ "\""+"GMT"+"\""+"}}"
      )
    }

    "display the invalid timezone when timezone is not IST or EST or GMT" in {
      val testActor = TestActorRef[TimeActor]
      testActor ! getTimeZone("UTC")
      expectMsg("Invalid Timezone")
    }
  }
}
