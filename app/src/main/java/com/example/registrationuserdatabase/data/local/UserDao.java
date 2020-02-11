package com.example.registrationuserdatabase.data.local;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM user") List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_email LIKE :email AND "
            + "user_password LIKE :password LIMIT 1")
    User findByEmail(String email, String password);

    @Insert
    void insertAll(User... users);

    @Insert
    void insertSingle(User user);

    @Delete
    void delete(User user);

    @Delete
    void deleteAll(List<User> users);
}
