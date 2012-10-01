package controllers;

import play.*;
import play.mvc.*;
import static play.mvc.Results.*;
import play.data.*;
import play.libs.*;
import com.avaje.ebean.*;
import play.db.ebean.*;
import models.*;
import play.data.validation.*;
import static play.data.validation.Constraints.*;
import javax.validation.*;
import views.html.*;
import java.util.*;
import play.cache.*;


public class Application extends Controller {


  public static Result js() {
    response().setContentType("text/javascript");
    return ok(
      Routes.javascriptRouter("playRoutes",
        // Routes
        controllers.routes.javascript.QuizMaster.tweetQuestion(),
        controllers.routes.javascript.QuizMaster.deleteQuestion()
      )
    );
  }

  public static Result stars() {
    List<Answerer> as = Answerer.stars(10).getPage(0).getList();
    return ok(
        views.html.stars.render(as)
    );
  }

}