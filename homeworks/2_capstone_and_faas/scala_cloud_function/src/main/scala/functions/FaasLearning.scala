package functions

import akka.actor.ActorSystem
import akka.http.scaladsl.model.ContentTypes
import akka.stream.alpakka.googlecloud.storage.scaladsl.GCStorage
import akka.stream.scaladsl.Source
import akka.util.ByteString
import com.google.cloud.functions.{HttpFunction, HttpRequest, HttpResponse}
import play.api.libs.json._

import java.util.{Calendar, UUID}
import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.jdk.CollectionConverters._

class FaasLearning extends HttpFunction {
  override def service(httpRequest: HttpRequest, httpResponse: HttpResponse): Unit = {
    implicit val system: ActorSystem = ActorSystem("QuickStart")

    val bucketName           = "okyshkevych-tf-learning"
    val currentTime          = Calendar.getInstance().toInstant.toString
    val fileUniqueIdentifier = UUID.randomUUID().toString
    val pathToFile           = s"faas/requests/$currentTime-$fileUniqueIdentifier"

    val method: JsObject = Json.obj("method" -> httpRequest.getMethod)
    val uri: JsObject    = Json.obj("uri" -> httpRequest.getUri)
    val path: JsObject   = Json.obj("path" -> httpRequest.getPath)
    val query: JsObject  = Json.obj("query" -> httpRequest.getQuery.orElse("None"))
    val queryParams      = Json.obj("queryParam" -> httpRequest.getQueryParameters.asScala.toMap.view.mapValues(_.asScala.toList))

    val json: JsObject       = method ++ uri ++ path ++ query ++ queryParams
    val byteJson: ByteString = ByteString(Json.prettyPrint(json))

    val writeFile =
      GCStorage.simpleUpload(bucketName, pathToFile, Source.single(byteJson), ContentTypes.`text/plain(UTF-8)`).run

    Await.ready(writeFile, Duration.Inf)
    httpResponse.getWriter.write(s"Your request and all your personal data was stolen by us, have a nice day!")
  }
}


