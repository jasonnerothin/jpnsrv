package code.jackson

import code.model.Gig
import code.mongo.loader.GigLoader

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 10:08 PM
 * Provides an implementation of `JsonServiceWriter` for `Gigs`
 */
class GigWriterService(val gigLoader: GigLoader) extends JsonWriterServiceSkel[Gig]{

  val loader = gigLoader

}