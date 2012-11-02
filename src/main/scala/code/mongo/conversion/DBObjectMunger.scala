package code.mongo.conversion

import code.model.{EntityKey, Entity}
import com.mongodb.casbah.commons.{MongoDBList, MongoDBObject}
import collection.mutable
import org.bson.types.ObjectId

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 10:21 PM
 * Uses the contents of the `DBObject` map to
 * populate an `Entity`. Note that this `Entity` may need
 * represent a fully-fledged `Entity`, but merely a naive
 * representation of the fields contained within the database
 */
trait DBObjectMunger[Entity] {

  /**
   * Takes properties of d and sticks them into a
   * new instance of e, which is returned. Note: returned `E`
   * may not be fully-initialized, but will contain all properties
   * of `D` that need only to simply copied or calculated
   * @param d data source
   * @return a new instance of e
   */
  def populate(d: MongoDBObject): Entity

  /**
   * Grabs the `Entity`'s "_id" out of the mongo dbobject
   * and any oids for related `Entity`s as well
   * @param d database object
   * @return all of the oids, in one little package
   */
  def extractOids(d: MongoDBObject) : EntityKey

  // TODO consider getting rid of in favor of Options
  /**
   * Convenience method to turn scala-able Options into java-like
   * nulls in case Jackson requires something like that
   * @param propName property name we seek
   * @param d db result
   * @return a string or null
   */
  protected[conversion] def stringOrNull(propName: String, d: MongoDBObject): String ={
    val str: Option[String] = d.getAs[String](propName)
    if ( str.isEmpty ) null else str.get
  }

  protected[conversion] def oidAsStringOrNull(propName: String, d: MongoDBObject): String = {
    val opt: Option[ObjectId] = d.getAs[ObjectId](propName)
    val oid = opt getOrElse null
    if ( oid == null ) null else oid.toString
  }

  /**
   * Converts a known property into a List of strings
   * @param propName property
   * @param d database result
   * @return a (possibly empty) list
   */
  protected[conversion] def asList(propName: String, d: MongoDBObject): List[String] ={
    def asMongoList():MongoDBList = {
      try{
        d.as[MongoDBList](propName)
      } catch{
        case _: Throwable =>
          MongoDBList()
      }
    }
    val list = asMongoList()
    var stringList: mutable.MutableList[String] = new mutable.MutableList[String]
    for { item <- list.toArray } stringList += item.toString
    stringList.toList
  }

}
