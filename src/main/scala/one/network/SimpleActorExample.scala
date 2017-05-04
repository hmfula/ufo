package one.network

import akka.actor.{Actor, ActorSystem, Props}



object SimpleActorExample extends App {

  class SimpleActor extends Actor {
    def receive = {
      case s: String =>
        println("String: " + s)
      case i: Int =>
        println("Number: " + i)
    }
  }
    val system = ActorSystem("SimpleMe")
    val actor = system.actorOf(Props[SimpleActor], "Hello")
  println("Before messages ")
    actor ! "Hello Yelo"
  println("After String: " )
    actor ! 1
  println("After Number: " )
  actor ! "a"
  println("After char: " )
    system.awaitTermination()


}