package code.model

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfterEach, FunSuite}
import org.scalatest.mock.MockitoSugar
import code.testutil.Generator
import org.joda.time.{Duration, DateTime}
import org.mockito.Mockito
import org.scalatest.matchers.ShouldMatchers

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 9:40 AM
 */
@RunWith(classOf[JUnitRunner])
class SkillTest extends FunSuite with MockitoSugar with BeforeAndAfterEach with ShouldMatchers {

  val daysPerYear = 365.25
  val monthsPerYear = 12
  val yearOfMillis: Long = (1000.toDouble * 60 * 60 * 24 * daysPerYear).toLong

  val testInstance = new Skill(str(), str(), List(str()), 0, List.empty)
  var gig0: Gig = _
  var gig1: Gig = _

  override def beforeEach() {
    gig0 = mock[Gig]
    gig1 = mock[Gig]
  }

  test("monthsBetween helper works") {
    val now = new DateTime()
    val before: DateTime = now.minusYears(1)

    val actual = monthsBetween(now, before) // rounds up!
    assert(actual === 13, "lookin for thirteen")
  }

  def millisBetween(after: DateTime, before: DateTime): Long = {
    val later = if (after == null) new DateTime() else after
    new Duration(before, later).getMillis
  }

  def yearsBetween(after: DateTime, before: DateTime): Double = {
    val millis = millisBetween(after, before)
    millis.toDouble / yearOfMillis
  }

  def monthsBetween(after: DateTime, before: DateTime): Int = {
    val years: Double = yearsBetween(after, before)
    val months: Double = years * 12
    math.ceil(months).toInt
  }

  test("months experience works for two dates") {
    val twoYearsAgo = new DateTime().plusYears(-2)
    val aYearAgo = new DateTime().plusYears(-1)
    val aMonthAgo = new DateTime().plusMonths(-1)
    Mockito.when(gig0.startDate).thenReturn(twoYearsAgo)
    Mockito.when(gig0.endDate).thenReturn(aYearAgo)
    Mockito.when(gig1.startDate).thenReturn(aYearAgo)
    Mockito.when(gig1.endDate).thenReturn(aMonthAgo)

    val months = testInstance.calcExperience(List(gig0, gig1))

    months - 23 should be(0 plusOrMinus 1)

  }

  test("months experience works for two durations") {

    Mockito.when(gig0.durationInMillis).thenReturn(yearOfMillis)
    Mockito.when(gig1.durationInMillis).thenReturn(yearOfMillis * 2)

    val months = testInstance.calcExperience(List(gig1, gig0))

    months - 36 should be(0 plusOrMinus 1)

  }

  test("startDateOnly works for duration calculation") {
    Mockito.when(gig0.startDate).thenReturn(new DateTime().minusMonths(2))
    Mockito.when(gig0.endDate).thenReturn(null)

    val months = testInstance.calcExperience(List(gig0))

    // 3 at the most (rounding)...
    months - 2 should be(0 plusOrMinus 1)
  }

  test("merge with works") {
    val skill = new Skill(str(), str(), List(str(), str()), 22, List())
    val gigs = List(gigMe(),gigMe(),gigMe())

    val actual:Skill = skill mergeWith gigs

    assert( actual.skillId === skill.skillId, "skillId mismatch")
    assert( actual.skillName === skill.skillName, "skillName mismatch")
    assert( actual.categories === skill.categories, "categories mismatch")
    assert( actual.monthsOfExperience === skill.calcExperience(gigs), "expect experience to be recalculated")

    // lists work like stacks, so we need to reverse :(
    assert( actual.gigs.reverse === gigs, "expected gigs to make it across")

  }

  def gigMe(): Gig = {
    new Gig(
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

  def dateMe(): DateTime = {
    new DateTime(new Generator().someAs()(1).l)
  }

  def str(): String = new Generator().randomStr

}
