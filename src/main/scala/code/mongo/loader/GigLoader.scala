package code.mongo.loader

import code.model.{Skill, GigKeys, Gig}
import code.mongo.reader.{SearchParam, SkillReader, GigReader}
import code.mongo.conversion.{SkillMunger, GigMunger}
import com.mongodb.casbah.commons.MongoDBObject

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 11:58 PM
 * Provides a `Loader` for `Gigs`
 */
// TODO this class is nearly identical to `SkillLoader` and both should be refactored into a single class
// TODO with the appropriate support of generics and variances
class GigLoader(gigReader: GigReader,
                gigMunger: GigMunger,
                skillReader: SkillReader,
                skillMunger: SkillMunger) extends Loader[Gig] with SearchParam{

  /**
   * Loads a `Gig`and merges companion `Skills` into a detail version
   * @param gigId application assigned gigId
   */
  def loadById(gigId: String): Gig = {
    val dbo = new MongoDBObject(gigReader.findOne(searchParam("gigId", gigId)))
    val gig = gigMunger.populate(dbo)
    val gigKeys = gigMunger.extractOids(dbo)
    pullInSkills(gig, gigKeys)
  }

  /**
   * Loads `Gigs`and merges companion `Skills` into detail records
   * @return list of detailed `Gig` records
   */
  def loadAll(): List[Gig] = {
    var gigList = List[Gig]()
    var cursor = gigReader.find()
    val gigDbos = (for (dbo <- cursor) yield new MongoDBObject(dbo)).toList
    for {dbo <- gigDbos} {
      val gig = gigMunger.populate(dbo)
      val gigKeys = gigMunger.extractOids(dbo)
      val newList = pullInSkills(gig, gigKeys)::gigList
      gigList = newList
    }
    gigList
  }

  /**
   * Retrieve the skills associated to this `Gig` and return a `Gig` that
   * has those skills in its `skills` property
   * @param gig for which to retrieve `Skills`
   * @param gigKeys skill oid container
   * @return a merged gig
   */
  protected [loader] def pullInSkills(gig: Gig, gigKeys: GigKeys): Gig = {
    val skillOids = gigKeys.skillOids
    var list = List[Skill]()
    for {skillOid <- skillOids} {
      val dbo = new MongoDBObject(skillReader.findOne(searchParam("_id", skillOid)))
      val newList = skillMunger.populate(dbo) :: list
      list = newList
    }
    gig mergeWith list
  }

}
