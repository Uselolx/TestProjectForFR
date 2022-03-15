package com.example.tristagramm.controller;

import com.example.tristagramm.domain.Answer;
import com.example.tristagramm.domain.Role;
import com.example.tristagramm.domain.Vote;
import com.example.tristagramm.domain.User;
import com.example.tristagramm.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class VoteController {
    @Autowired
    private VoteRepository voteRepository;

    @GetMapping("/")
    public String greeting(Map<String, Object> model) {
        return "greeting";
    }

    @GetMapping("/main")
    public Iterable<Vote> getActiveVotes() {
        return voteRepository.findAll();
    }

    @PostMapping("/main")
    public void addVote(
            @AuthenticationPrincipal User user,
            @RequestParam String text,
            @RequestParam List<Answer> answers,
            @RequestParam String tag) {
        Vote vote = new Vote(text, tag, user, answers);
        voteRepository.save(vote);
    }

    @PostMapping("/main/{id}")
    public Iterable<Vote> deleteVote(
            @AuthenticationPrincipal User user,
            @RequestParam Long id) {
        voteRepository.deleteById(id);
        return voteRepository.findAll();
    }

    @GetMapping("/main/{id}")
    public Vote voteDetails(
            @AuthenticationPrincipal User user,
            @RequestParam Long id) throws Exception {
        Optional<Vote> vote = voteRepository.findById(id);
        if (vote.isPresent()) {
            return vote.get();
        }
        throw new Exception("No vote found");
    }

    @GetMapping("/main/{id}/edit")
    public void editVote(
            @AuthenticationPrincipal User user,
            @RequestParam Long id) {
        if (!user.getRoles().equals(Role.USER.toString())) {
            voteRepository.findById(id);
        }
    }

    @PostMapping("/main/{id}/edit")
    public void updateVote(
            @AuthenticationPrincipal User user,
            @RequestParam Long id,
            @RequestParam String text,
            @RequestParam String tag,
            @RequestParam List<Answer> answers
    ) {
        Optional<Vote> vote = voteRepository.findById(id);
        if (vote.isPresent()) {
            vote.get().setText(text);
            vote.get().setAuthor(user);
            vote.get().setAnswers(answers);
            voteRepository.save(vote.get());
        }
    }

    @PostMapping("/main/{id}/cast")
    public void castVote(
            @AuthenticationPrincipal User user,
            @RequestParam Long id,
            @RequestParam Answer answer
    ) {
        Optional<Vote> vote = voteRepository.findById(id);
        if (vote.isPresent()) {
            vote.get().setAnswersOfUser(new HashMap<User, Answer>());
            voteRepository.save(vote.get());
        }
    }
}