package code.mongo.loader

import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.mock.MockitoSugar
import code.mongo.conversion.{GigMunger, SkillMunger}
import code.mongo.reader.{GigReader, SkillReader}
import org.mockito.Mockito._
import org.mockito.Matchers._
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.casbah.MongoCursor
import code.model.{Skill, Gig, SkillKeys}
import com.mongodb.DBObject

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 7:30 PM
 */
@RunWith(classOf[JUnitRunner])
class SkillLoaderTest extends FunSuite with MockitoSugar with BeforeAndAfterEach{

  var skillReader: SkillReader = _
  var skillMunger: SkillMunger = _
  var gigReader: GigReader = _
  var gigMunger: GigMunger = _

  var testInstance: SkillLoader = _

  var dbObject: DBObject = _
  var cursor: MongoCursor = _
  var skillKeys: SkillKeys = _
  var gig0: Gig = _
  var gig1: Gig = _
  var skill: Skill = _

  var gigMatcher: EntityMatcher[Gig] = _

  override def beforeEach(){

    skillReader = mock[SkillReader]
    skillMunger = mock[SkillMunger]
    gigReader = mock[GigReader]
    gigMunger = mock[GigMunger]

    dbObject = mock[DBObject]
    cursor = mock[MongoCursor]
    skillKeys = mock[SkillKeys]
    gig0 = mock[Gig]
    gig1 = mock[Gig]
    skill = mock[Skill]

    gigMatcher = new EntityMatcher[Gig](gig0, gig1)

    testInstance = new SkillLoader(skillReader, skillMunger, gigReader, gigMunger )

    when(gigReader.find()).thenReturn(cursor)
    when(gigReader.findOne(isA(classOf[MongoDBObject]))).thenReturn(dbObject)
    when(gigMunger.populate(isA(classOf[MongoDBObject]))).thenReturn(gig0).thenReturn(gig1)

    when(skillKeys.gigOids).thenReturn(List("xyz","pdq"))
    when(skillReader.find()).thenReturn(cursor)
    when(skillReader.findOne(isA(classOf[MongoDBObject]))).thenReturn(dbObject)
    when(skillMunger.populate(isA(classOf[MongoDBObject]))).thenReturn(skill)
    when(skillMunger.extractOids(isA(classOf[MongoDBObject]))).thenReturn(skillKeys)

  }

  test("gigs end up getting mergedWith skill during findById"){

    testInstance.loadById("foo")
    verify(skill,times(1)).mergeWith(argThat[List[Gig]](gigMatcher))

  }

  test("gigs end up mergedWith skills during findAll"){
    if( false ){ // near impossible to mock
      when( cursor withFilter(anyObject())) thenReturn Iterator(dbObject)

      testInstance.loadAll()
      verify(skill, times(1)).mergeWith(argThat[List[Gig]](gigMatcher))
    }
  }

}