package actors

import actors.ChatActor.SendMessage
import akka.actor.{Actor, ActorRef, Props}

class ChatActor(out: ActorRef, manager: ActorRef) extends Actor {

  manager ! ChatManager.NewChatter(self)

  override def receive: Receive = {
    case m: String => manager ! ChatManager.Message(m)
    case ChatManager.Message(m) => out ! m
    case SendMessage(m) => out ! m
    case m => println(s"unhandled message: $m")
  }
}

object ChatActor {
  def props(out: ActorRef, manager: ActorRef) = Props(new ChatActor(out, manager))

  case class SendMessage(m: String)
}
