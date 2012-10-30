package code.mongo

import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoCursor
import com.mongodb.DBObject


/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 10:51 PM
 */
trait MongoReader {

  def findOne(params: MongoDBObject) : DBObject

  def find(params: MongoDBObject) : MongoCursor

  def findAll() : MongoCursor

}
