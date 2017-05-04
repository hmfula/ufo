package one.network;
import kafka.serializer.StringDecoder
import org.apache.log4j.Logger
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.dstream.{DStream, InputDStream}
import org.apache.spark.streaming.kafka.{KafkaUtils, OffsetRange}
/**
 * Created by harry on 27.4.2017.
 */
 object  KafkaStreaming {

  val localLogger = Logger.getLogger("WeatherDataStream")
  case class PersonData(
                             iuser_id: String,
                             fname: String,
                             lname: String)
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf(true).set("spark.cassandra.connection.host", "localhost").setAppName("Kafka Tester")
    sparkConf.setIfMissing("spark.master", "local[5]")

    val ssc = new StreamingContext(sparkConf, Seconds(2))
    val kafkaTopicRaw = "amazingTopic"
    val kafkaBroker = "127.0.01:9092"

    val cassandraKeyspace = "mykeyspace"
    val cassandraTableRaw = "users"

    println(s"Using Table: $cassandraTableRaw")

//    val topics: Set[String] = kafkaTopicRaw.split(",").map(_.trim).toSet
//    val kafkaParams = Map[String, String]("metadata.broker.list" -> kafkaBroker)

//    localLogger.info(s"connecting to brokers: $kafkaBroker")
//    localLogger.info(s"kafkaParams: $kafkaParams")
//    localLogger.info(s"topics: $topics")


    //val rawWeatherStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, kafkaParams, topics)
//    val parsedWeatherStream: DStream[PersonData] = ingestStream(rawWeatherStream)




    // hostname:port for Kafka brokers, not Zookeeper
    val kafkaParams = Map("metadata.broker.list" -> "localhost:9092")

    val topics = Set("amazingTopic")
    val stream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc, kafkaParams, topics)
    val lines = stream.map(_._2)
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1L)).reduceByKey(_ + _)

    System.out.println("Hello world", wordCounts.print())

    //Kick off
    ssc.start()

    ssc.awaitTermination()

    ssc.stop()
  }

//  def ingestStream(rawWeatherStream: InputDStream[(String, String)]):  DStream[PersonData] = {
//    val parsedWeatherStream = rawWeatherStream.map(_._2.split(","))
//      .map(PersonData(_))
//    parsedWeatherStream
//
//  }

}
