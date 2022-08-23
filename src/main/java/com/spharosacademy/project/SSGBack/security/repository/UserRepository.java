package com.spharosacademy.project.SSGBack.security.repository;


import com.spharosacademy.project.SSGBack.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 jparepository가 들고있다
// @repository라는 어노테이션이 없어도 됨 ioc 된다. => jparepository를 상속해서
public interface UserRepository extends JpaRepository<User, Integer> {
}