package com.example.sbtest.repository;

import com.example.sbtest.entity.Card;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends CrudRepository<Card, UUID> {

    @Transactional(propagation = Propagation.REQUIRED)
    Optional<Card> findById(UUID id) ;

    @Transactional(propagation = Propagation.REQUIRED)
    Card save(Card entity);
}
