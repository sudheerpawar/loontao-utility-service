package com.loontao.utilityservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.loontao.utilityservice.entity.UserRewardPointsEntity;

import java.util.Optional;

/*
 * RewardPointsRepository is an interface that extends JpaRepository.
 * Repository layer interacts with database to perform CRUD operations.
 * Why Repository layer?
 * 1. To simplify the database operations. No need to write SQL queries.
 * 2. Follows separation of concerns principle. Keeps DB logic separate from business logic in service layer.
 * 3. Works with Spring Data JPA which provides built in methods & allows writing customer queries using JPA.
 * 4. Auto-Implemented by Spring at runtime when declared using interface. Spring handles implementation.
 */
/*
 * JPA (Java Persistence API - A "specification" (interface) that defines how Java applications should interact with databases.
*  Hibernate - A popular "implementation" of JPA (ORM - Object Relational Mapping framework) that provides actual database operations.
*  Spring Data JPA - A layer built on top of JPA to simplify database operations in Spring Boot.
*  JpaRepository - A Spring Data JPA interface that provides built-in CRUD methods and interacts with Hibernate under the hood.
*/
public interface UserRewardPointsRepository extends JpaRepository<UserRewardPointsEntity, String> {
    Optional <UserRewardPointsEntity> findByPhoneNumber(String phoneNumber);
}