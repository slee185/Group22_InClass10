// Group22_InClass10
// LoginFragment.java
// Ken Stanley & Stephanie Karp

package edu.uncc.inclass10;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edu.uncc.inclass10.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    FragmentLoginBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonLogin.setOnClickListener(v -> {
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            if (email.isEmpty()) {
                Toast.makeText(getActivity(), "Enter valid email!", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(getActivity(), "Enter valid password!", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(loginTask -> {
                            if (!loginTask.isSuccessful()) {
                                return;
                            }
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            mListener.goToPosts(user);
                        })
                        .addOnFailureListener(e -> {
                            AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                            builder
                                    .setTitle(R.string.error_account_title)
                                    .setMessage(e.getMessage())
                                    .setPositiveButton("Ok", (dialog, i) -> dialog.dismiss())
                                    .show();
                        });
            }
        });

        binding.buttonCreateNewAccount.setOnClickListener(v -> mListener.createNewAccount());

        requireActivity().setTitle(R.string.login_label);
    }

    LoginListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (LoginListener) context;
    }

    interface LoginListener {
        void createNewAccount();

        void goToPosts(FirebaseUser user);
    }
}