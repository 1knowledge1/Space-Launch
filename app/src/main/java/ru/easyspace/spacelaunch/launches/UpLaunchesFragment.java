package ru.easyspace.spacelaunch.launches;

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

public class UpLaunchesFragment extends Fragment {

    private UpLaunchesViewModel upLaunchesViewModel;
    private SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_up_launches, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.up_launches_swipe_container);
        RecyclerView recycler = view.findViewById(R.id.up_launches_recycler);
        final UpLaunchesAdapter adapter = new UpLaunchesAdapter();
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        Observer<List<UpcomingLaunch>> observer = new Observer<List<UpcomingLaunch>>() {
            @Override
            public void onChanged(List<UpcomingLaunch> upcomingLaunches) {
                if (upcomingLaunches != null ) {
                    adapter.setLaunches(upcomingLaunches);
                } else {
                    Toast.makeText(getContext(),
                            "Ошибка загрузки. Проверьте подключение к сети.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                swipeContainer.setRefreshing(false);
            }
        };

        upLaunchesViewModel = new ViewModelProvider(getActivity())
                .get(UpLaunchesViewModel.class);
        upLaunchesViewModel.refresh();
        upLaunchesViewModel.getLaunches().observe(getViewLifecycleOwner(), observer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upLaunchesViewModel.refresh();
            }
        });
    }

    private class UpLaunchesAdapter extends RecyclerView.Adapter<UpLaunchesViewHolder> {

        private List<UpcomingLaunch> mLaunches = new ArrayList<>();

        public void setLaunches(List<UpcomingLaunch> launches) {
            mLaunches = launches;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public UpLaunchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new UpLaunchesViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.up_launch_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull UpLaunchesViewHolder holder, int position) {
            final UpcomingLaunch launch = mLaunches.get(position);
            holder.mTitle.setText(launch.getTitle());
            holder.mRocket.setText(launch.getRocket());
            holder.mAgency.setText(launch.getAgency());
            holder.mPad.setText(launch.getPad());
            holder.mLocation.setText(launch.getLocation());
            holder.mDate.setText(launch.getStartDate());
            holder.mTime.setText(launch.getStartTime());
            Glide.with(getContext())
                    .load(launch.getImage())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.BLACK))
                    .into(holder.mImage);
        }

        @Override
        public int getItemCount() {
            return mLaunches.size();
        }
    }

    static class UpLaunchesViewHolder extends RecyclerView.ViewHolder {

        protected TextView mTitle;
        protected TextView mRocket;
        protected TextView mAgency;
        protected TextView mPad;
        protected TextView mLocation;
        protected TextView mDate;
        protected TextView mTime;
        protected ImageView mImage;

        public UpLaunchesViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.launch_title);
            mRocket = itemView.findViewById(R.id.rocket_name);
            mAgency = itemView.findViewById(R.id.agency_name);
            mPad = itemView.findViewById(R.id.pad_name);
            mLocation = itemView.findViewById(R.id.pad_location);
            mDate = itemView.findViewById(R.id.start_date);
            mTime = itemView.findViewById(R.id.start_time);
            mImage = itemView.findViewById(R.id.launch_image);
        }
    }
}
