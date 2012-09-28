package controllers;

import models.Question;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.questionForm;
import views.html.questionList;

import java.util.List;

import static models.Question.findAllQuestions;

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
            question.post();
            return showQuestionForm();
        }
    }

    public static Result allQuestions(){
        return ok(questionList.render(findAllQuestions()));
    }
}
