package code.mongo.reader

import code.mongo.DbConnection
import com.mongodb.casbah.MongoCollection

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 9:36 AM
 */
class GigReader(val dbConnection: DbConnection) extends MongoReaderSkel{

  private val gigsCollection: MongoCollection = dbConnection.mongoConnection.apply("gigs")

  final override def collection() : MongoCollection = {
    gigsCollection
  }

}
