package code.mongo.loader

import code.model.Entity

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 1:07 PM
 * Provides an adapter around reading from mongo and conversion of
 * mongo records to `Entity` types
 */
trait Loader [+E <: Entity]{

  /**
   * Loads and merges companion `Entities` into a detail version of this `Entity`
   * @param appId application assigned id - e.g. skillId for skills, gigId for gigs, etc
   */
  def loadById(appId: String) : E

  /**
   * Loads and merges companion `Entities` into detail records
   * @return list of detailed `E` records
   */
  def loadAll() : List[E]

}