package code.model

import org.codehaus.jackson.annotate.JsonProperty
import com.mongodb.casbah.commons.MongoDBObject
import org.specs2.time.Duration
import collection.immutable
import org.joda.time.DateTime

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/26/12
 * Time: 9:24 PM
 * Provides a superficial description of some of my various skillz.
 */
class Skill( @JsonProperty val skillId: String
             , @JsonProperty val skillName: String
             , @JsonProperty val categories: List[String]
             , @JsonProperty val monthsOfExperience: Int
             , @JsonProperty val gigs: List[Gig] ) extends Entity{
  /**
   * returns the application-assigned "entityId"
   * for this entity: skillId for `Skill`, gigId for `Gigs`, etc
   */
  override def entityId = skillId

  /**
   * Rip through the gigs, adding up their millis of
   * experience and then convert it into months
   * @param gigs on which this skill has been plied
   * @return calculated value
   */
  protected[model] def calcExperience(gigs: List[Gig]): Int = {
    val dur = new Duration(gigDurationInMillis(gigs))
    val months = int2float(dur.inDays) / 365.25 * 12
    math.ceil(months.toInt).toInt
  }

  private def millisDiff(left: DateTime, right: DateTime): Long ={
    val now = new DateTime()
    val l = if ( left == null ) now else left
    val r = if ( right == null ) now else right
    val after = if ( l.isBefore(r) ) r else l
    val before = if( l.isBefore(r) ) l else r
    after.getMillis - before.getMillis
  }

  private def gigDurationInMillis(gigs: List[Gig]): Long = {
    var millisExperience: Long = 0
    for ( g <- gigs ){
      val millisFromDates = millisDiff(g.startDate, g.endDate)
      val durationInMillis = g.durationInMillis
      val millis = if ( millisFromDates > 0 ) millisFromDates else durationInMillis
      millisExperience = millisExperience + millis
    }
    millisExperience
  }

  /**
   * Merges skills into a copy of this Gig and calculates
   * the months of experience based on the experience values in the gigs
   * @param gigs gigs in which this skill has been applied
   * @return a new instance with gigs set and experience calculated
   */
  override def mergeWith(gigs: List[Entity]): Skill = {
    // TODO these first 4 lines are wrong
    var list = List[Gig]()
    for{ e <- gigs }{
      list = e.asInstanceOf[Gig]::list
    }
    val monthsExperience = calcExperience(list)
    new Skill(
      this.skillId,
      this.skillName,
      this.categories,
      monthsExperience,
      list
    )
  }
}

trait EntityKey{
  def oid: String
}

class SkillKeys(val oid: String, val gigOids: List[String]) extends EntityKey