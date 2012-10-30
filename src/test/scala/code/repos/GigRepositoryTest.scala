package code.repos

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import org.specs2.specification.BeforeEach
import code.mongo.MongoReader

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 9:19 PM
 */
@RunWith(classOf[JUnitRunner])
sealed class GigRepositoryTest extends FunSuite with MockitoSugar{

    var testMe : GigRepository = _
    var mongoReader : MongoReader = _

    test("yo mammy"){
      assert(23+25 == 48)
    }

}
