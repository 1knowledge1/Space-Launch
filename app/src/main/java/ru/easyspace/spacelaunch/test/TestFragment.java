package ru.easyspace.spacelaunch.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.easyspace.spacelaunch.R;


public class TestFragment extends Fragment {

    private TestViewModel testViewModel;
    private SwipeRefreshLayout swipeContainer;
    private OnStartTestFragmentListener startListener;

    public interface OnStartTestFragmentListener {
        void startQuestionFragment(SpaceTest test);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnStartTestFragmentListener) {
            startListener = (OnStartTestFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnStartTestFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.tests_swipe_container);
        RecyclerView recycler = view.findViewById(R.id.tests_recycler);
        final TestAdapter adapter = new TestAdapter(startListener);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        Observer<List<SpaceTest>> observer = new Observer<List<SpaceTest>>() {
            @Override
            public void onChanged(List<SpaceTest> tests) {
                if (tests != null ) {
                    adapter.setTests(tests);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Ошибка загрузки. Проверьте подключение к сети.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                swipeContainer.setRefreshing(false);
            }
        };

        testViewModel = new ViewModelProvider(getActivity())
                .get(TestViewModel.class);
        testViewModel.refresh();
        testViewModel.getTests().observe(getViewLifecycleOwner(), observer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                testViewModel.refresh();
            }
        });

    }

    @Override
    public void onDetach() {
        super.onDetach();
        startListener = null;
    }

    private class TestAdapter extends RecyclerView.Adapter<TestViewHolder> {

        private List<SpaceTest> mTests = new ArrayList<>();
        private OnStartTestFragmentListener startListener;

        TestAdapter(OnStartTestFragmentListener listener) {
            startListener = listener;
        }

        public void setTests(List<SpaceTest> tests) {
            mTests = tests;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public TestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TestViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.tests_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull TestViewHolder holder, int position) {
            final SpaceTest test = mTests.get(position);
            holder.mName.setText(test.getName());
            holder.mDescription.setText(test.getDescription());
            StringBuilder builder = new StringBuilder();
            builder.append(test.getScore()).append('/').append(test.getQuestions().size());
            holder.mScore.setText(builder.toString());
            Glide.with(getContext())
                    .load(test.getImage())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.BLACK))
                    .into(holder.mImage);
            holder.itemView.setOnClickListener(view -> {
                startListener.startQuestionFragment(test);
            });
        }

        @Override
        public int getItemCount() {
            return mTests.size();
        }
    }

    static class TestViewHolder extends RecyclerView.ViewHolder {

        protected TextView mName;
        protected TextView mDescription;
        protected TextView mScore;
        protected ImageView mImage;

        public TestViewHolder(@NonNull View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.test_name);
            mDescription = itemView.findViewById(R.id.test_description);
            mScore = itemView.findViewById(R.id.score);
            mImage = itemView.findViewById(R.id.test_image);
        }
    }
}


