package code.mongo.conversion

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.mock.MockitoSugar
import com.mongodb.casbah.commons.{MongoDBList, MongoDBObject}
import org.joda.time.DateTime
import code.model.Gig
import org.scalatest.junit.JUnitRunner

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 1:04 AM
 */
@RunWith(classOf[JUnitRunner])
class GigMungerTest extends FunSuite with MockitoSugar with BeforeAndAfterEach {

  val testInstance: GigMunger = new GigMunger

  val gigId = "jasonnerothin.com"
  val blurb = "late nights are fun"
  val employer = "Superior Bioming L.L.C."
  val title = "King of Town"
  val cityState ="Gary IN"
  val methodology = "slave labor"
  val result = "victory"
  val gigName = "yada yada"
  val startDate:DateTime = new DateTime()
  val endDate:DateTime= startDate.plusYears(2)
  val oid: String = "110010101001010"
  val skillOid0 = "2020120301002300123"
  val skillOid1 = "2020120301002300124"
  val skillOid2 = "2020120301002300125"

  var dbObject: MongoDBObject = _

  override def beforeEach(){

    val builder = MongoDBObject.newBuilder

    val oidBuilder = MongoDBObject.newBuilder
    oidBuilder += "$oid" -> oid
    builder += "_id" -> oidBuilder.result

    builder += "gigId" -> gigId
    builder += "blurb" -> blurb
    builder += "employer" -> employer
    builder += "title" -> title
    builder += "cityState" -> cityState
    builder += "methodology" -> methodology
    builder += "gigName" -> gigName
    builder += "startDate" -> startDate
    builder += "endDate" -> endDate
    builder += "result" -> result

    val skillListBuilder = MongoDBList.newBuilder
    skillListBuilder += "2020120301002300123"
    skillListBuilder += "2020120301002300124"
    skillListBuilder += "2020120301002300125"
    builder += "skills" -> skillListBuilder.result()

    dbObject = new MongoDBObject(builder.result())
//    println (dbObject.toString())

  }

  test ("populate gets all fields correctly"){

    val actual: Gig = testInstance.populate(dbObject)

    assert(actual.gigId === gigId, "gigId is wrong")
    assert(actual.blurb === blurb, "incorrect blurb")
    assert(actual.employer === employer, "employer is wrong")
    assert(actual.title === title, "title is wrong")
    assert(actual.cityState === cityState, "incorrect cityState")
    assert(actual.methodology === methodology, "methodology is all wrong (always is)")
    assert(actual.gigName === gigName, "gigName is wrong")
    assert(actual.startDate === new DateTime(startDate), "startDate is wrong")
    assert(actual.endDate === new DateTime(endDate), "endDate is wrong" )
    assert(actual.result === result, "result is wrong")

  }

  test("populate gets null endDate okay"){

    dbObject.update("endDate", null)

    val actual: Gig = testInstance.populate(dbObject)

    assert(actual.endDate === null, "null means null")
  }

  test("populate calculates durationInMillis correctly when endDate is set"){

    val millis = long2Long(endDate.getMillis - startDate.getMillis)
    val actual = testInstance.populate(dbObject).durationInMillis

    assert(millis === actual, "duration should be the same, but was not.")
  }

  test("populate calculates durationInMillis correctly when endDate is not set"){

    val actual = testInstance.populate(dbObject).durationInMillis
    val now = new DateTime
    val millis = long2Long(now.getMillis - startDate.getMillis)
    val diff:Long = millis - actual

    assert( diff < 1000, "now shouldn't be more than a second after duration was calculated")
  }

  test("populate creates empty skill set"){

    val actual: Gig = testInstance.populate(dbObject)

    assert(actual.skills != null, "null's hardly ever any good")
    assert(actual.skills.isEmpty, "but it should be empty")

  }

  // todo until we learn otherwise from Jackson, for example...
  test("unset values result in good clean nulls"){

    dbObject.remove("employer")
    dbObject.remove("gigName")

    val actual: Gig = testInstance.populate(dbObject)

    assert(actual.employer === null, "employer should not have been set")
    assert(actual.gigName === null, "gigName as well")

    assert(actual.gigId != null, "a little negative control never hurts")

  }

  test("gigId is a requirement of Gig results"){
    dbObject.update("gigId", null)

    var caught = intercept[IllegalStateException]{
      testInstance.populate(dbObject)
    }

    val msg = caught.getMessage
    assert( msg.contains("Illegal db result with null gigId"))
  }

}
