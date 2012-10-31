package code.mongo.loader

import code.model.{Skill, GigKeys, Gig}
import code.mongo.reader.{SkillReader, GigReader}
import code.mongo.conversion.{SkillMunger, GigMunger}
import com.mongodb.casbah.commons.MongoDBObject
import com.mongodb.DBObject

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 11:58 PM
 * Provides a `Loader` for `Gigs`
 */
class GigLoader(gigReader: GigReader,
                gigMunger: GigMunger,
                skillReader: SkillReader,
                skillMunger: SkillMunger) extends Loader[Gig] {

  /**
   * Loads a `Gig`and merges companion `Skills` into a detail version
   * @param gigId application assigned gigId
   */
  def loadById(gigId: String): Gig = {
    val dbo = new MongoDBObject(gigReader.findOne(searchParam("gigId", gigId)))
    val gig = gigMunger.populate(dbo)
    val gigKeys = gigMunger.extractOids(dbo)
    merge(gig, gigKeys)
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
      val gig: Gig = gigMunger.populate(dbo)
      val gigKeys: GigKeys = gigMunger.extractOids(dbo)
      val newList = merge(gig, gigKeys)::gigList
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
  protected [loader] def merge(gig: Gig, gigKeys: GigKeys): Gig = {
    val skillOids = gigKeys.skillOids
    var list = List[Skill]()
    for {skillOid <- skillOids} {
      val dbo = new MongoDBObject(skillReader.findOne(searchParam("_id", skillOid)))
      val newList = skillMunger.populate(dbo) :: list
      list = newList
    }
    gig mergeWith list
  }

  private def searchParam(propName: String, propVal: String): MongoDBObject = {
    val builder = MongoDBObject.newBuilder
    builder += propName -> propVal
    new MongoDBObject(builder.result())
  }

}
