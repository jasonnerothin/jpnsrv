package code.mongo

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.mockito.{Matchers, Mockito}
import com.mongodb.casbah.{MongoCollection, MongoDB}
import org.specs2.specification.BeforeEach
import org.specs2.internal.scalaz.Digit._0

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 8:44 PM
 */
@RunWith(classOf[JUnitRunner])
class MongoReadersTest extends FunSuite with MockitoSugar{

  var testInstance0: GigReader = _ // new GigReader(dbConnection)
  var testInstance1: SkillReader = _ // new SkillReader(dbConnection)

  test("connection is not null"){

    val dbConnection: DbConnection = mock[DbConnection]
    val mongoDb: MongoDB = mock[MongoDB]
    Mockito.when(dbConnection.mongoConnection).thenReturn(mongoDb)
    val mongoCollection: MongoCollection = mock[MongoCollection]
    Mockito.when(mongoDb.apply(Matchers.anyString())).thenReturn(mongoCollection)

    testInstance0 = new GigReader(dbConnection)
    assert( testInstance0.collection() != null, "only one thing to do, and you go and screw it up!")
  }

  test("connection should not be null again"){
    val dbConnection: DbConnection = mock[DbConnection]
    val mongoDb: MongoDB = mock[MongoDB]
    Mockito.when(dbConnection.mongoConnection).thenReturn(mongoDb)
    val mongoCollection: MongoCollection = mock[MongoCollection]
    Mockito.when(mongoDb.apply(Matchers.anyString())).thenReturn(mongoCollection)

    testInstance1 = new SkillReader(dbConnection)
    assert( testInstance1.collection() != null, "on, my goodnesss, what a lazy class you are")
  }

}
