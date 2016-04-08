package me.pzang

import com.typesafe.config.{Config, ConfigObject, ConfigValue, ConfigValueType}

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

import utils.CommonUtils._

/**
  * Compose Config representation object source code,
  * based on overrided packageName, topObjectName, and configFile
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
        buildConfigSet(k, confObj.keySet(), confObj)
      }

    }
  }

  def buildFromBoolean(k: String, v: Boolean): String = {
    s"def `${k}` = ${v} $RB"
  }

  def buildNULL(k: String): String = {
    s"def `${k}` = null $RB"
  }

  def buildNumber(k: String, v: Number): String = {
    v.doubleValue() % 1 == 0 match {
      // use double to ensure precision
      case true =>
        s"def `${k}` : Double = ${v.doubleValue()} $RB"
      case false =>
        // use Long to avoid integer overflow
        s"def `${k}` : Long = ${v.longValue()}L  $RB"
    }
  }

  def buildString(k: String, v: String): String = {
    s"""def `${k}` : String = \"${v}\" $RB"""
  }

  val listTemplate: (String, String, String) => String =
    (k, t, m) => s"""def `${k}` : scala.List[${t}] = config.${m}( \"${k}\") $RB"""

  def buildList(k: String, v: java.util.List[Object]): String = {
    val list = v.asScala.toList
    list match {
      case _ if isNullList(list) == true =>
        listTemplate(k,"Object","getAnyRefList")
      case _ if isLongList(list) == true =>
        listTemplate(k,"Long","getLongList")
      case _ if isDoubleList(list) == true =>
        listTemplate(k,"Double","getDoubleList")
      case _ if isStringList(list) == true =>
        listTemplate(k,"String","getStringList")
      case _ if isConfigList(list) == true =>
        listTemplate(k,"com.typesafe.config.ConfigList","getConfigList") //TODO: further expand this to an actual list?
      case _ =>
        listTemplate(k,"com.typesafe.config.ConfigObject","getObjectList") //TODO: further expand this as a list of object?
    }
  }


}
