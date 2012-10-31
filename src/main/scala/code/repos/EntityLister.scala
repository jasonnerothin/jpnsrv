package code.repos

import code.model.Entity

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 9:54 PM
 * Provides a list of all domain model `Entity`s of a given
 * type
 */
trait EntityLister[+E <: Entity]{

  /**
   * @return all entities - fully populated with related types, including other entities
   */
  def list(): List[E]

  def get(entityId: String) : E

}
