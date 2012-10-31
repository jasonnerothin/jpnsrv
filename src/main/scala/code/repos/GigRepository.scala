package code.repos

import code.model.{Skill, Gig}
import code.mongo.loader.GigLoader

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 10:01 PM
 * Provides a repository for the `Gig` domain model `Entity` type.
 */
class GigRepository(val loader: GigLoader) extends EntityLister[Gig]{

  /**
   * Merges `Skill`s into `Gig`s and returns the list of `Gig`s.
   * @return all merged gigs
   */
  def list() : List[Gig] = {
    null
  }

  def get(gigId: String) : Gig = {
    null
  }
}
