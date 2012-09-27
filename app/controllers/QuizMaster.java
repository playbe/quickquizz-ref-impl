package controllers;

import models.Question;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.questionForm;
import views.html.questionList;

import java.util.List;

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
            return showQuestionForm();
        }
    }

    public static Result allQuestions(){
        List<Question> questions = Question.findAllQuestions();
        return ok(questionList.render(questions));
    }
}
