package code.rest

import net.liftweb.http._
import code.jackson.JsonWriterService
import net.liftweb.common.Full
import code.model.{Skill, Gig}
import code.mongo.loader.Loader
import rest.RestHelper

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 * Time: 8:30 AM
 * Provides rest services for `Gigs` and  `Skills`
 */
object GigRestService extends RestHelper {

  var gigWriter: JsonWriterService[Gig] = _
  var gigLoader: Loader[Gig] = _

  serve("rest" / "gigs" prefix {

    case Req("all"::Nil, suffix, GetRequest) =>
      () => Responder.jsonResponse(gigWriter.jsonForAllAndToAllAGoodNight(gigLoader))

    // /rest/gigs/search/someGigId or /rest/gigs/search?gigId=someGigId
    case "search" :: gigId JsonGet _ =>
       () => {
         if( S.params("gigId").isEmpty )
           Full(BadResponse())
         else{
           val gigId = S.params("gigId").head
           val b = new StringBuilder
           gigWriter.writeJsonEntityId(b, gigId)
           Responder.jsonResponse(b.toString())
         }
       }

  })

}

object SkillRestService extends RestHelper{

  var skillWriter: JsonWriterService[Skill] = _
  var skillLoader: Loader[Skill] = _

  serve( "rest" / "skills" prefix {

    case Req("all"::Nil, suffix, GetRequest) =>
      () => Responder.jsonResponse(skillWriter.jsonForAllAndToAllAGoodNight(skillLoader))

    // /rest/skills/search/someSkillId or /rest/skills/search?skillId=someSkillId
    case "search" :: skillId JsonGet _ =>
      () => {
        if( S.params("skillId").isEmpty )
          Full(BadResponse())
        else{
          val skillId = S.params("skillId").head
          val b = new StringBuilder
          skillWriter.writeJsonEntityId(b, skillId)
          Responder.jsonResponse(b.toString())
        }
      }
  })

}

protected [rest] object Responder{

  def jsonResponse(json: String): Full[LiftResponse] = {
    Full(PlainTextResponse(json, List("ContentType" -> "application/json"), 200))
  }

}