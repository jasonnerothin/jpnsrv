package code.rest

import net.liftweb.http._
import js.JE.Str
import net.liftweb.http.rest._
import code.jackson.JsonWriterService
import net.liftweb.common.Full
import code.model.{Skill, Gig}
import code.mongo.loader.Loader
import net.liftweb.json.JsonAST.{JInt, JValue}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 * Time: 8:30 AM
 * Provides
 */

object JpnRest {

  var gigWriter: JsonWriterService[Gig] = _
  var gigLoader: Loader[Gig] = _
  var skillWriter: JsonWriterService[Skill] = _
  var skillLoader: Loader[Skill] = _

  def dispatch: LiftRules.DispatchPF = {

      case Req("api" :: "gigs" :: Nil, suffix, GetRequest) => () =>
        Full(asResponse(gigWriter.jsonForAllAndToAllAGoodNight(gigLoader)))

//    serve {
//      case Req("api" :: "skills" :: Nil, suffix, GetRequest) => () =>
//        Full(asResponse(skillWriter.jsonForAllAndToAllAGoodNight(skillLoader)))
//    }
//
//    serve {
//      case Req("rest" :: "gigs" :: entityId :: Nil,
//      suffix, GetRequest) => () =>
//        Full(asResponse(gigWriter.jsonForEntityId("jasonnerothin.com", gigLoader)))
//
//    }

      case Req("api"::_::Nil, suffix, GetRequest) => () => {
        val y = 23
        Full(asResponse("babababa"))
      }

      case _ => () =>{
        val x = 23
        Full(asResponse(skillWriter.jsonForAllAndToAllAGoodNight(skillLoader)))
      }

  }

  private def asResponse(json: String): LiftResponse = {
    JsonResponse(Str(json))
  }

}

object Foo {
  def method = JInt(12)
}
