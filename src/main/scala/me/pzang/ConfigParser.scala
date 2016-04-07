package me.pzang

import com.typesafe.config.{Config, ConfigFactory, ConfigValue}

import scala.collection.JavaConversions._

/**
  * Created by pzang on 4/5/16.
  */
trait ConfigParser {

  def parseConfigFile(file: String) = {
    val conf: Config = ConfigFactory.load(file)
    val rootMap: Map[String, ConfigValue] = conf.entrySet().map(entry => entry.getKey -> entry.getValue).toMap
    rootMap.map {
      case (key, subConf) => {
        subConf
      }
    }
  }
}
