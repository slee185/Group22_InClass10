// Group22_InClass10
// CreatePostFragment.java
// Ken Stanley & Stephanie Karp

package edu.uncc.inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.uncc.inclass10.databinding.FragmentCreatePostBinding;
import edu.uncc.inclass10.models.Post;

public class CreatePostFragment extends Fragment {

    private static final String ARG_USER = "user";
    private static final String ARG_POSTSLIST = "postsList";

    private FirebaseUser user;
    private ArrayList<Post> mPosts = new ArrayList<>();

    public CreatePostFragment() {
        // Required empty public constructor
    }

    public static CreatePostFragment newInstance(FirebaseUser user, ArrayList<Post> mPosts) {
        CreatePostFragment fragment = new CreatePostFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_USER, user);
        args.putSerializable(ARG_POSTSLIST, mPosts);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = getArguments().getParcelable(ARG_USER);
            mPosts = (ArrayList<Post>) getArguments().getSerializable(ARG_POSTSLIST);
        }
    }

    FragmentCreatePostBinding binding;

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
                Post post = new Post();
                buildPost(postText, post);
                mPosts.add(post);
                mListener.goBackToPosts();
            }
        });

        requireActivity().setTitle(R.string.create_post_label);
    }

    CreatePostListener mListener;

    public void buildPost(String postText, Post post) {
        post.created_by_name = user.getDisplayName();
        post.post_id = getString(mPosts.size() + 1);
        post.created_by_uid = user.getUid();
        post.post_text = postText;
        Date drinkDate = Calendar.getInstance().getTime();
        post.created_at = drinkDate.toString();
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