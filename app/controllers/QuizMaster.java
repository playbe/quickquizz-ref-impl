package controllers;

import models.Question;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.questionForm;
import views.html.questionList;

import play.libs.Json;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import java.util.List;

import static models.Question.findAllQuestions;
import static models.Question.byId;


import org.apache.commons.lang.NotImplementedException;


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
            Question question = form.get();
            question.save();
            return showQuestionForm();
        }
    }

    public static Result tweetQuestion(Long id) {
        Question q = byId(id);
        ObjectNode result = Json.newObject();
        q.post();
        result.put("status", "OK");
        result.put("quetion", q.question);
        return ok(result);
    }

    public static Result deleteQuestion(Long id) {
        Question q = byId(id);
        q.delete();
        ObjectNode result = Json.newObject();
        result.put("status", "OK");
        return ok(result);
    }

    public static Result allQuestions(){
        return ok(questionList.render(findAllQuestions()));
    }
}
