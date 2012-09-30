package models;

import play.Logger;
import play.db.ebean.Model;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
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

    public Boolean tweeted = false;

    public Date tweetedOn;

    public boolean tweet() {
        boolean success = false;
        try {
            Twitter twitter = TwitterFactory.getSingleton();
            twitter.updateStatus(String.format("%s [%d]",
                                 question,
                                 id));

            tweeted = true;
            tweetedOn = new Date();
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
