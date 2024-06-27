package com.sl.foodorderingsystem.Repository;


import com.sl.foodorderingsystem.entity.Role;
import com.sl.foodorderingsystem.entity.User;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.Optional;


@Transactional
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String username);

    @Modifying
    @Query(value = "update User u set u.status=:status where u.id=:userid")
    int updateStatus(@RequestParam Integer userid , @RequestParam String status);

    @Query(value="select u from User u where u.role=:role")
    List<User> findAllByRole(@RequestParam Role role);
}
