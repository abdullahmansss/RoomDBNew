package softagi.s.roomdb.Ui.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import softagi.s.roomdb.Data.AppDatabase;
import softagi.s.roomdb.Models.UserModel;
import softagi.s.roomdb.R;

public class MainActivity extends AppCompatActivity
{
    private AppDatabase database;
    private RecyclerView recyclerView;
    private userAdapter userAdapter;
    private EditText name_field,mobile_field;
    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDatabase();
        initRecycler();
        initViews();
        new getUsers().execute();
    }

    private void initViews()
    {
        name_field = findViewById(R.id.user_name_field);
        mobile_field = findViewById(R.id.user_mobile_field);
        floatingActionButton = findViewById(R.id.add_user_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*String userName = name_field.getText().toString();
                String userMobile = mobile_field.getText().toString();

                if (userName.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "enter full name", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (userMobile.isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "enter mobile", Toast.LENGTH_SHORT).show();
                    return;
                }

                UserModel userModel = new UserModel(userName,userMobile);
                new addUser().execute(userModel);*/
                int [] a = {6,8,9};
                new getById().execute(a);
            }
        });
    }

    class deleteUser extends AsyncTask<UserModel, Void, Void>
    {
        @Override
        protected Void doInBackground(UserModel... userModels)
        {
            database.userDao().deleteUser(userModels);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            Toast.makeText(MainActivity.this, "deleted", Toast.LENGTH_SHORT).show();
        }
    }

    class addUser extends AsyncTask<UserModel, Void, Void>
    {
        @Override
        protected Void doInBackground(UserModel... userModels)
        {
            database.userDao().insertAll(userModels);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            super.onPostExecute(aVoid);
            new getUsers().execute();
        }
    }

    class getUsers extends AsyncTask<Void, Void, List<UserModel>>
    {
        List<UserModel> userModelList;

        @Override
        protected List<UserModel> doInBackground(Void... voids)
        {
            userModelList = database.userDao().getAll();
            return userModelList;
        }

        @Override
        protected void onPostExecute(List<UserModel> userModels)
        {
            super.onPostExecute(userModels);
            userAdapter = new userAdapter(userModels);
            recyclerView.setAdapter(userAdapter);
        }
    }

    class getById extends AsyncTask<int[], Void, List<UserModel>>
    {
        List<UserModel> userModelList;

        @Override
        protected List<UserModel> doInBackground(int[]... ints)
        {
            userModelList = database.userDao().getAllById(ints[0]);
            return userModelList;
        }

        @Override
        protected void onPostExecute(List<UserModel> userModels)
        {
            super.onPostExecute(userModels);
            userAdapter = new userAdapter(userModels);
            recyclerView.setAdapter(userAdapter);
        }
    }

    private void initRecycler()
    {
        recyclerView = findViewById(R.id.recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(
                getApplicationContext(),
                RecyclerView.VERTICAL,
                false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                getApplicationContext(),
                DividerItemDecoration.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    private void initDatabase()
    {
        database = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "users").build();
    }

    class userAdapter extends RecyclerView.Adapter<userAdapter.userViewHolder>
    {
        List<UserModel> userModelList;

        userAdapter(List<UserModel> userModelList)
        {
            this.userModelList = userModelList;
        }

        @NonNull
        @Override
        public userViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.user_item, parent, false);
            return new userViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull userViewHolder holder, final int position)
        {
            final UserModel userModel = userModelList.get(position);

            int user_id = userModel.getId();
            String user_name = userModel.getName();
            String user_mobile = userModel.getMobile();

            holder.id.setText(String.valueOf(user_id));
            holder.name.setText(user_name);
            holder.mobile.setText(user_mobile);

            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    new deleteUser().execute(userModel);
                    userModelList.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }

        @Override
        public int getItemCount()
        {
            return userModelList.size();
        }

        class userViewHolder extends RecyclerView.ViewHolder
        {
            TextView id,name,mobile;

            userViewHolder(@NonNull View itemView)
            {
                super(itemView);

                id = itemView.findViewById(R.id.user_id);
                name = itemView.findViewById(R.id.user_name);
                mobile = itemView.findViewById(R.id.user_mobile);
            }
        }
    }
}