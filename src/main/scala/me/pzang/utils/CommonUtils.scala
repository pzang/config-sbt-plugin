package me.pzang.utils

import com.typesafe.config.{Config, ConfigList, ConfigObject}

import scala.reflect.runtime.{universe => ru}

/**
  * Created by pzang on 4/6/16.
  */
object CommonUtils {
  def isDouble(num: Number): Boolean = num.doubleValue() % 1 != 0

  def isLong(num: Number): Boolean = !isDouble(num)

  // TODO: change this to use reflection, travers class hierarchy to find least common super class
  def isLongList(list: List[Object]): Boolean =
   !isNullList(list) && list.forall(n => (classOf[Number] isAssignableFrom n.getClass) && (isLong(n.asInstanceOf[Number])))

  def isDoubleList(list: List[Object]): Boolean =
   !isNullList(list) && list.forall(n => (classOf[Number] isAssignableFrom n.getClass) && (isDouble(n.asInstanceOf[Number])))

  def isNumberList(list: List[Object]): Boolean =
   !isNullList(list) && list.forall(_.getClass equals classOf[Number])

  def isStringList(list: List[Object]): Boolean =
    !isNullList(list) && list.forall(_.getClass equals classOf[String])

  def isConfigList(list: List[Object]): Boolean =
   !isNullList(list) && list.forall(_.getClass equals classOf[Config])

  def isNullList(list: List[Object]): Boolean = list.exists(_ == null)

  def getTypeTag[T: ru.TypeTag](obj: T) = ru.typeTag[T]


}
