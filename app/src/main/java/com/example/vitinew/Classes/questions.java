package com.example.vitinew.Classes;

public class questions {
    String question,answer;

    public questions() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public questions(String question, String answer) {

        this.question = question;
        this.answer = answer;
    }
}
