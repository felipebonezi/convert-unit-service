import javax.inject.Inject
import play.api.OptionalDevContext
import play.api.http._
import play.api.mvc._
import play.api.mvc.request.RequestTarget
import play.api.routing.Router
import play.core.WebCommands

/**
 * Handle all requests in the project.
 * This class have the responsibility to map if we are facing with a REST request or not.
 */
class RequestHandler @Inject() (
    webCommands: WebCommands,
    optDevContext: OptionalDevContext,
    router: Router,
    errorHandler: HttpErrorHandler,
    configuration: HttpConfiguration,
    filters: HttpFilters
) extends DefaultHttpRequestHandler(webCommands, optDevContext, router, errorHandler, configuration, filters) {

  override def handlerForRequest(request: RequestHeader): (RequestHeader, Handler) = {
    super.handlerForRequest {
      if (isREST(request)) {
        addTrailingSlash(request)
      } else {
        request
      }
    }
  }

  private def isREST(request: RequestHeader) = {
    request.uri match {
      case uri: String if uri.contains("v1/") => true
      case _                                  => false
    }
  }

  private def addTrailingSlash(origReq: RequestHeader): RequestHeader = {
    if (!origReq.path.endsWith("/")) {
      val path = origReq.path + "/"
      if (origReq.rawQueryString.isEmpty) {
        origReq.withTarget(
          RequestTarget(path = path, uriString = path, queryString = Map())
        )
      } else {
        origReq.withTarget(
          RequestTarget(path = path, uriString = origReq.uri, queryString = origReq.queryString)
        )
      }
    } else {
      origReq
    }
  }
}
