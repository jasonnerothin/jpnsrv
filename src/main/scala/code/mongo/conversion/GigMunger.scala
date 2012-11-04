package code.mongo.conversion

import code.model.{Skill, GigKeys, Gig}
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.{Duration, DateTime}
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 10:31 PM
 */
class GigMunger extends DBObjectMunger[Gig] {

  RegisterJodaTimeConversionHelpers() // no point in ever turning them off, really

  /**
   * Takes properties of d and sticks them into a
   * new instance of e, which is returned. Note: returned `Gig`
   * may not be fully-initialized, but will contain all properties
   * of `Gig` that need only to simply copied or calculated
   *
   * requires that d have a non-null gigId set
   * @param d data source
   * @return a new instance of e
   */
  def populate(d: MongoDBObject): Gig = {

    assert(d != null)

    def string(propName: String): Option[String] = {
      d.getAs[String](propName)
    }

    def dateTime(propName: String): Option[DateTime] = {
      RegisterJodaTimeConversionHelpers()
      d.getAs[DateTime](propName)
    }

    val startDate = dateTime("startDate")
    val endDate = dateTime("endDate")

    def durationInMillis(): Long = {
      val now = new DateTime(System.currentTimeMillis())
      val end = if (endDate == None) now else endDate.get
      val d = new Duration(startDate.get, end)
      d.getMillis
    }

    // TODO will jackson tolerate nulls? or does it want Option[Stings]?
    val gig = new Gig(
      string("gigId") getOrElse null,
      string("gigName") getOrElse null,
      string("title") getOrElse null,
      string("employer") getOrElse null,
      string("blurb") getOrElse null,
      string("cityState") getOrElse null,
      string("methodology") getOrElse null,
      string("result") getOrElse null,
      startDate getOrElse null,
      endDate getOrElse null,
      durationInMillis(),
      List.empty[Skill]
    )

    if(gig.gigId == null) throw new IllegalStateException("Illegal db result with null gigId: " + d.toString() )

    gig

  }

  /**
   * Grabs the `Gigs`'s "_id" out of the mongo dbobject
   * and any oids for related `Skill`s as well
   * @param d database object
   * @return all of the oids, in one little package
   */
  def extractOids(d: MongoDBObject) : GigKeys = {
    val oid = oidAsStringOrNull("_id", d)
    require(oid != null, "_id value may not be null")
    val list = asList("skills", d)
    new GigKeys(oid, list)
  }

}