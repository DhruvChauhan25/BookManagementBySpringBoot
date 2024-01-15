package com.bookmanagement.bookmanagement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.bookmanagement.bookmanagement.User;


import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
  List<User> findByBookName(String bookName);

}
