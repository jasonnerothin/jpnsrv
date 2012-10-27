package code.model

import org.joda.time.DateTime

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/26/12
 * Time: 9:19 PM
 * Provides a description of one of the many gigs I've worked over the years.
 */
class Gig(
           val gigId: String
           , val gigName: String
           , val title: String
           , val employer: String
           , val blurb: String
           , val address: String
           , val methodology: String
           , val result: String
           , val startDate: DateTime
           , val endDate: DateTime
           , val skills: Array[Skill]
         )