package code.repos

import code.model.{Gig, Skill}
import code.mongo.loader.SkillLoader

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 10:41 PM
 * Provides a repository for the `Skill` domain model `Entity` type.
 */
class SkillRepository(val loader: SkillLoader) extends EntityLister[Skill]{

  /**
   * Merges `Gig` entities into list of all `Skill`s and returns the List.
   * Also calculates the `experience` value
   * @return all `Skill`s
   */
  def list(): List[Skill] = {
    loader.loadAll()
  }

  def get(skillId: String): Skill = {
    loader.loadById(skillId)
  }

}