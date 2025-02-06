package com.example.FinalProject.Repository;

import com.example.FinalProject.Model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {

    @Query("Select um from UserModel um where um.userName = :username")
    UserModel findByUsername(@Param("username") String username);

    @Query("Select um from UserModel um where um.id = :id")
    UserModel getById(@Param("id") UUID id);
}
