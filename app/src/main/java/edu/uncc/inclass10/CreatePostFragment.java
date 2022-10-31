// Group22_InClass10
// CreatePostFragment.java
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

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.uncc.inclass10.databinding.FragmentCreatePostBinding;
import edu.uncc.inclass10.models.Post;

public class CreatePostFragment extends Fragment {

    private static final String ARG_USER = "user";

    private FirebaseUser user;

    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private final CollectionReference mPosts = mStore.collection("posts");

    FragmentCreatePostBinding binding;
    CreatePostListener mListener;

    public static CreatePostFragment newInstance(FirebaseUser user) {
        CreatePostFragment fragment = new CreatePostFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatePostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCancel.setOnClickListener(v -> mListener.goBackToPosts());

        binding.buttonSubmit.setOnClickListener(v -> {
            String postText = binding.editTextPostText.getText().toString();

            if (postText.isEmpty()) {
                Toast.makeText(getActivity(), "Enter valid post !!", Toast.LENGTH_SHORT).show();
            } else {
                Post post = new Post(user.getDisplayName(), user.getUid(), postText);
                mPosts.document(post.getPost_id()).set(post)
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                return;
                            }
                            mListener.goBackToPosts();
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

        requireActivity().setTitle(R.string.create_post_label);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (CreatePostListener) context;
    }

    interface CreatePostListener {
        void goBackToPosts();
    }
}