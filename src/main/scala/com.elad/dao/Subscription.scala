package com.elad.dao

import java.util.UUID

import cats.effect.IO
import cats.implicits._
import com.elad.db.HikariConnectionPool

import scala.collection.immutable.HashMap

case class Subscription(subscriptionId: String, subscribedAppName: String, dataAppName: String)



object Subscription{

  var subsMap: HashMap[String, Subscription] = HashMap()

  def readById(id: String) = IO{

    subsMap.get(id)
//    val query =
//      """
//        |SELECT id, make, model, year, colour
//        |  FROM "testDb".car
//        | WHERE id = ?;
//      """.stripMargin
//
//    val connection = HikariConnectionPool.getConnection()
//    val prepStmt = connection.prepareStatement(query)
//    prepStmt.setString(1, id)
//    val resultSet = prepStmt.executeQuery()
//
//    val created = if(resultSet.next()){
//      Some(Subscription(resultSet.getString(1), resultSet.getString(2),
//        resultSet.getString(3)))
//    }
//    else None
//
//    connection.close()
//    created
  }

  def create(obj: Subscription) = {
    println(s"create new resource with key ${obj.subscriptionId}")
    subsMap += (obj.subscriptionId -> obj)
    readById(obj.subscriptionId)
  }
//    val query =
//      """
//        |INSERT INTO "testDb".car(
//        |  id, make, model, year, colour)
//        |  VALUES (?, ?, ?, ?, ?);
//      """.stripMargin
//
//    val connection = HikariConnectionPool.getConnection()
//    val prepStmt = connection.prepareStatement(query)
//    prepStmt.setString(1, obj.subscribedAppName)
//    prepStmt.setString(2, obj.dataAppName)
//
//    val created = prepStmt.execute()
//
//    connection.close()
//    readById(obj.subscriptionId)
//  }.flatten

  def update(obj: Subscription) = {
      println(s"old resource: ${obj}")
      subsMap -= obj.subscriptionId
      subsMap += (obj.subscriptionId -> obj)
      println(s"new resource: ${obj}")
      readById(obj.subscriptionId)

  }

  def delete(obj: Subscription) = IO {
    println(s"resource to delete: ${obj}")
    subsMap -= obj.subscriptionId
    Option.apply(Subscription(obj.subscriptionId, "", ""))
  }


}
