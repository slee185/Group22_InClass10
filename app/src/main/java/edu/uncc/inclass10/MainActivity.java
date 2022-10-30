// Group22_InClass10
// MainActivity.java
// Ken Stanley & Stephanie Karp

package edu.uncc.inclass10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements LoginFragment.LoginListener,
        SignUpFragment.SignUpListener, PostsFragment.PostsListener, CreatePostFragment.CreatePostListener {

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
                .replace(R.id.containerView, new CreatePostFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToPosts(FirebaseUser user) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerView, PostsFragment.newInstance(user))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goBackToPosts() {
        getSupportFragmentManager().popBackStack();
    }

}