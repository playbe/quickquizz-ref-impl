package models;

import play.Logger;
import play.db.ebean.Model;

import twitter4j.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

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

    @Required
    public boolean answered = false;

    @Transient
    private static Boolean sync = true;
    private static Twitter twitter;

    public boolean tweet() {
        boolean success = false;
        try {
            twitter.updateStatus(String.format("%s [#qq%d]",
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

    public static List<String> lastMentions() {
        List<String> s = new ArrayList<String>();
        try {
            List<Status> ss = twitter().getMentions();
            for (Status st : ss) {
                s.add(st.getText());
            } //would like a map please....
        } catch (TwitterException e) {
            Logger.error("Unable to send tweet",
                    e);
        }
        return s;
    }

    private static Twitter twitter() {
        synchronized(sync) {
            if (twitter == null) {
                twitter = TwitterFactory.getSingleton();
            }
            return twitter;
        }
    }

    public static Question questionById(Long id) {
        return find.byId(id);
    }

    public static List<Question> findAllQuestions() {
        return find.all();
    }

}
