package code.rest

import net.liftweb.http._
import js.JE.Str
import js.JsExp
import net.liftweb.http.rest._
import code.jackson.{JsonWriterService, SkillWriterService, GigWriterService}
import testing.Item
import net.liftweb.json.JsonAST.{JArray, JString, JValue, JInt}
import javax.swing.JList
import collection.GenTraversable
import net.liftweb.common.Full
import code.model.{Skill, Gig}
import code.mongo.loader.Loader

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

  serve {
    case Req("api" :: "gigs" :: Nil,
      suffix, GetRequest) => () =>
        Full(asResponse(gigWriter.jsonForAllAndToAllAGoodNight(gigLoader)))

    case Req("api" :: "skills" :: Nil, suffix, GetRequest) => () =>
      Full(asResponse(skillWriter.jsonForAllAndToAllAGoodNight(skillLoader)))

    case Req("rest" :: "gigs" :: entityId :: Nil,
      suffix, GetRequest) => () =>
        Full(asResponse(gigWriter.jsonForEntityId("jasonnerothin.com", gigLoader)))

  }

  private def asResponse(json: String): LiftResponse = {
    JsonResponse(Str(json))
  }

}


