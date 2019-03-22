package com.example.homesecurityautomation;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteUser extends AppCompatActivity implements View.OnClickListener {

    private Button back, delete;
    private ListView list;
    List<User> users;
    DatabaseReference databaseReference;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);
                    Log.d("user adding", user.getUsername());

                    users.add(user);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        list = findViewById(R.id.list);
        back = findViewById(R.id.back);
        delete = findViewById(R.id.delete);

        back.setOnClickListener(this);
        delete.setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter<User>(this, R.layout.user_text, users);
        list.setAdapter(adapter);

        if(!users.isEmpty()) {
            list.setSelection(0);
        }
        //list.setOnItemClickListener((p, V, pos, id) -> SelectUser(pos));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
            }
        });
    }


    public void onClick(View view)
    {
        if(view == back)
        {
            finish();
            startActivity(new Intent(this, AdminSettings.class));
        }
        if(view == delete)
        {

        }
    }


}
