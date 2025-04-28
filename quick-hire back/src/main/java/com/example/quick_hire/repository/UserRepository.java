package com.example.quick_hire.repository;

import com.example.quick_hire.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA na gjeneron metoda te gatshme te cilat
    // nuk kemi nevoje ti krijojme me sql queries
}
