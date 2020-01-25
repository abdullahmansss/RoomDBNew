package softagi.s.roomdb.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import softagi.s.roomdb.Models.UserModel;

@Dao
public interface UserDao
{
    @Query("SELECT * FROM userTable")
    List<UserModel> getAll();

    @Insert
    void insertAll(UserModel... user);

    @Query("SELECT * FROM userTable WHERE userID IN (:uID)")
    List<UserModel> getAllById(int[] uID);

    @Delete
    void deleteUser(UserModel... user);

    @Update
    void updateUser(UserModel... user);
}