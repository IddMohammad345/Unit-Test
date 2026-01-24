package com.idd.usersservice.io;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UserEntity,String> {
    UserEntity findByEmail(String username);
}
