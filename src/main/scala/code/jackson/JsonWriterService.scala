package code.jackson

import code.model.{Gig, Entity}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 * Time: 12:52 AM
 */
trait JsonWriterService[E <: Entity] extends ActionJackson[E] {

  def writeJsonEntityId(out: StringBuilder, entityId: String)

  def writeJsonForAll(out: StringBuilder)

}
