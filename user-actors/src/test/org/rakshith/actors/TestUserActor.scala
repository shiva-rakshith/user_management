package org.rakshith.actors

import akka.actor.ActorSystem
import akka.testkit.{DefaultTimeout, ImplicitSender, TestActorRef, TestKit}
import org.scalatest.{BeforeAndAfterAll, Matchers, WordSpecLike}
import play.api.libs.json.Json

class TestUserActor extends TestKit(ActorSystem("UserActorTests")) with ImplicitSender with DefaultTimeout with WordSpecLike with BeforeAndAfterAll with Matchers{

   "Sending CRUD operations messages to UserActor" should {
     "Create the user details if the operation is 'CREATE'" in {
       val testActor = TestActorRef[UserActor]
       val body = """{"phoneNumber":"8499017912",
         "name":"kalyan",
         "age":30,
         "location":"Mumbai"}"""
       testActor ! user("create",Json.parse(body))
       expectMsg("User created successfully").orElse("User already exist")
     }

     "Show the user details if the operation is 'READ'" in {
       val testActor = TestActorRef[UserActor]
       val body = """{"phoneNumber":"8499017912"}"""
       testActor ! user("read",Json.parse(body))
       expectMsg("""{"phoneNumber":"8499017912","name":"kalyan","age":30,"location":"Mumbai"}""")
     }

     "Update the user details if the operation is 'UPDATE'" in {
       val testActor = TestActorRef[UserActor]
       val body = """{"phoneNumber":"8499017912",
         "name":"sai",
         "age":32
         "location":"Hyderabad"}"""
       testActor ! user("create",Json.parse(body))
       expectMsg("Update user details from kalyan,30,Mumbai to sai,32,Hyderabad")
     }

     "Delete the user details if the operation is 'DELETE'" in {
       val testActor = TestActorRef[UserActor]
       val body = """{"phoneNumber":"8499017912"}"""
       testActor ! user("create",Json.parse(body))
       expectMsg("Deleted user details successfully")
     }

   }
}
