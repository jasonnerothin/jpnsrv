package code.mongo.conversion

import org.specs2.runner.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import com.mongodb.casbah.commons.{MongoDBList, MongoDBObject}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 10:06 AM
 */
@RunWith(classOf[JUnitRunner])
class SkillMungerTest extends FunSuite {

  val testInstance: SkillMunger = new SkillMunger
  val skillId: String = "skilllll"
  val skillName: String = "namedy namedy name name name"
  val category0 = "puma"
  val category1 = "lion"
  val category2 = "leopard"

  var dbObject: MongoDBObject = _

  def before() {
    var builder = MongoDBObject.newBuilder
    builder += "skillId" -> skillId
    builder += "skillName" -> skillName
    builder += "othercrap" -> "foobly"

    var listBuilder = MongoDBList.newBuilder
    listBuilder += category0
    listBuilder += category1
    listBuilder += category2
    builder += "categories" -> listBuilder.result

    dbObject = new MongoDBObject(builder.result())
  }

  test("populate populates") {
    before()

    val actual = testInstance.populate(dbObject)

    assert(actual.monthsOfExperience == 0, "cannot be calculated at this stage")
    assert(actual.gigs != null, "gigs should not be null")
    assert(actual.gigs.isEmpty, "gigs should be empty")
    assert(actual.skillId == skillId, "skillId should have been captured")
    assert(actual.skillName == skillName, "skillName should have been captured")

    val cats = actual.categories
    assert( cats == List(category0, category1, category2), "categories should match")

  }

  test("populate makes empty list when no categories"){
    before()

    dbObject.removeField("categories")

    val actual = testInstance.populate(dbObject)

    assert(actual.categories != null, "not null")
    assert(actual.categories.isEmpty, "but empty")

  }

  test("null skillId throws"){
    before()

    dbObject.update("skillId", null)
    val caught = intercept[IllegalStateException]{
      testInstance.populate(dbObject)
    }

    val msg = caught.getMessage
    assert(msg.contains("Skill record with no skillId:"))
  }
}
