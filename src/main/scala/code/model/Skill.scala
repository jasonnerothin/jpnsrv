package code.model

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/26/12
 * Time: 9:24 PM
 * Provides a superficial description of some of my various skillz.
 */
class Skill(
             val skillId: String
             , val skillName: String
             , val categories: Array[String]
             , val experience: Int
             , val gigs: Array[Gig]
           )