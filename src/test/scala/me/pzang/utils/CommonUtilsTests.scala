package me.pzang.utils

import org.scalatest.{FlatSpec, Matchers}

import scala.util.Random

/**
  * Created by pzang on 4/6/16.
  */
class CommonUtilsTests extends FlatSpec with Matchers {
  val d: java.lang.Double = java.lang.Double.valueOf(1.234)
  val i: java.lang.Integer = java.lang.Integer.valueOf(123)
  val l: java.lang.Long = java.lang.Long.valueOf(Random.nextLong())
  val str: String = Random.nextString(10)
  val longlist: List[Object] = (1L to 10L).map(java.lang.Long.valueOf(_)).toList
  val intlist: List[Object] = (1 to 10).map(java.lang.Double.valueOf(_)).toList
  val doublelist: List[Object] = (1 to 10).map(_ => java.lang.Double.valueOf(Random.nextDouble())).toList
  val strlist: List[Object] = (1 to 10).map(_ => Random.nextString(10)).toList
  val nulllist: List[Object] = List(1,2L,3.5, null).asInstanceOf[List[Object]]

  "isDouble" should "check if Number is double correctly" in {
    CommonUtils.isDouble(d) shouldBe true
    CommonUtils.isDouble(i) shouldBe false
    CommonUtils.isDouble(l) shouldBe false
  }

  "isLong" should "check if Number is Long correctly" in {
    CommonUtils.isLong(l) shouldBe true
    CommonUtils.isLong(i) shouldBe true
    CommonUtils.isLong(d) shouldBe false
  }

  "isLongList/DoubleList/StringList/ConfigList" should "check correctly list type" in {
    CommonUtils.isLongList(longlist) shouldBe true
    CommonUtils.isLongList(intlist) shouldBe true
    CommonUtils.isLongList(doublelist) shouldBe false
    CommonUtils.isLongList(strlist) shouldBe false
    CommonUtils.isLongList(nulllist) shouldBe false

    CommonUtils.isDoubleList(doublelist) shouldBe true
    CommonUtils.isDoubleList(intlist) shouldBe false
    CommonUtils.isDoubleList(longlist) shouldBe false
    CommonUtils.isDoubleList(strlist) shouldBe false
    CommonUtils.isDoubleList(nulllist) shouldBe false

    CommonUtils.isStringList(strlist) shouldBe true
    CommonUtils.isStringList(longlist) shouldBe false
    CommonUtils.isStringList(doublelist) shouldBe false
    CommonUtils.isStringList(intlist) shouldBe false
    CommonUtils.isStringList(nulllist) shouldBe false
  }

}
