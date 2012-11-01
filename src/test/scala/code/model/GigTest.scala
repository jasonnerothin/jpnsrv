package code.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.mock.MockitoSugar
import code.testutil.Generator
import org.joda.time.DateTime

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 8:36 PM
 * Provides...
 */
@RunWith(classOf[JUnitRunner])
class GigTest extends FunSuite with MockitoSugar with BeforeAndAfterEach{

  var testInstance: Gig = _

  def dateMe(): DateTime = new DateTime(new Generator().someAs()(1).l)

  override def beforeEach(){
    testInstance = new Gig(
      str(),
      str(),
      str(),
      str(),
      str(),
      str(),
      str(),
      str(),
      dateMe(),
      dateMe(),
      22,
      List()
    )
  }

  test("merge with skills works"){

    val skills = List(skillMe(), skillMe(), skillMe())
    val actual = testInstance mergeWith skills

    assert( actual.entityId === testInstance.entityId)
    assert( actual.blurb === testInstance.blurb)
    assert( actual.cityState === testInstance.cityState )
    assert( actual.durationInMillis === testInstance.durationInMillis )
    assert( actual.employer === testInstance.employer )
    assert( actual.endDate === testInstance.endDate )
    assert( actual.gigId === testInstance.gigId )
    assert( actual.gigName === testInstance.gigName )
    assert( actual.methodology === testInstance.methodology )
    assert( actual.result === testInstance.result )
    assert( actual.startDate === testInstance.startDate )
    assert( actual.title === testInstance.title )

    // lists work like stacks in scala, so we need to reverse
    assert( actual.skills === skills)
  }

  def str(): String = new Generator().randomStr

  def skillMe(): Skill = {
    new Skill(
      str(),
      str(),
      List(str(),str()),
      22,
      List()
    )
  }

}
