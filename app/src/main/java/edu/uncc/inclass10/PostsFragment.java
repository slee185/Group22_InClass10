// Group22_InClass10
// PostsFragment.java
// Ken Stanley & Stephanie Karp

package edu.uncc.inclass10;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import edu.uncc.inclass10.databinding.FragmentPostsBinding;
import edu.uncc.inclass10.models.Post;

public class PostsFragment extends Fragment {

    private static final String ARG_USER = "user";

    private FirestoreRecyclerAdapter<Post, PostHolder> adapter;

    private FirebaseUser user;
    private final FirebaseFirestore mStore = FirebaseFirestore.getInstance();
    private final CollectionReference mPosts = mStore.collection("posts");

    FragmentPostsBinding binding;
    PostsListener mListener;

    public static PostsFragment newInstance(FirebaseUser user) {
        PostsFragment fragment = new PostsFragment();
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
        binding = FragmentPostsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonCreatePost.setOnClickListener(v -> mListener.createPost());

        binding.buttonLogout.setOnClickListener(v -> {
            FirebaseAuth auth = FirebaseAuth.getInstance();
            auth.signOut();
            mListener.logout();
        });

        binding.recyclerViewPosts.setLayoutManager(new LinearLayoutManager(getContext()));

        Query query = mPosts.orderBy("created_at", Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Post> options = new FirestoreRecyclerOptions.Builder<Post>()
                .setQuery(query, Post.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Post, PostHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull Post model) {
                holder.setCreated_by_name(model.getCreated_by_name());
                holder.setCreated_at(model.getCreated_at());
                holder.setPost_text(model.getPost_text());
            }

            @NonNull
            @Override
            public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row_item, parent, false);
                return new PostHolder(view);
            }
        };
        binding.recyclerViewPosts.setAdapter(adapter);

        requireActivity().setTitle(R.string.posts_label);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (PostsListener) context;
    }

    private static class PostHolder extends RecyclerView.ViewHolder {
        private final View view;

        PostHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setCreated_at(String created_at) {
            TextView textView = (TextView) view.findViewById(R.id.textViewCreatedAt);
            textView.setText(created_at);
        }

        void setCreated_by_name(String created_by_name) {
            TextView textView = (TextView) view.findViewById(R.id.textViewCreatedBy);
            textView.setText(created_by_name);
        }

        void setPost_text(String post_text) {
            TextView textView = (TextView) view.findViewById(R.id.textViewPost);
            textView.setText(post_text);
        }
    }

    interface PostsListener {
        void logout();

        void createPost();

        void goBackToPosts();
    }
}