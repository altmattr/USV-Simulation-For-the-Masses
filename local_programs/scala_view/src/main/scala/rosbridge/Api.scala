package rosbridge

import akka.actor.ActorSystem
import akka.{ Done, NotUsed }
import akka.http.scaladsl.Http
import akka.stream.scaladsl._
import akka.http.scaladsl.model._
import akka.http.scaladsl.model.ws._

import scala.concurrent.Future

object subscribe {
  def apply(topic: String, onMessage: Function[String, Unit]): Unit = {
    implicit val system = ActorSystem()
    import system.dispatcher

    val incoming: Sink[Message, Future[Done]] =
      Sink.foreach[Message] {
        case message: TextMessage.Strict =>
          onMessage(message.text)
        case _ =>
      }

    val outgoing = Source.single(TextMessage("""{"op": "subscribe", "topic": """" + topic + """"}""")).concatMat(Source.maybe[Message])(Keep.right)

    val webSocketFlow = Http().webSocketClientFlow(WebSocketRequest("ws://localhost:9090"))

    // the materialized value is a tuple with
    // upgradeResponse is a Future[WebSocketUpgradeResponse] that
    // completes or fails when the connection succeeds or fails
    // and closed is a Future[Done] with the stream completion from the incoming sink
    val (upgradeResponse, closed) =
      outgoing
        .viaMat(webSocketFlow)(Keep.right) // keep the materialized Future[WebSocketUpgradeResponse]
        .toMat(incoming)(Keep.both) // also keep the Future[Done]
        .run()

    // just like a regular http request we can access response status which is available via upgrade.response.status
    // status code 101 (Switching Protocols) indicates that server support WebSockets
    val connected = upgradeResponse.flatMap { upgrade =>
      if (upgrade.response.status == StatusCodes.SwitchingProtocols) {
        Future.successful(Done)
      } else {
        throw new RuntimeException(s"Connection failed: ${upgrade.response.status}")
      }
    }

    // in a real application you would not side effect here
    // connected.onComplete(println)
    // closed.foreach(_ => println("closed"))
  }
}