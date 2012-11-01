package code.mongo.reader

import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import com.mongodb.casbah.commons.MongoDBObject

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 7:19 PM
 */
@RunWith(classOf[JUnitRunner])
class SearchParamTest extends FunSuite with BeforeAndAfterEach{

  val clazz = new TestClass
  val param = clazz.param

  test("param is not null"){
    assert(param != null, "expected non null")
  }

  test("param is a DBObject"){
    assert(param.isInstanceOf[MongoDBObject], "is a dbobject")
  }

  test("has foo for a key"){
    param.contains("foo")
  }

  test("bar is the value"){
    val actual = param.get("foo")
    assert( actual.get == "bar", "really need a bar right now")
  }

  class TestClass extends SearchParam{
    val param = searchParam("foo", "bar")
  }
}
