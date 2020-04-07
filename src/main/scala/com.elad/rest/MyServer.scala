package com.elad.rest

import cats.effect.IO
import com.elad.dao.Subscription
import fs2.StreamApp
import io.circe.generic.auto._
import org.http4s.HttpService
import org.http4s.dsl.Http4sDsl
import org.http4s.circe._
import org.http4s.server.blaze.BlazeBuilder
import org.http4s.util.ExitCode

import scala.concurrent.ExecutionContext.Implicits.global

object MyServer extends StreamApp[IO] with Http4sDsl[IO] {

  implicit val decoder = jsonOf[IO, Subscription]
  implicit val encoder = jsonEncoderOf[IO, Subscription]

  val SUB = "sub"

  val root = Root / SUB

  val service = HttpService[IO] {
    case request@GET -> root / id =>
      println("get")
      Subscription.readById(id).flatMap(_.fold(NotFound())(Ok(_)))

    case request@POST -> root  => {
      println("create")
      val temp: IO[Option[Subscription]] = request.as[Subscription].flatMap(c => Subscription.create(c))
      temp.flatMap(_.fold(InternalServerError())(Created(_)))
    }
    case request@PUT -> root =>{
      println("put)")
      val historyVal: IO[Option[Subscription]] = request.as[Subscription].flatMap(c=> Subscription.update(c))
      historyVal.flatMap((_.fold(InternalServerError())(Ok(_))))
    }
    case request@DELETE -> root =>{
      println("delete)")
      val deleteVal: IO[Option[Subscription]] = request.as[Subscription].flatMap(c=> Subscription.delete(c))
      deleteVal.flatMap((_.fold(InternalServerError())(Ok(_))))
    }
  }

  def stream(args: List[String], requestShutdown: IO[Unit])=
    BlazeBuilder[IO]
      .bindLocal(9090)
      .mountService(service, "/")
      .serve
}


