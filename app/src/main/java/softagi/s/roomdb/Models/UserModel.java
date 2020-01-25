package softagi.s.roomdb.Models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userTable")
public class UserModel
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    private int id;
    @ColumnInfo(name = "full_name")
    private String name;
    @ColumnInfo(name = "mobile")
    private String mobile;

    public UserModel(String name, String mobile)
    {
        this.name = name;
        this.mobile = mobile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}