package org.rakshith.actors

import akka.actor.Actor
import play.api.libs.json.{Json, OWrites}
import java.text.SimpleDateFormat
import java.util.{Calendar, TimeZone}

case class getTimeZone(timeZone: String)
case class currentTime(time: String,timezone: String)

class TimeActor extends Actor{

  implicit val writes: OWrites[currentTime] = Json.writes[currentTime]

  override def receive(): Receive ={
          case getTimeZone(timeZone) if timeZone=="IST" =>
            val time = Json.stringify(Json.toJson(currentTime(getTimeFromTimeZone("IST"),timeZone)))
            sender() ! jsonConversion(time)
          case getTimeZone(timeZone) if timeZone=="EST" =>
            val time = Json.stringify(Json.toJson(currentTime(getTimeFromTimeZone("EST"),timeZone)))
            sender() ! jsonConversion(time)
          case getTimeZone(timeZone) if timeZone=="GMT" =>
            val time = Json.stringify(Json.toJson(currentTime(getTimeFromTimeZone("GMT"),timeZone)))
            sender() ! jsonConversion(time)
          case getTimeZone(timeZone) if timeZone=="All" =>
            val timeZones = Array("IST","EST","GMT")
            val allTime = timeZones.map(a => Json.stringify(Json.toJson(currentTime(getTimeFromTimeZone(a),a))))
            sender() ! jsonConversion(allTime.mkString(","))
          case _ => sender() ! "Invalid Timezone"
  }

  def getTimeFromTimeZone(timeZone: String): String = {
    val cal = Calendar.getInstance()
    TimeZone.setDefault(TimeZone.getTimeZone(timeZone))
    cal.setTimeZone(TimeZone.getTimeZone(timeZone))
    val formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
    val currentTime = cal.getTime
    val dateFormat = formatter.format(currentTime)
    dateFormat
  }

  def jsonConversion(time: String) : String ={
    val jsonString: String = "{"+"\""+"Current Time"+"\""+":"+s"$time"+"}"
    jsonString
  }
}
