package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

import static play.data.validation.Constraints.Required;

@Entity
public class Question extends Model {

    @Id
    public Long id;

    @Required
    public String question;

    @Required
    public String expectedAnswer;

}
