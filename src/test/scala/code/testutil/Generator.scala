package code.testutil

import util.Random

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/31/12
 * Time: 12:24 AM
 */
class Generator{

  case class A(b : Boolean, i : Int, l : Long, ch: Char)

  def someAs() = 0 to 10 map ( x => A(Random.nextBoolean(), Random.nextInt(), Random.nextLong(), (Random.nextInt(26) + 42).toChar) )

  val randomStr = {
    val buf = new StringBuilder
    val as = someAs()
    for { i <- 0 to 10 } buf.append(as(i))
    buf.toString()
  }

}