package code.model

import org.codehaus.jackson.annotate.JsonProperty

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/26/12
 * Time: 9:24 PM
 * Provides a superficial description of some of my various skillz.
 */
class Skill( @JsonProperty val skillId: String
             , @JsonProperty val skillName: String
             , @JsonProperty val categories: Array[String]
             , @JsonProperty val monthsOfExperience: Int
             , @JsonProperty val gigs: List[Gig] ) extends Entity{}