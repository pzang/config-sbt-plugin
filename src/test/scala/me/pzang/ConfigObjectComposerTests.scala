package me.pzang

import java.util

import com.typesafe.config.{ConfigFactory, ConfigValueType}
import org.scalatest.{FlatSpec, Matchers}

import scala.collection.JavaConversions._

/**
  * Created by pzang on 4/6/16.
  */
class ConfigObjectComposerTests extends FlatSpec with Matchers with ConfigObjectComposer {
  override val packageName: String = "me.pzang.test"
  override val configFile: String = "application.conf"
  override val topObjectName: String = "TestConfig"

  "buildList" should "recognize List type" in {
    val list = new java.util.ArrayList[Object]()
    list.add("asdf")
    list.add("more")
    println(buildList("testList", list))
  }

  "buildNumber" should "build correctly" in {
    assert(false)
  }

}
