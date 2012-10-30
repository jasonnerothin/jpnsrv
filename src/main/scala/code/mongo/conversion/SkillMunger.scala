package code.mongo.conversion

import code.model.{Gig, SkillKeys, Skill}
import com.mongodb.casbah.commons.MongoDBObject

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 9:31 AM
 * Provides an implementation of `DBObjectMunger` for
 * the `Skill` entity type
 */
class SkillMunger extends DBObjectMunger[Skill]{

  /**
   * Takes properties of d and sticks them into a
   * new Skill instance, which is returned. Note: returned `Skill`
   * will not be fully-initialized, i.e. will not have any
   * `Gig` values in its `List` of `gigs`
   *
   * Returns 0 for experience and empty list of gigs
   *
   * Requires the dbObject to have a non-null skillId
   *
   * @param d data source
   * @return a skill value
   */
  def populate(d: MongoDBObject): Skill = {

    val skill = new Skill(
        stringOrNull("skillId", d),
        stringOrNull("skillName", d),
        asList("categories",d),
        0,
        List.empty[Gig]
    )

    if ( skill.skillId == null ) throw new IllegalStateException("Skill record with no skillId: " + d.toString())
    skill
  }

  /**
   * Grabs the `Skill`'s "_id" out of the mongo dbobject
   * and any oids for related `Gigs`s as well
   * @param d datbase object
   * @return all of the oids, in one little package
   */
  def extractOids(d: MongoDBObject): SkillKeys = null

}
