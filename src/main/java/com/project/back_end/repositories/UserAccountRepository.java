package com.project.back_end.repositories;

import com.project.back_end.models.UserAccount;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
    Optional<UserAccount> findById(ObjectId id);
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<UserAccount> findByPhoneNumber(String phoneNumber);
    boolean existsByEmail(String email);
    Optional<UserAccount> findByEmail(String email);
    List<UserAccount> findByRole(String role);
}
