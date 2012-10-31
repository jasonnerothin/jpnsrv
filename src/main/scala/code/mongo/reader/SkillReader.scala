package code.mongo.reader

import com.mongodb.casbah.MongoCollection
import code.mongo.DbConnection

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 12:59 PM
 * Provides...
 */
class SkillReader(val dbConnection: DbConnection) extends MongoReaderSkel{

  private val skillCollection: MongoCollection = dbConnection.mongoConnection("skills")

  final override def collection() = {
    skillCollection
  }

}
