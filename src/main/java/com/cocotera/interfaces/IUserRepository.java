package com.cocotera.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.cocotera.models.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {
    User findByName(String name);
}
