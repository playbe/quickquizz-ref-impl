package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

import static play.data.validation.Constraints.Required;

@Entity
public class Question extends Model {

    @Id
    public Long id;

    @Required
    public String question;

    @Required
    public String expectedAnswer;

    private static final Finder<Long, Question> find = new Finder<Long, Question>(Long.class, Question.class);

    public static List<Question> findAllQuestions() {
        return find.all();
    }
}
