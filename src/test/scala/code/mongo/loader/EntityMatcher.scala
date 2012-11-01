package code.mongo.loader

import org.mockito.ArgumentMatcher
import code.model.{Entity, Skill}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 7:43 PM
 * Provides...
 */
class EntityMatcher[E <: Entity](val e0: E, val e1: E) extends ArgumentMatcher[List[E]] {

  def matches(argument: Any): Boolean = {
    if (argument == null) false
    else {
      var list = argument.asInstanceOf[List[E]] // not only is it okay, but it's test code
      var matchCnt = 0
      for (item <- list) {
        if (e0 == item) matchCnt = matchCnt + 1
        if (e1 == item) matchCnt = matchCnt + 1
      }
      matchCnt == 2
    }
  }

}
