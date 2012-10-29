package code.repos

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 9:19 PM
 */
@RunWith(classOf[JUnitRunner])
class GigRepositoryTest extends FunSuite {

    test("yo mammy"){
      import code.repos.GigRepository
      assert(23+25 == 48)
    }

}
