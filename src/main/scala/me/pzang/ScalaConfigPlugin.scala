package com.example

import sbt._

/**
 * This plugin helps you which operating systems are awesome
 */
object ScalaConfigPlugin extends AutoPlugin {

  /**
   * Defines all settings/tasks that get automatically imported,
   * when the plugin is enabled
   */
  object autoImport {
    val configFile = settingKey[String] ("config file path")
    val packageName = settingKey[String] ("package name in which this config object sites")
    val topObjectName = settingKey[String] ("the top most object name as the entry point")
    val generateConfigObject = taskKey[String]("the task generates config object in ")
  }

  import autoImport._

  /**
   * Provide default settings
   */
  override def projectSettings: Seq[Setting[_]] = Seq(
    defaultConfigFileSetting
  )

  def defaultConfigFileSetting: Setting[_] = configFile := {
//    streams.value.log.info("default configFile is application.conf")
    s"application.conf"
  }

}