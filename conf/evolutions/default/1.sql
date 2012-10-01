# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table answerer (
  id                        bigint not null,
  username                  varchar(255),
  score                     integer,
  constraint pk_answerer primary key (id))
;

create table question (
  id                        bigint not null,
  question                  varchar(255),
  expected_answer           varchar(255),
  tweeted                   boolean,
  tweeted_on                timestamp,
  answered                  boolean,
  constraint pk_question primary key (id))
;

create sequence answerer_seq;

create sequence question_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists answerer;

drop table if exists question;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists answerer_seq;

drop sequence if exists question_seq;

