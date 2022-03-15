package com.example.tristagramm.repository;

import com.example.tristagramm.domain.Vote;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface VoteRepository extends CrudRepository<Vote, Long> {
    List<Vote> findByTag(String tag);
    void deleteById(Long aLong);
}
