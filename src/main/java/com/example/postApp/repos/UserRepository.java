package com.example.postApp.repos;

import com.example.postApp.entities.Post;
import com.example.postApp.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

}
