package code.mongo.reader

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoCursor
import com.mongodb.DBObject
import code.model.Entity


/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 10:51 PM
 */
trait MongoReader {

  /**
   * @param params query params
   * @return zero or one db results
   */
  def findOne(params: MongoDBObject) : DBObject

  /**
   * @param params query params
   * @return db cursor for result collection
   */
  def find(params: MongoDBObject) : MongoCursor

  /**
   * @return all
   */
  def find(): MongoCursor

}
