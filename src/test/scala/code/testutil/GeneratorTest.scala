package code.testutil

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.joda.time.DateTime

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 8:38 PM
 * Provides...
 */
@RunWith(classOf[JUnitRunner])
class GeneratorTest extends FunSuite {

  val testInstance = new Generator

  test("randomStr is the right length"){
    val actual = testInstance.randomStr

    assert(actual.length === 10, "supposed to be 10 long")

  }

  test("randomStr is the right (arbitrary) length"){
    val actual = testInstance.randomStr(56)

    assert(actual.length === 56, "56 is just right")
  }

  test("random date in the past at least 1 second in the past"){
    val now = new DateTime()
    val actual = testInstance.dateInThePast()

    assert( now.minusSeconds(1) isAfter actual )

  }

  test("date in the future is at least 15 seconds in the future"){
    val now = new DateTime()
    val actual = testInstance.dateInTheFuture()

    assert(actual.minusSeconds(15) isAfter now)

  }

}
