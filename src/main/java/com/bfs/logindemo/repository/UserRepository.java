package com.bfs.logindemo.repository;

import com.bfs.logindemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User u SET u.active = :isActive WHERE u.userId = :userId")
    int updateStatus(int userId, boolean isActive);
}
