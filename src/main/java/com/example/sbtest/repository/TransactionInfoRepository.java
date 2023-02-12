package com.example.sbtest.repository;

import com.example.sbtest.entity.Card;
import com.example.sbtest.entity.TransactionInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TransactionInfoRepository extends CrudRepository<TransactionInfo, UUID> {

    @Transactional(propagation = Propagation.REQUIRED)
    Optional<TransactionInfo> findByRecipientCardId(UUID recipientCardId);
}
