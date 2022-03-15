package com.example.tristagramm.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;
    private String tag;
    @ElementCollection(targetClass = Answer.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "answer_id", joinColumns = @JoinColumn(name = "user_id"))
    private List<Answer> answers;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;
    @ElementCollection(fetch = FetchType.EAGER)
    private Map<User, Answer> answersOfUser;

    public Vote() {

    }

    public Vote(String text, String tag, User author, List<Answer> answers) {
        this.text = text;
        this.tag = tag;
        this.author = author;
        this.answers = answers;
    }

    public String getAuthorName() {
        return author != null ? author.getUsername() : "<none>";
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Map<User, Answer> getAnswersOfUser() {
        return answersOfUser;
    }

    public void setAnswersOfUser(Map<User, Answer> answersOfUser) {
        this.answersOfUser = answersOfUser;
    }
}
