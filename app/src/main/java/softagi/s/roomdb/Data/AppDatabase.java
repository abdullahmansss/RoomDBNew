package softagi.s.roomdb.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import softagi.s.roomdb.Models.UserModel;

@Database(entities = {UserModel.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase
{
    public abstract UserDao userDao();
}
