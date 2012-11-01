package code.jackson

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import code.model.{Skill, Gig}
import code.mongo.loader.{SkillLoader, GigLoader}
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._
import code.testutil.Generator
import org.mockito.Matchers
import util.Random

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 * Time: 1:23 AM
 */
@RunWith(classOf[JUnitRunner])
class ActionJacksonTest extends FunSuite with BeforeAndAfterEach with MockitoSugar{

  var gigTestInstance: ActionJackson[Gig] = _
  var gigLoader: GigLoader = _
  var skillTestInstance: ActionJackson[Skill] = _
  var skillLoader: SkillLoader = _
  var generator: Generator = _

  override def beforeEach(){
    gigLoader = mock[GigLoader]
    gigTestInstance = new GigWriterService(gigLoader)

    skillLoader = mock[SkillLoader]
    skillTestInstance = new SkillWriterService(skillLoader)

    testSkillId = str()
    testGigId = str()
    generator = new Generator()

  }

  def str() = new Generator().randomStr

  var testSkillId: String = _
  var testGigId: String = _

  test("json for gigId"){

    val skill0 = makeMeASkill()
    val skill1 = makeMeASkill()
    val gig = makeMeAGig() mergeWith List(skill0, skill1)

    when(gigLoader.loadById(Matchers.eq(testGigId))).thenReturn(gig)
    val actual = gigTestInstance.jsonForEntityId(testGigId, gigLoader)

    assert( actual != null, "no null json, please")
    assert( actual.length > 2, "no mts")

  }

  test("json for skillId"){

    val gig0 = makeMeAGig()
    val gig1 = makeMeAGig()
    val skill = makeMeASkill() mergeWith List(gig0, gig1)

    when(skillLoader.loadById(Matchers.eq(testSkillId))).thenReturn(skill)
    val actual = skillTestInstance.jsonForEntityId(testSkillId, skillLoader)

    assert( actual != null, "no null json here either")
    assert( actual.length() > 2, "mt sucks")

  }

  test("json for all gigs"){

    val skill0 = makeMeASkill()
    val skill1 = makeMeASkill()
    val skill2 = makeMeASkill()

    val gig0 = makeMeAGig() mergeWith List(skill0, skill1)
    val gig1 = makeMeAGig() mergeWith List(skill1, skill2)

    when(gigLoader.loadAll()).thenReturn(List(gig0,gig1))
    val actual = gigTestInstance.jsonForAllAndToAllAGoodNight(gigLoader)

    assert( actual !=  null, "null if for Java")
    assert( actual.length > 2, "or empty either")
  }

  test("json for all skills"){

    val gig0 = makeMeAGig()
    val gig1 = makeMeAGig()
    val gig2 = makeMeAGig()

    val skill0 = makeMeASkill() mergeWith List(gig0, gig1)
    val skill1 = makeMeASkill() mergeWith List(gig1, gig2)

    when(skillLoader.loadAll()).thenReturn(List(skill0,skill1))
    val actual = skillTestInstance.jsonForAllAndToAllAGoodNight(skillLoader)

    assert( actual != null, "no null json dadblameit")
    assert( actual.length > 2, "or empty either")
  }

  def makeMeAGig(): Gig = {

    val date0 = new Generator().dateInThePast()
    val date1 = new Generator().dateInThePast()
    val start = if ( date0 isAfter date1 ) date1 else date0
    val end = if ( date0 isAfter date1 ) date0 else date1

    new Gig(
      str(),
      str(),
      str(),
      str(),
      str(),
      str(),
      str(),
      str(),
      start,
      end,
      math.abs(Random.nextLong()),
      List()
    )
  }

  def makeMeASkill(): Skill = {
    new Skill(
      str(),
      str(),
      List(str(),str(),str()),
      Random.nextInt(23)+1,
      List()
    )
  }

}
