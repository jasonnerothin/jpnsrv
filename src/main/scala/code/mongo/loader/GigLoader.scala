package code.mongo.loader

import code.model.Gig
import code.mongo.reader.{SkillReader, GigReader}

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/30/12
 * Time: 11:58 PM
 * Provides a `Loader` for `Gigs`
 */
class GigLoader(gigReader: GigReader, skillReader: SkillReader) extends Loader[Gig]{

  /**
   * Loads a `Gig`and merges companion `Skills` into a detail version
   * @param gigId application assigned gigId
   */
  def loadById(gigId: String) = {
    null
  }

  /**
   * Loads `Gigs`and merges companion `Skills` into detail records
   * @return list of detailed `Gig` records
   */
  def loadAll() = {
    null
  }

}
