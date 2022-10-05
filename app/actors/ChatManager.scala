package actors

import actors.ChatManager._
import akka.actor.{Actor, ActorRef}

class ChatManager extends Actor {
  private var chatters = List.empty[ActorRef]

  override def receive: Receive = {
    case NewChatter(chatter) => chatters ::= chatter
    case Message(m) => chatters.foreach(_ ! ChatActor.SendMessage(m))
    case m => println(s"unhandled message: $m")
  }
}

object ChatManager {
  case class NewChatter(chatter: ActorRef)

  case class Message(m: String)
}