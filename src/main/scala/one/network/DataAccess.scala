package one.network
import org.apache.spark.SparkConf
import com.datastax.spark.connector._
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.sql.cassandra.CassandraSQLContext
/**
  * Demonstrates how to connect to the cassandra database using in scala
  * Created by harry on 19.3.2017.
  */
object DataAccess  extends  App{

  val conf = new SparkConf(true).set("spark.cassandra.connection.host", "localhost")
   // setMaster("local").setAppName("mySparkApp")
  //spark.driver.allowMultipleContexts, true

  // Create the SparkContext from SparkConf
  val sc = new SparkContext("local","test",conf)
  val rd = sc.cassandraTable("mykeyspace","users")
  rd.foreach(println)
  println(rd.first().columnValues)
  val cs = new CassandraSQLContext(sc)
  val p = cs.sql("select * from mykeyspace.users where lname like '%cot%'")
  //cs.sql("insert into table mykeyspace.users('user_id', 'fname','lname') values ('1000', 'Harrison', 'Mfula')")
  val collection = sc.parallelize(Seq(("1000","gregory", "wellington")))

  collection.saveToCassandra("mykeyspace","users",SomeColumns("user_id", "fname","lname"))
  p.show()
  // p.foreach(println)
}
