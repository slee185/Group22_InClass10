// Group22_InClass10
// MainActivity.java
// Ken Stanley & Stephanie Karp

package edu.uncc.inclass10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import edu.uncc.inclass10.models.Post;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, PostsFragment.PostsListener, CreatePostFragment.CreatePostListener {

    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void createNewAccount() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new SignUpFragment())
                .commit();
    }

    @Override
    public void login() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void logout() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, new LoginFragment())
                .commit();
    }

    @Override
    public void createPost() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, CreatePostFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToPosts() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, PostsFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void setUser(FirebaseUser user) {
        this.user = user;
    }

    @Override
    public void goBackToPosts() {
        getSupportFragmentManager().popBackStack();
    }

}