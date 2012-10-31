package code.model

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 9:56 PM
 * Provides a marker trait representing the DDD concept
 * of an "Entity" in our domain model.
 */
trait Entity{

  /**
   * returns the application-assigned "entityId"
   * for this entity: skillId for `Skill`, gigId for `Gigs`, etc
   */
  def entityId: String

  /**
   * Merges entities into a copy of this entity
   * @param entities related entities: e.g. skills for Gig or gigs for Skill
   * @return a new instance with entities set
   */
  def mergeWith(entities: List[Entity]) : Entity

}
