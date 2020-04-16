package com.elad.rest

import cats.effect.{ContextShift, ExitCode, IO, IOApp, Timer}
import com.elad.dao.Subscription
import io.circe._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.io._
import org.http4s.implicits._
import org.http4s.server.Router
import org.http4s.server.blaze.BlazeServerBuilder

import scala.concurrent.ExecutionContext.Implicits.global

object MyServer extends IOApp {

  implicit val decoder = jsonOf[IO, Subscription]
  implicit val encoder = jsonEncoderOf[IO, Subscription]

  val SUB = "sub"

  val root = Root / SUB

  val service = HttpRoutes.of[IO] {
    case GET -> root / id =>
      println("get")
      Subscription.readById(id).flatMap(_.fold(NotFound())(Ok(_)))

    case request@POST -> root => {
      println("create")
      val temp: IO[Option[Subscription]] = request.as[Subscription].flatMap(c => Subscription.create(c))
      temp.flatMap(_.fold(InternalServerError())(Created(_)))
    }
    case request@PUT -> root => {
      println("put)")
      val historyVal: IO[Option[Subscription]] = request.as[Subscription].flatMap(c => Subscription.update(c))
      historyVal.flatMap((_.fold(InternalServerError())(Ok(_))))
    }
    case request@DELETE -> root => {
      println("delete)")
      val deleteVal: IO[Option[Subscription]] = request.as[Subscription].flatMap(c => Subscription.delete(c))
      deleteVal.flatMap((_.fold(InternalServerError())(Ok(_))))
    }
  }

  val routes = Router("/" -> service).orNotFound

  import scala.concurrent.ExecutionContext.Implicits.global
  import cats.implicits._





  //val fiber = serverBuilder.resource.use(_ => IO.never).start.unsafeRunSync()

  override def run(args: List[String]): IO[ExitCode] = {
    BlazeServerBuilder[IO]
      .bindHttp(8080, "0.0.0.0")
      .withHttpApp(routes)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}




