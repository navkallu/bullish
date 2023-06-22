package com.bullish.elecstore.repository;

import com.bullish.elecstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
