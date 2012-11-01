package code.mongo.reader

import com.mongodb.DBObject
import com.mongodb.casbah.commons.MongoDBObject

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 7:15 PM
 * Provides a trait that generates a single parameter
 * DBObject for searching.
 */
trait SearchParam {

  /**
   * @param key search for DBObjects with the key
   * @param value mapped to this value
   * @return the search object
   */
  def searchParam(key: String, value: String): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += key -> value
    new MongoDBObject(builder.result())
  }
}
