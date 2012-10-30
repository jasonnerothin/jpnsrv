package code.mongo.conversion

import org.junit.runner.RunWith
import org.specs2.runner.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import org.specs2.specification.BeforeEach
import code.model.Gig
import com.mongodb.DBObject
import com.mongodb.casbah.commons.{MongoDBList, MongoDBObject}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 1:08 AM
 */
@RunWith(classOf[JUnitRunner])
class DbObjectMungerTest extends FunSuite with MockitoSugar{

  val testInstance: TestDbObjectMunger = new TestDbObjectMunger
  var dbo: MongoDBObject = _

  def before{

    val builder = MongoDBObject.newBuilder
    builder += "xyz"->"pdq"
    builder += "123" -> "456"

    val listBuilder = MongoDBList.newBuilder
    listBuilder += "baloney"
    listBuilder += "provalone"
    listBuilder += "mustard"

    builder += "sammichstuff" -> listBuilder.result

    dbo = new MongoDBObject(builder.result())
  }

  // TODO unless Jackson forbids it or uses Nil or something instead
  test("stringOrNull returns null when None"){
    before
    val actual = testInstance.stringOrNull("foo", dbo)
    assert( actual == null, "want null if property isn't there.")
  }

  class TestDbObjectMunger extends DBObjectMunger[Gig]{
    def populate(d: MongoDBObject) = null // testing abstract methods is lame
    def extractOids(d: MongoDBObject) = null
  }

  test("stringOrNull returns the right thing"){
    before
    val actual = testInstance.stringOrNull("xyz", dbo)
    assert("pdq" === actual, "the joke just wouldn't work, otherwise...")
  }

  test("asList works right"){
    before
    val actual = testInstance.asList("sammichstuff", dbo)
    assert(List("baloney", "provalone", "mustard") == actual, "a list apart, apparently")
  }

  test("mongoDbObject doesn't do funny type inference"){
    before
    val actual = testInstance.stringOrNull("123", dbo)
    assert(actual.getClass == classOf[String], "type inference is incorrect")
  }
}
