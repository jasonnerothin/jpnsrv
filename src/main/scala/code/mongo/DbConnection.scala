package code.mongo

import com.mongodb.casbah.Imports._

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 11:14 PM
 * Provides a singleton instance...
 */
object DbConnection {

  def DATABASE_NAME = "jpnsrv"

  lazy val INSTANCE = new DbConnection
  def apply() = INSTANCE

}

class DbConnection private{
  import com.mongodb.casbah._
  val mongoConnection = MongoConnection("localhost")
}