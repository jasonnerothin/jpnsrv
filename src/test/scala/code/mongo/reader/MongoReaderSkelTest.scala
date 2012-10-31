package code.mongo.reader

import com.mongodb.casbah.MongoCollection
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar

import com.mongodb.casbah.Imports._
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

import org.mockito.{Matchers, Mockito}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 1:02 PM
 */
@RunWith(classOf[JUnitRunner])
class MongoReaderSkelTest extends FunSuite with MockitoSugar {

  val mockCollection = mock[MongoCollection]
  val testInstance = new FakeReader

  test("findOne returns DBObject.empty when database returns None") {

    val dbObject: MongoDBObject = mock[MongoDBObject]
    Mockito.when(mockCollection.findOne(Matchers.eq(dbObject))(Matchers.anyObject())).thenReturn(None)

    val actual: DBObject = testInstance.findOne(dbObject)

    assert(DBObject.empty === actual, "expect nothing not to mean something")

  }

  test("findOne returns a result from database") {

    val dbObject: MongoDBObject = MongoDBObject("a" -> "b", "c" -> "d")
    val something: Option[DBObject] = Some(dbObject)

    Mockito.when(mockCollection.findOne(Matchers.eq(dbObject))(Matchers.anyObject())).thenReturn(something)

    val actual: DBObject = testInstance.findOne(dbObject)

    assert(something.head === actual, "expect something to be returned")

  }

  test("can't use null with findOne") {

    val caught: Exception = intercept[IllegalArgumentException] {
      testInstance.findOne(null)
    }

    val msg = caught.getMessage

    assert(msg === "requirement failed: params must be non-null", "nulls shouldn't be allowed")

  }

  test("find returns what it gets back from the database") {

    val testCursor: MongoCursor = mock[MongoCursor]

    Mockito.when(mockCollection.find()).thenReturn(testCursor)

    var actual = testInstance.find()

    assert(actual === testCursor, "expected result to come straight back")

  }

  test("find needs non-null params") {

    val caught: Exception = intercept[IllegalArgumentException] {
      testInstance.find(null)
    }
    val msg = caught.getMessage

    assert(msg == "requirement failed: params must be non-null", "nulls shouldn't be allowed")

  }

  test("whatever comes out of the db gets returned") {

    val testParams: MongoDBObject = mock[MongoDBObject]
    val testVal: MongoCursor = mock[MongoCursor]
    Mockito.when(testVal.isEmpty).thenReturn(true)

    Mockito.when(mockCollection.find(Matchers.eq(testParams))(Matchers.anyObject())).thenReturn(testVal)

    val actual = testInstance.find(testParams)

    assert(actual === testVal, "expected empty return type")
    assert(actual.isEmpty, "best be empty, as well")

  }

  class FakeReader extends MongoReaderSkel {
    override def collection(): MongoCollection = {
      mockCollection
    }
  }

}