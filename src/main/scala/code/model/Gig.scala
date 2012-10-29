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
class Gig(
           @JsonProperty val gigId: String
           , @JsonProperty val gigName: String
           , @JsonProperty val title: String
           , @JsonProperty val employer: String
           , @JsonProperty val blurb: String
           , @JsonProperty val address: String
           , @JsonProperty val methodology: String
           , @JsonProperty val result: String
           , @JsonProperty val startDate: DateTime
           , @JsonProperty val endDate: DateTime
           , @JsonProperty val skills: List[Skill]
         )