package code.jackson
import code.mongo.loader.Loader
import code.model.Entity

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 *
 * Time: 1:05 AM
 * Provides a skeletal implementation of `JsonWriterService`
 */
abstract class JsonWriterServiceSkel[E <: Entity] extends JsonWriterService[E] {

  /**
   * Want a reference to the upper-type-bound loader, but that loader needs to
   * be provided by the concrete subclasses
   */
  val loader: Loader[E]

  def writeJsonEntityId(out: StringBuilder, entityId: String){
    out.append(jsonForEntityId(entityId, loader))
  }

  def writeJsonForAll(out: StringBuilder){
    out.append(jsonForAllAndToAllAGoodNight(loader))
  }

}
