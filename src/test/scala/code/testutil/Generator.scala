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

  def someAs(): Seq[A] = {
    someAs(10)
  }

  def someAs(len: Int): Seq[A] = 0 to len map( xml =>
    A(  Random.nextBoolean(),
        Random.nextInt(),
        Random.nextLong(),
        (Random.nextInt(26) + offset(Random.nextBoolean())).toChar
    )
  )

  private def offset(b: Boolean): Int = {
    if ( b ) 65 else 97
  }

  /**
   * Random string, length 10
   */
  def randomStr: String = {
    randomStr(10)
  }

  /**
   * Arbitrarily length random string
   * @param len desired length
   * @return the string
   */
  def randomStr(len: Int): String = {
    val buf = new StringBuilder
    val as = someAs(len)
    for { i <- 0 to len-1} buf.append(as(i).ch)
    buf.toString()
  }

}