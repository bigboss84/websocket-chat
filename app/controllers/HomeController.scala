package controllers

import actors.{ChatActor, ChatManager}
import akka.actor.{ActorSystem, Props}
import akka.stream.Materializer
import play.api.libs.streams.ActorFlow
import play.api.mvc._

import javax.inject._

@Singleton
class HomeController @Inject()
(val controllerComponents: ControllerComponents)
(implicit mat: Materializer, actorSystem: ActorSystem) extends BaseController {

  private val manager = actorSystem.actorOf(Props[ChatManager], "ChatManager")

  def index: Action[AnyContent] = Action { _ =>
    Ok(views.html.index())
  }

  def socket: WebSocket = WebSocket.accept[String, String] { _ =>
    ActorFlow.actorRef { out =>
      ChatActor.props(out, manager)
    }
  }

}
