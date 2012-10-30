package code.mongo

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import code.annotations.IntegrationTest

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 11:30 PM
 */
@RunWith(classOf[JUnitRunner])
@IntegrationTest("Needs a running mongo db")
class DbConnectionIntegrationTest extends FunSuite{

  val testInstance: DbConnection = DbConnection.INSTANCE
  var ignore = false

  test("instance is not null") {
    if (!ignore) assert(testInstance != null)
  }

  test("two instances are the same") {
    if (!ignore) {
      val con0 = DbConnection()
      val con1 = DbConnection()
      val con2 = DbConnection.INSTANCE
      val con3 = DbConnection.apply()
      assert(con0 === con1)
      assert(con1 === con2)
      assert(con2 === con3)
      assert(con3 === testInstance)
    }
  }

  test("connection is open for business") {
    if (!ignore) {
      testInstance.mongoConnection.getName().equals(DbConnection.DATABASE_NAME)
    }
  }

}
