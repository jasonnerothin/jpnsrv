package code.repos

import code.model.Skill

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/26/12
 * Time: 9:13 PM
 * Provides access to mongo, for retrieving skills
 */
trait SkillRepository {

  /**
   * @return all of my skills
   */
  def findAll(): List[Skill]

}