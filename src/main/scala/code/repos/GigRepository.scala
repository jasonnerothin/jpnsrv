package code.repos

import code.model.Gig

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/26/12
 * Time: 9:16 PM
 * Provides access to gigs that are stored in mongo
 */
trait GigRepository {

  /**
   * @return all of the gigs i've worked
   */
  def findAll(): List[Gig]

}
