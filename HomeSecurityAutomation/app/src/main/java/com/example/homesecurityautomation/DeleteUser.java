package com.example.homesecurityautomation;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DeleteUser extends AppCompatActivity implements View.OnClickListener {

    private Button back, delete;
    ListView list;
    List<User> users;
    DatabaseReference databaseReference;
    int index;
    User admin;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_user);

        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        users = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, R.layout.user_text, users);


        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {

                    User user = postSnapshot.getValue(User.class);
                    Log.d("user adding", user.getUsername());

                    if(user.getAdmin())
                    {
                        admin = user;
                    }
                    else {
                        users.add(user);
                    }

                }
                list.setAdapter(adapter);
                Log.d("list", users.toString());

                if(!users.isEmpty()) {
                    list.setSelection(0);
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

        //list.setOnItemClickListener((p, V, pos, id) -> SelectUser(pos));
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i;
                Log.d("SelectedUser", users.get(i).toString());
            }
        });

        FirebaseUser u = firebaseAuth.getCurrentUser();
        Log.d("Current User", u.getEmail());
        firebaseAuth.signOut();
        if(firebaseAuth.getCurrentUser() == null)
        {
            Log.d("Current User", "SIGNED OUT");
        }

        firebaseAuth = FirebaseAuth.getInstance();

        Log.d("Signed out","sign out");
        firebaseAuth.signInWithEmailAndPassword(admin.getUsername(), admin.getPassword());

                /*.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success", "signInWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("fails", "signInWithEmail:failure", task.getException());
                    Toast.makeText(DeleteUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }

                // ...
            }
        });
*/
        Log.d("checking sign in", "Checking");
        if(firebaseAuth.getCurrentUser() == null)
        {
            Log.d("Current User", "SIGNED OUT");
        }
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
            final User userRemove = users.get(index);
            Log.d("Selected User", userRemove.getUsername());
            Log.d("Password",userRemove.getPassword());
            FirebaseUser u = firebaseAuth.getCurrentUser();
            Log.d("Current User", u.getEmail());
            firebaseAuth.signOut();
            if(firebaseAuth.getCurrentUser() == null)
            {
                Log.d("Current User", "SIGNED OUT");
            }

            firebaseAuth = FirebaseAuth.getInstance();

            Log.d("Signed out","sign out");
            firebaseAuth.signInWithEmailAndPassword(admin.getUsername(), admin.getPassword());

                /*.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success", "signInWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("fails", "signInWithEmail:failure", task.getException());
                    Toast.makeText(DeleteUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }

                // ...
            }
        });
*/
            Log.d("checking sign in", "Checking");
            if(firebaseAuth.getCurrentUser() == null)
            {
                Log.d("Current User", "SIGNED OUT");
            }
            finish();
            startActivity(new Intent(this, AdminSettings.class));

        }
    }

    public void deleteUser()
    {
        final User userRemove = users.get(index);
        Log.d("Selected User", userRemove.getUsername());
        Log.d("Password",userRemove.getPassword());
        FirebaseUser u = firebaseAuth.getCurrentUser();
        Log.d("Current User", u.getEmail());
        firebaseAuth.signOut();
        if(firebaseAuth.getCurrentUser() == null)
        {
            Log.d("Current User", "SIGNED OUT");
        }

        firebaseAuth = FirebaseAuth.getInstance();

        Log.d("Signed out","sign out");
        firebaseAuth.signInWithEmailAndPassword(admin.getUsername(), admin.getPassword());

                /*.addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("success", "signInWithEmail:success");
                    FirebaseUser user = firebaseAuth.getCurrentUser();

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("fails", "signInWithEmail:failure", task.getException());
                    Toast.makeText(DeleteUser.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }

                // ...
            }
        });
*/
        Log.d("checking sign in", "Checking");
        if(firebaseAuth.getCurrentUser() == null)
        {
            Log.d("Current User", "SIGNED OUT");
        }
        /*
        FirebaseUser r = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseAuth.getCurrentUser()==null)
        {
            Log.d("NULL USER", "DID NOT SIGN IN");
        }
        //Log.d("delete user: ", r.getEmail());
        r.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("DELETE", "User account deleted.");
                }
                else{
                    Log.d("FAILED", "DID NOT DELETE");
                }

            }
        });
        firebaseAuth.signInWithEmailAndPassword(admin.getUsername(),admin.getPassword());
        String id = userRemove.getUsername().replace(".", "_");

        databaseReference.getDatabase().getReference("users").child(id).removeValue();
        users.remove(index);
        */

    }


}
