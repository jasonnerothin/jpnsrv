package code.model

import org.joda.time.DateTime
import org.codehaus.jackson.annotate.JsonProperty

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/26/12
 * Time: 9:19 PM
 * Provides a description of one of the many gigs I've worked over the years.
 */
class Gig (
           @JsonProperty val gigId: String
           , @JsonProperty val gigName: String
           , @JsonProperty val title: String
           , @JsonProperty val employer: String
           , @JsonProperty val blurb: String
           , @JsonProperty val cityState: String
           , @JsonProperty val methodology: String
           , @JsonProperty val result: String
           , @JsonProperty val startDate: DateTime
           , @JsonProperty val endDate: DateTime
           , @JsonProperty val durationInMillis: Long
           , @JsonProperty val skills: List[Skill]
         ) extends Entity{

  /**
   * returns the application-assigned "entityId"
   * for this entity: skillId for `Skill`, gigId for `Gigs`, etc
   */
  override def entityId = gigId

  /**
   * Sets skills on a new instance of Gig. All other properties copied over from this
   * @param skills to set
   * @return a new instance with entities set
   */
  override def mergeWith(skills: List[Entity]) : Gig ={
    new Gig(
      this.gigId,
      this.gigName,
      this.title,
      this.employer,
      this.blurb,
      this.cityState,
      this.methodology,
      this.result,
      this.startDate,
      this.endDate,
      this.durationInMillis,
      skills.asInstanceOf[List[Skill]] // TODO replace with proper, polymorphic type-safety/variance
    )
  }

}

class GigKeys(val oid: String, val skillOids: List[String]) extends EntityKey
