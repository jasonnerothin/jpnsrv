package code.jackson

import code.model.Entity
import code.mongo.loader.Loader
import org.codehaus.jackson.map.{SerializationConfig, DeserializationConfig, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 9:47 PM
 */
trait ActionJackson[E <: Entity]{

  /**
   * Converts an entity to json
   * @param entityId application-provided entityId
   * @param entityLoader a loader
   * @return well-formed json
   */
  def jsonForEntityId(entityId: String, entityLoader: Loader[E]): String = {
    val e = entityLoader.loadById(entityId)
    makeMeAMapper().writeValueAsString(e)
  }

  /**
   * Loads up all entities of the given type and writes them to a Json
   * string
   * @param entityLoader loader
   * @return bunch o' json
   */
  def jsonForAllAndToAllAGoodNight(entityLoader: Loader[E]): String = {
    val builder = new StringBuilder("[")
    for(e <- entityLoader.loadAll()){
      builder.append(makeMeAMapper().writeValueAsString(e)).append(",")
    }
    val json = builder.toString()
    val anotherBuilder = new StringBuilder(json.substring(0, json.length-1)) // chomp last comma
    anotherBuilder.append("]")
    anotherBuilder.toString()
  }

  def makeMeAMapper(): ObjectMapper ={
    val mapper = new ObjectMapper
    mapper.registerModule(DefaultScalaModule)
    mapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS, false)
    mapper.configure(SerializationConfig.Feature.USE_ANNOTATIONS, true)
    mapper
  }

}