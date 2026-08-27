package org.example.gupytestspring.repository;

import org.example.gupytestspring.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long > {
}
