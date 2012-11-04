package code.mongo.reader

import code.mongo.DbConnection
import com.mongodb.casbah.MongoCollection
import com.mongodb.casbah.commons.MongoDBObject
import org.joda.time.DateTime
import com.mongodb.casbah.commons.conversions.scala.RegisterJodaTimeConversionHelpers

/**
 * Created by IntelliJ IDEA.
 * User: jason
 * Date: 10/29/12
 * Time: 9:36 AM
 */
class GigReader(val dbConnection: DbConnection) extends MongoReaderSkel {

  private val gigsCollection: MongoCollection = dbConnection.mongoConnection.apply("gigs")

  final override def collection(): MongoCollection = {
    gigsCollection
  }

}

object foo {

  def main(args: Array[String]) {

    RegisterJodaTimeConversionHelpers()

    val oct24_2012 = new DateTime(2012, 10, 24, 18, 0, 0, 0)
    val aug16_2010 = new DateTime(2010, 8, 16, 8, 0, 0, 0)
    val mar17_2008 = new DateTime(2008, 3, 17, 17, 0, 0, 0)
    val nov5_2007 = new DateTime(2007, 11, 5, 6, 0, 0, 0)
    val oct5_2005 = new DateTime(2005, 10, 5, 11, 0, 0, 0)
    val sep29_2004 = new DateTime(2004, 9, 24, 16, 30, 0, 0)

    def createEntry(start: DateTime,
                    end: DateTime,
                    gigId: String,
                    gigName: String,
                    blurb: String,
                    results: String,
                    cityState: String,
                    employer: String,
                    methodology: String,
                    title: String) {

      val reader = new GigReader(DbConnection.INSTANCE)
      val col = reader.collection()

      val set = MongoDBObject.newBuilder
      set += "gigId" -> gigId
      set += "startDate" -> start
      set += "endDate" -> end
      set += "blurb" -> blurb
      set += "results" -> results
      set += "cityState" -> cityState
      set += "gigName" -> gigName
      set += "employer" -> employer
      set += "methodology" -> methodology
      set += "title" -> title

      col.save(set.result())

    }
    /*
    createEntry(
      nov5_2007,
      mar17_2008,
      "gates-biz",
      "Gates Business Solutions Inc.",
      "Developed a custom tag library (JSP) for representing common data types.",
      "Shipped.",
      "Madison WI",
      "Gates Business Solutions Inc.",
      "waterfall",
      "Java Programmer"
    )
    createEntry(
      oct5_2005,
      nov5_2007,
      "ucla",
      "UCLA-DOE Dept of Genomics & Proteomics",
      "Provided application and database administration for the Prolinks and DIP\nscientific databases. Developed a AJAX application that enabled curation of protein-protein\ninteraction data in the MIF format.",
      "Published.",
      "Los Angeles CA",
      "University of California, Los Angeles",
      "Cowboy (no)",
      "Programmer Analyst IV/DBA"
    )
    creteEntry(
      oct24_2012
      , null
      , "jasonnerothin.com"
      , "jasonnerothin.com"
      , "Developed this website, and put it up on EC2."
      , "You're lookin' at it."
      , "Madison WI"
      , "Superior Biomining LLC"
      , "cowboy"
      , "webmaster"
    )
    creteEntry(
      aug16_2010
      , null
      , "pharmacy-onesource"
      , "pharmacy-onesource"
      , "Developed an XML to DRL transpiler that takes legacy SQL-driven business rule metadata and submits it to a massively-parallel rule engine through a Spring web service. Produced functionality for message processing system that is responsible for processing messages from hospital data feeds (HL7)."
      , ">$10M decrease in ongoing operating costs"
      , "Middleton WI"
      , "Pharmacy Onesource Inc."
      , "agile"
      , "Senior Developer & Independent Consultant"
    )
    creteEntry(
      mar17_2008
      , aug16_2010
      , "roche-nimblegen"
      , "roche-nimblegen"
      , "Delivered core functionality for DIVA, a desktop application for analysis of DNA micro-array results. Wrote a binary data engine capable of IO sufficient to power statistical analyses in R for both 32- and 64-bit architectures."
      , "Delivered."
      , "Madison WI"
      , "Roche Nimblegen Inc."
      , "waterfall"
      , "Software Engineer III"
    )
    */

  }

}
