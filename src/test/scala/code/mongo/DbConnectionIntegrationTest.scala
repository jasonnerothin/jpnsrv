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
class DbConnectionIntegrationTest extends FunSuite {

  var ignore = true

  test("instance is not null") {
    if (!ignore) assert(DbConnection() != null)
  }

  test("two instances are the same") {
    if (!ignore) {
      var con0 = DbConnection()
      var con1 = DbConnection()
      var con2 = DbConnection.INSTANCE
      var con3 = DbConnection.apply()
      assert(con0 === con1)
      assert(con1 === con2)
      assert(con2 === con3)
    }
  }

  test("connection is open for business") {
    if (!ignore) {
      var cnxn = DbConnection()
      cnxn.mongoConnection.getDatabaseNames().contains(DbConnection.DATABASE_NAME)
    }
  }

}
