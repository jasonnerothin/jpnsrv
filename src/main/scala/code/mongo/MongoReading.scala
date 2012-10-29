package code.mongo



/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/28/12
 * Time: 10:51 PM
 */
trait MongoReading[T] {

  def findOne(params: AnyRef) : T

  def find(params: AnyRef) : List[T]

}
