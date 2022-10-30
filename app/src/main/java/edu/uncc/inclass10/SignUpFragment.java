// Group22_InClass10
// SignUpFragment.java
// Ken Stanley & Stephanie Karp

package edu.uncc.inclass10;

import android.app.AlertDialog;
import android.content.Context;
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
import com.google.firebase.auth.UserProfileChangeRequest;

import edu.uncc.inclass10.databinding.FragmentSignUpBinding;

public class SignUpFragment extends Fragment {
    FragmentSignUpBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignUpBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.buttonCancel.setOnClickListener(v -> mListener.login());

        binding.buttonSignup.setOnClickListener(v -> {
            String name = binding.editTextName.getText().toString();
            String email = binding.editTextEmail.getText().toString();
            String password = binding.editTextPassword.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(getActivity(), "Enter valid name!", Toast.LENGTH_SHORT).show();
            } else if (email.isEmpty()) {
                Toast.makeText(getActivity(), "Enter valid email!", Toast.LENGTH_SHORT).show();
            } else if (password.isEmpty()) {
                Toast.makeText(getActivity(), "Enter valid password!", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseAuth auth = FirebaseAuth.getInstance();

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(createTask -> {
                    if (!createTask.isSuccessful()) {
                        return;
                    }

                    FirebaseUser user = createTask.getResult().getUser();
                    UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .build();

                    assert user != null;
                    user.updateProfile(request).addOnCompleteListener(updateTask -> {
                        if (!updateTask.isSuccessful()) {
                            return;
                        }

                        mListener.goToPosts(user);
                    })
                    .addOnFailureListener(e -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                        builder
                                .setTitle(R.string.error_account_title)
                                .setMessage(e.getMessage())
                                .show();
                    });
                })
                .addOnFailureListener(e -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                    builder
                            .setTitle(R.string.error_account_title)
                            .setMessage(e.getMessage())
                            .show();
                });
            }
        });

        requireActivity().setTitle(R.string.create_account_label);
    }

    SignUpListener mListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SignUpListener) context;
    }

    interface SignUpListener {
        void login();
        void goToPosts(FirebaseUser user);
    }
}