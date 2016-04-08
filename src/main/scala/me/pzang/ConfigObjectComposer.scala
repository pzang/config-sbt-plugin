package me.pzang

import com.typesafe.config.{Config, ConfigObject, ConfigValue, ConfigValueType}

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import utils.CommonUtils._

/**
  * Created by pzang on 4/5/16.
  */
trait ConfigObjectComposer {

  val packageName: String
  val topObjectName: String
  val configFile: String

  val RB = System.lineSeparator()

  def build(config: Config): String = {
    s"""package ${packageName} $RB
        |${buildConfigFile} $RB
        | ${buildConfigSet(topObjectName, config.root().keySet(), config.root())} $RB
       """.stripMargin
  }

  def buildConfigFile: Unit = {
    s""" lazy val config: com.typesafe.config.Config = com.typesafe.config.ConfigFactory.load(\" ${configFile} \")"""
  }

  def buildConfigSet(objName: String, keyset: java.util.Set[String], confObj: ConfigObject): String = {
    val sb = new StringBuilder(s"""object ${objName} {  $RB""")
    sb.append(keyset
      .map(k => buildFromObject(k, confObj.get(k)))
      .mkString(System.lineSeparator())
    )
    sb.append(s"""$RB}""")
    sb.toString()
  }

  def buildFromObject(k: String, confVal: ConfigValue): String = {
    confVal.valueType match {
      case ConfigValueType.NULL =>
        buildNULL(k)
      case ConfigValueType.BOOLEAN =>
        buildFromBoolean(k, confVal.unwrapped().asInstanceOf[Boolean])
      case ConfigValueType.NUMBER =>
        buildNumber(k, confVal.unwrapped().asInstanceOf[Number])
      case ConfigValueType.STRING =>
        buildString(k, confVal.unwrapped().asInstanceOf[String])
      case ConfigValueType.LIST =>
        buildList(k, confVal.unwrapped().asInstanceOf[java.util.List[Object]])
      case ConfigValueType.OBJECT => {
        val confObj = confVal.unwrapped().asInstanceOf[ConfigObject]
        buildConfigSet(k,confObj.keySet(), confObj)
      }

    }
  }

  def buildFromBoolean(k: String, v: Boolean): String = {
    s"def `${k}` = "
  }

  def buildNULL(k: String): String = {
    s"def `${k}` = null "
  }

  def buildNumber(k: String, v: Number): String = {
    v.doubleValue() % 1 == 0 match {
      // use double to ensure precision
      case true =>
        s"def `${k} : Double = ${v.doubleValue()}"
      case false =>
        // use Long to avoid integer overflow
        s"def `${k} : Long = ${v.longValue()}L"
    }
  }

  def buildString(k: String, v: String): String = {
    s"""def `${k} : String = \"${v}\""""
  }


  def buildList(k: String, v: java.util.List[Object]): String = {
    val list = v.asScala.toList
    list match {
      case _ if isNullList(list) == true =>
        s"""def `${k}` : scala.List[Object] = config.getAnyRefList( \"${k}\") """
      case _ if isLongList(list) == true =>
        s"""def `${k}` : scala.List[Long] = config.getLongList( \"${k}\") """
      case _ if isDoubleList(list) == true =>
        s"""def `${k}` : scala.List[Double] = config.getDoubleList( \"${k}\") """
      case _ if isStringList(list) == true =>
        s"""def `${k}` : scala.List[String] = config.getStringList( \"${k}\") """
      case _ if isConfigList(list) == true =>
        s"""def `${k}` : scala.List[com.typesafe.config.ConfigList] = config.getConfigList( \"${k}\") """
      case _ =>
        s"""def `${k}` : scala.List[Object] = config.getObjectList( \"${k}\") """
    }
  }


}
