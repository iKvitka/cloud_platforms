package functions

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.alpakka.googlecloud.storage.StorageObject
import akka.stream.alpakka.googlecloud.storage.scaladsl.GCStorage
import akka.stream.scaladsl.{Sink, Source}
import com.google.cloud.functions.{HttpFunction, HttpRequest, HttpResponse}

import scala.concurrent.duration.Duration
import scala.concurrent.{Await, Future}


// [START functions_helloworld_get]
class ScalaHelloWorld extends HttpFunction {
  override def service(httpRequest: HttpRequest, httpResponse: HttpResponse): Unit = {
    implicit val system: ActorSystem = ActorSystem("QuickStart")

    val listSource: Future[Seq[StorageObject]] = GCStorage.listBucket("okyshkevych-tf-learning",None).runWith(Sink.seq)

    val result = Await.result(listSource, Duration.Inf)

    httpResponse.getWriter.write(s"files in backet: ${result.mkString("\n")}")
  }
}
// [END functions_helloworld_get]
