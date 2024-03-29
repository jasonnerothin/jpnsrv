package code.mongo.reader

import com.mongodb.casbah.Imports._

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 12:55 PM
 * Provides a skeletal implementation of MongoReader
 */
abstract class MongoReaderSkel extends MongoReader{

  def collection() : MongoCollection

  def findOne(params: MongoDBObject) : DBObject = {
    require(params != null, "params must be non-null")
    val col = collection()
    val result: Option[DBObject] = col.findOne[MongoDBObject](params)
    if( result.isEmpty ) DBObject.empty else result.head
  }

  def find(params: MongoDBObject) : MongoCursor = {
    require(params != null, "params must be non-null")
    collection().find(params)
  }

  def find() : MongoCursor = {
    collection().find()
  }

}
