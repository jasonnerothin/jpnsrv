package code.mongo.loader

import code.model.{Skill, Gig}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 12:00 AM
 * Provides a `Loader` for `Gigs`
 */
class SkillLoader extends Loader[Skill]{

  /**
   * Loads a `Skill`and merges companion `Gigs` into a detail version
   * @param skillId application assigned skillId
   */
  def loadById(skillId: String) = null

  /**
   * Loads `Skills`and merges companion `Gigs` into detail records
   * @return list of detailed `Skill` records
   */
  def loadAll() = null

}
