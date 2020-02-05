package com.project.restapi.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.restapi.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}