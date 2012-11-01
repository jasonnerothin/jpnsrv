package code.jackson

import code.mongo.loader.{SkillLoader, GigLoader}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.mock.MockitoSugar
import code.model.{Skill, Gig}
import code.testutil.Generator
import org.joda.time.DateTime
import org.mockito.Matchers._
import org.mockito.Mockito._

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 * Time: 2:34 AM
 */
@RunWith(classOf[JUnitRunner])
class WriterServicesTest extends FunSuite with MockitoSugar with BeforeAndAfterEach {

  var gigWriterService: GigWriterService = _
  var skillWriterService: SkillWriterService = _
  var gigLoader: GigLoader = _
  var skillLoader: SkillLoader = _

  override def beforeEach() {

    gigLoader = mock[GigLoader]
    skillLoader = mock[SkillLoader]

    gigWriterService = new GigWriterService(gigLoader)
    skillWriterService = new SkillWriterService(skillLoader)

  }

  test("gigWriterService byId works") {

    when(gigLoader.loadById(anyString())).thenReturn(makeMeAGig())

    val actual = gigWriterService.jsonForEntityId(str(), gigLoader)

    assert(actual != null)
    assert(actual.length > 2)

  }

  test("skillWriterService byId works") {

    when(skillLoader.loadById(anyString())).thenReturn(makeMeASkill())
    val actual = skillWriterService.jsonForEntityId(str(), skillLoader)

    assert(actual != null)
    assert(actual.length > 2)

  }

  test("gigWriterService forAll works") {

    when(gigLoader.loadAll()).thenReturn(List(makeMeAGig(), makeMeAGig()))
    val actual = gigWriterService.jsonForAllAndToAllAGoodNight(gigLoader)

    assert(actual != null)
    assert(actual.length > 2)
  }

  test("skillWriterService forAll works") {

    when(skillLoader.loadAll()).thenReturn(List(makeMeASkill(), makeMeASkill()))
    val actual = skillWriterService.jsonForAllAndToAllAGoodNight(skillLoader)

    assert(actual != null)
    assert(actual.length > 2)
  }

  def makeMeAGig(): Gig = {
    new Gig(str(), str(), str(), str(), str(), str(), str(), str(),
      date(pastOrFuture = true), date(pastOrFuture = true), 230000, List())
  }

  def makeMeASkill(): Skill = {
    new Skill(str(), str(), List(str(), str()), 23, List())
  }

  def str(): String = {
    new Generator().randomStr
  }

  def date(pastOrFuture: Boolean): DateTime = {
    val g = new Generator
    if (pastOrFuture) g.dateInThePast() else g.dateInTheFuture()
  }

}
