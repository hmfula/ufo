package one.network

import org.scalatest.FunSuite

/**
  * Created by harry on 19.3.2017.
  */
class SimpleAppTest extends FunSuite {

    test("Greeting class musk greet user correctly"){
    val simpleApp = new HelloApp
    assert(simpleApp.sayHello("Harry") == "Hello Harry")
  }
}
