package org.rakshith.actors

import akka.actor.Actor
import com.redis.RedisClient
import com.typesafe.config.{Config, ConfigFactory}
import play.api.libs.json.{JsValue, Json, OWrites}

case class user(action : String,body :JsValue)
case class createUser(phoneNumber : String,name : String,age : Int,location : String)

class UserActor extends Actor{

  implicit val writes: OWrites[createUser] = Json.writes[createUser]

  override def receive() : Receive = {
    case user(action,body) if action == "create" =>
      sender() ! create(body)
    case user(action,body) if action == "delete" =>
      sender() ! delete(body)
    case user(action,body) if action == "read" =>
      sender() ! read(body)
    case user(action,body) if action == "update" =>
      sender() ! update(body)
  }

  val conf: Config = ConfigFactory.load
  val hostName : String = conf.getString("redis-host")
  val port :Int = conf.getInt("redis-port")
  val redis = new RedisClient(hostName,port)

  private def create(body: JsValue) : String ={
    val phoneNumber : String = (body \ "phoneNumber").as[String]
    if(!(redis.exists(phoneNumber))){
      val name : String = (body \ "name").as[String]
      val age : Int = (body \ "age").as[Int]
      val location : String = (body \ "location").as[String]
      val userDetails = Json.stringify(Json.toJson(createUser(phoneNumber,name,age,location)))
      redis.set(phoneNumber,userDetails)
      "User created successfully"
    }
    else
    {
      "User already exist"
    }
  }

  private def delete(body:JsValue): String ={
    val phoneNumber : String = (body \ "phoneNumber").as[String]
    if(redis.exists(phoneNumber)){
      redis.del(phoneNumber)
      "Deleted user details successfully"
    }
    else
      {
        "User do not exist"
      }
  }

  private def read(body:JsValue) : String ={
    val phoneNumber : String = (body \ "phoneNumber").as[String]
    if(redis.exists(phoneNumber)){
      val userDetails = redis.get(phoneNumber)
      show(userDetails)
    }
    else
    {
      "User do not exist"
    }
  }

  private def update(body:JsValue) : String ={
    val phoneNumber : String = (body \ "phoneNumber").as[String]
    val newName : String = (body \ "name").as[String]
    val newAge : Int = (body \ "age").as[Int]
    val newLocation : String = (body \ "location").as[String]

    if(redis.exists(phoneNumber)){
      val details = redis.get(phoneNumber)
      val exactDetails = show(details)
      val parsedDetails = Json.parse(exactDetails)
      val oldName : String = (parsedDetails \ "name").as[String]
      val oldAge : Int = (parsedDetails \ "age").as[Int]
      val oldLocation : String = (parsedDetails \ "location").as[String]
      val userDetails = Json.stringify(Json.toJson(createUser(phoneNumber,newName,newAge,newLocation)))
      redis.set(phoneNumber,userDetails)
      s"Update user details from $oldName, $oldAge, $oldLocation to $newName, $newAge, $newLocation"
    }
    else
    {
      "User do not exist"
    }

  }

  def show(x: Option[String]): String = x match {
    case Some(s) => s
    case None => "?"
  }

}


