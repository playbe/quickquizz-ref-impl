import play.*;
import play.libs.*;
import play.Logger;

import java.util.*;

import com.avaje.ebean.*;

import models.*;

import play.libs.Akka;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import akka.util.*;
import akka.actor.*;
import twitter4j.TwitterFactory;


public class Global extends GlobalSettings {



    public static class AnswerValidator extends UntypedActor {

        static Pattern p = Pattern.compile(".*#qq([0-9]+).*");

        public void onReceive(Object message) throws Exception {
            if (message instanceof String) {
                List<String> lm = Question.lastMentions(); //todo the return should keep the answerer ID...
                for (String s : lm) {
                    Matcher m = p.matcher(s);
                    if (m.matches()) {
                        Long id = Long.parseLong(m.group(1));
                        Question q = Question.questionById(id);
                        if (q != null && !q.answered) {
                            if (s.contains(q.expectedAnswer)) {
                                q.answered = true;
                                q.save();

                                // steve: this is just an idea...need to parse the correct responder's name, and I'm just about to go for a shower
                                TwitterFactory.getSingleton().updateStatus(String.format("[#qq%d] has been answered by xxx",
                                                                                         id));

                                Logger.info("Question " + id + " has been answered!");
                            } else {
                              Logger.warn("bad answer : " + id + " -- " + s);
                            }
                        } else {
                          Logger.warn("bad question or already answered : " + id + " -- " + s);
                        }
                    } else {
                      Logger.info("not an answer : " + s);
                    }
                }

            } else {
                unhandled(message);
            }
        }
    }


    public void onStart(Application app) {
        ActorRef myActor = Akka.system().actorOf(new Props(AnswerValidator.class));

        Akka.system().scheduler().schedule(
            Duration.create(0, TimeUnit.MILLISECONDS),
            Duration.create(10, TimeUnit.SECONDS),
            myActor,
            "tick"
        );

        System.out.println("Started listening for answers");
    }
}