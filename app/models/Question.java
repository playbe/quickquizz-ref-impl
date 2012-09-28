package models;

import org.apache.commons.lang.NotImplementedException;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;

import static play.data.validation.Constraints.Required;

@Entity
public class Question extends Model {

    private static final Finder<Long, Question> find = new Finder<Long, Question>(Long.class, Question.class);

    @Id
    public Long id;

    @Required
    public String question;

    @Required
    public String expectedAnswer;


    public void post() {
        throw new NotImplementedException();
    }

    public static Question byId(Long id) {
        return find.byId(id);
    }

    public static List<Question> findAllQuestions() {
        return find.all();
    }

}
