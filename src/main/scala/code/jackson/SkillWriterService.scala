package code.jackson

import code.mongo.loader.SkillLoader
import code.model.Skill

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 11/1/12
 * Time: 1:14 AM
 * Provides an implementation of `JsonServiceWriter` for `Skills`
 */
class SkillWriterService(val skillLoader: SkillLoader) extends JsonWriterServiceSkel[Skill]{

  val loader = skillLoader

}
