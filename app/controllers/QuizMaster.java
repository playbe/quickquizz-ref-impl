package controllers;

import models.Question;
import org.codehaus.jackson.node.ObjectNode;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.questionForm;
import views.html.questionList;

import java.util.List;

import static models.Question.findAllQuestions;
import static models.Question.questionById;


public class QuizMaster extends Controller {

    public static Result showQuestionForm(){
        Form<Question> form = form(Question.class);
        return ok(questionForm.render(form));
    }

    public static Result postQuestionForm(){
        Form<Question> form = form(Question.class).bindFromRequest();
        Result result;
        if (form.hasErrors()){
            result = badRequest(questionForm.render(form));
        } else {
            form.get().save();
            result = redirect(routes.QuizMaster.allQuestions());
        }
        return result;
    }

    public static Result tweetQuestion(Long id) {
        Question q = questionById(id);
        boolean tweetOk = q.tweet();

        Result result;
        if (tweetOk) {
            ObjectNode node = Json.newObject();
            node.put("status", "OK");
            node.put("question", q.question);
            result = ok(node);
        } else {
            result = badRequest("Unable to send tweet");
        }

        return result;
    }

    public static Result deleteQuestion(Long id) {
        questionById(id).delete();
        ObjectNode result = Json.newObject();
        result.put("status", "OK");
        return ok(result);
    }

    public static Result allQuestions(){
        return ok(questionList.render(findAllQuestions()));
    }

}
