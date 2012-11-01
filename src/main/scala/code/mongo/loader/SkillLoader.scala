package code.mongo.loader

import code.model.{Gig, SkillKeys, Skill}
import code.mongo.conversion.{GigMunger, SkillMunger}
import code.mongo.reader.{GigReader, SkillReader, SearchParam}
import com.mongodb.casbah.commons.MongoDBObject

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 12:00 AM
 * Provides a `Loader` for `Gigs`
 */
// TODO this class is nearly identical to `SkillLoader` and both should be refactored into a single class
// TODO with the appropriate support of generics and variances
class SkillLoader(skillReader: SkillReader,
                  skillMunger: SkillMunger,
                  gigReader: GigReader,
                  gigMunger: GigMunger) extends Loader[Skill] with SearchParam{

  /**
   * Loads a `Skill`and merges companion `Gigs` into a detail version
   * @param skillId application assigned skillId
   */
  def loadById(skillId: String): Skill = {
    val dbo = new MongoDBObject(skillReader.findOne(searchParam("skillId", skillId)))
    val skill = skillMunger.populate(dbo)
    val skillKeys = skillMunger.extractOids(dbo)
    pullInGigs(skill, skillKeys)
  }

  /**
   * Loads `Skills`and merges companion `Gigs` into detail records
   * @return list of detailed `Skill` records
   */
  def loadAll(): List[Skill] = {
    var skillList = List[Skill]()
    var cursor = gigReader.find()
    val skillDbos = (for ( dbo <- cursor ) yield new MongoDBObject(dbo))
    for ( dbo <- skillDbos ){
      val skill = skillMunger.populate(dbo)
      val skillKeys = skillMunger.extractOids(dbo)
      val newList = pullInGigs(skill, skillKeys)::skillList
      skillList = newList
    }
    skillList
  }

  protected [loader] def pullInGigs(skill: Skill, skillKeys: SkillKeys) : Skill = {
    val gigOids = skillKeys.gigOids
    var list = List[Gig]()
    for (gigOid <- gigOids){
      val dbo = new MongoDBObject(gigReader.findOne(searchParam("_id", gigOid)))
      val newList = gigMunger.populate(dbo)::list
      list = newList
    }
    skill mergeWith list
  }

}
