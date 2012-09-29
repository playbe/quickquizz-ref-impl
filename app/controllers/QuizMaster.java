package controllers;

import models.Question;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.questionForm;
import views.html.questionList;

import play.libs.Json;
import org.codehaus.jackson.node.ObjectNode;

import static models.Question.findAllQuestions;
import static models.Question.questionById;


public class QuizMaster extends Controller {

    public static Result showQuestionForm(){
        Form<Question> form = form(Question.class);
        return ok(questionForm.render(form));
    }

    public static Result postQuestionForm(){
        Form<Question> form = form(Question.class).bindFromRequest();
        if (form.hasErrors()){
            return badRequest(questionForm.render(form));
        } else {
            form.get().save();
            return allQuestions();
        }
    }

    public static Result tweetQuestion(Long id) {
        Question q = questionById(id);
        q.post();
        ObjectNode result = Json.newObject();
        result.put("status", "OK");
        result.put("quetion", q.question);
        return ok(result);
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
