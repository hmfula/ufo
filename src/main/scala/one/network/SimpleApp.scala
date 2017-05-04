package one.network


import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
/**
  * @author Harrison Mfula
  * @since on 19.3.2017.
  */
object SimpleApp extends App {
  // create Spark context with Spark configuration
  val sc = new SparkContext(new SparkConf().setAppName("Simple Spark App").setMaster("local[*]"))
  val rdd = sc.parallelize(Seq(1,2,3,4,5))
//  val rdd = sc.textFile("src/main/resources/test-data.txt")
   //val res = rdd.flatMap( w => w.split(" ")).map(w=>(w,1)).reduceByKey(_+_)
// val res = rdd.flatMap( w => w.split(" ")).map(w=>(w,1))
 // res.foreach(println)
  //res.saveAsTextFile("pekka_food.txt")
  //println("The most popular food for Pekka "+ res.map(_.swap).max()._2)
  println("Harrison Running the FAT JAR "+ rdd.count())

}