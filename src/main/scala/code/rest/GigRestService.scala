package code.rest

import net.liftweb.http._
import code.jackson.JsonWriterService
import net.liftweb.common.Full
import code.model.{Skill, Gig}
import code.mongo.loader.Loader
import rest.RestHelper
import org.joda.time.DateTime
import org.codehaus.jackson.map.{SerializationConfig, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.joda.time.chrono.GregorianChronology
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

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

  })

}

object SkillRestService extends RestHelper{

  var skillWriter: JsonWriterService[Skill] = _
  var skillLoader: Loader[Skill] = _

  serve( "rest" / "skills" prefix {

    case Req("all"::Nil, suffix, GetRequest) =>
      () => Responder.jsonResponse(skillWriter.jsonForAllAndToAllAGoodNight(skillLoader))

  })


}

protected [rest] object Responder{

  def jsonResponse(json: String): Full[LiftResponse] = {
    Full(PlainTextResponse(json, List("ContentType" -> "application/json"), 200))
  }

}

object RunMe{


  def main(args: Array[String]) {

    RegisterJodaTimeConversionHelpers()

    val oct24_2012 = new DateTime(2012, 10, 24, 18, 0, 0, 0)
    val aug16_2010 = new DateTime(2010, 8, 16, 8, 0, 0, 0)
    val mar17_2008 = new DateTime(2008, 3, 17, 17, 0, 0, 0)
    val nov5_2007 = new DateTime(2007, 11, 5, 6, 0, 0, 0)
    val oct5_2005 = new DateTime(2005, 10, 5, 11, 0, 0, 0)
    val sep29_2004 = new DateTime(2004, 9, 24, 16, 30, 0, 0)

    val ends = Array(
      new Gig(null, null, null, null, null, null, null, null, null, oct24_2012, 0,List())
      ,new Gig(null, null, null, null, null, null, null, null, null, aug16_2010, 0,List())
      ,new Gig(null, null, null, null, null, null, null, null, null, mar17_2008, 0,List())
      ,new Gig(null, null, null, null, null, null, null, null, null, nov5_2007, 0,List())
      ,new Gig(null, null, null, null, null, null, null, null, null, oct5_2005, 0,List())
      ,new Gig(null, null, null, null, null, null, null, null, null, sep29_2004, 0,List())
    )

    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true)

    for( gig <- ends ){
       println( mapper.writeValueAsString(gig) )
    }

  }
}