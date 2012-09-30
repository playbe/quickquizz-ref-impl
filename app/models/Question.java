package models;

import org.apache.commons.lang.NotImplementedException;
import play.Logger;
import play.db.ebean.Model;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

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

    public boolean tweeted;

    public boolean tweet() {
        boolean success = false;
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.updateStatus(String.format("@%s %s [%d]",
                                 twitter.getScreenName(),
                                 question,
                                 id));

            tweeted = true;
            this.save();

            success = true;
        } catch (TwitterException e) {
            Logger.error("Unable to send tweet",
                    e);
        }

        return success;
    }

    public static Question questionById(Long id) {
        return find.byId(id);
    }

    public static List<Question> findAllQuestions() {
        return find.all();
    }

}
