package code.repos

import org.scalatest.{BeforeAndAfterEach, FunSuite}

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.mock.MockitoSugar
import code.mongo.loader.GigLoader

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 9:19 PM
 */
@RunWith(classOf[JUnitRunner])
sealed class GigRepositoryTest extends FunSuite with BeforeAndAfterEach with MockitoSugar {

  var testInstance : GigRepository = _
  var gigReader: GigLoader = _

  override def beforeEach(){
    testInstance = new GigRepository(gigReader)
  }

}
