package me.pzang

import com.typesafe.config.{Config, ConfigFactory}
import scala.collection.JavaConversions._

/**
  * Created by pzang on 4/5/16.
  */
trait ScalaConfigParser {

  def parseConfigFile(file: String) = {
    val conf: Config = ConfigFactory.load(file)
    val rootMap = conf.entrySet().map(entry => entry.getKey -> entry.getValue).toMap
    rootMap.map { case (key, subConf) => subConf }
  }
}
