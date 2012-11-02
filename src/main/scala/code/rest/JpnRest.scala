package code.rest

import net.liftweb.http._
import code.jackson.JsonWriterService
import net.liftweb.common.Full
import code.model.{Skill, Gig}
import code.mongo.loader.Loader
import net.liftweb.json.JsonAST.{JInt, JValue}
import rest.RestHelper

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 * Time: 8:30 AM
 * Provides
 */

object JpnRest extends RestHelper {

  var gigWriter: JsonWriterService[Gig] = _
  var gigLoader: Loader[Gig] = _
  var skillWriter: JsonWriterService[Skill] = _
  var skillLoader: Loader[Skill] = _

  serve("api" / "gigs" prefix {

    case Req("all"::Nil, suffix, GetRequest) =>
      () => Full(jsonResponse(skillWriter.jsonForAllAndToAllAGoodNight(skillLoader)))

    case Req( _, suffix, GetRequest) =>
      () => Full(ResponseWithReason(BadResponse(), "bad request"))

  })

  private def jsonResponse(json: String): LiftResponse = {
    PlainTextResponse(json, List("ContentType" -> "application/json"), 200)
  }

}