package models;

import play.Logger;
import play.db.ebean.Model;
import com.avaje.ebean.PagingList;

import twitter4j.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.persistence.GeneratedValue;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import static play.data.validation.Constraints.Pattern;
import static play.data.validation.Constraints.Required;

@Entity
public class Answerer extends Model {

    private static final Finder<Long, Answerer> find = new Finder<Long, Answerer>(Long.class, Answerer.class);

    @Transient
    private static final java.util.Random R = new java.util.Random();

    @Id
    @GeneratedValue
    public Long id;

    @Required
    @Pattern(
        value="@.*",
        message="Must start with an '@'"
    )
    public String username;

    public Integer score = 0;

    @play.db.ebean.Transactional
    public static Answerer scored(String answerer, Question question) {
        Answerer a = Answerer.find.where().eq("username", answerer).findUnique();
        if (a == null) {
            a = new Answerer();
            a.username = answerer;
        }
        a.score++;
        a.save();

        question.answered = true;
        question.save();
        return a;
    }

    public static PagingList<Answerer> stars(int pageSize) {
        return Answerer.find.orderBy("score desc").findPagingList(pageSize);
    }

}
