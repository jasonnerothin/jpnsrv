package code.repos

import code.model.Gig
import code.mongo.{SkillReader, GigReader}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 10:01 PM
 * Provides a repository for the `Gig` domain model `Entity` type.
 */
class GigRepository(val gigReader: GigReader, val skillReader: SkillReader) extends EntityList[Gig]{

  /**
   * Merges `Skill`s into `Gig`s and returns the list of `Gig`s.
   * @return all merged gigs
   */
  def list() : List[Gig] = {
    null
  }

}
