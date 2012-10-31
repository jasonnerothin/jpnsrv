package code.mongo.loader

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSuite
import org.scalatest.mock.MockitoSugar
import code.mongo.reader.{SkillReader, GigReader}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 12:09 AM
 */
@RunWith(classOf[JUnitRunner])
sealed class GigLoaderTest extends FunSuite with MockitoSugar{

  var testInstance: GigLoader = _
  var gigReader: GigReader = _
  var skillReader: SkillReader = _

}
