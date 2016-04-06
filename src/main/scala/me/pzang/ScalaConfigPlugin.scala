package com.example

import sbt._
import sbt.Keys._
import java.nio.charset.Charset

import sbt.complete.Parsers

/**
 * This plugin helps you which operating systems are awesome
 */
object ScalaConfigPlugin extends AutoPlugin {

  /**
   * Defines all settings/tasks that get automatically imported,
   * when the plugin is enabled
   */
  object autoImport {
    val hello = inputKey[Unit]("Prints Hello")
    val configFile = settingKey[String] ("config file path")

  }

  import autoImport._

  /**
   * Provide default settings
   */
  override def projectSettings: Seq[Setting[_]] = Seq(
    helloSetting,
    defaultConfigFileSetting
  )

  def helloSetting: Setting[_] = hello := {
    // Sbt provided logger.
    val log = streams.value.log
    log.info("Hello task")
  }

  def defaultConfigFileSetting: Setting[_] = configFile := {
    s"application.conf"
  }

}