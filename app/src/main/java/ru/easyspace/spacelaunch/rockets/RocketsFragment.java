package ru.easyspace.spacelaunch.rockets;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import ru.easyspace.spacelaunch.rockets.rocketLibraryAPI.RocketJSON;

public class RocketsFragment extends Fragment {
    RocketsViewModel mViewModel;
    SwipeRefreshLayout swipeContainer;
    EditText mSearchBar;
    String savedSearchedText;
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            savedSearchedText=savedInstanceState.getString("SearchText");
        }
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_rockets, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.rockets_swipe_container);
        RecyclerView recycler = view.findViewById(R.id.rockets_recycler);
        RocketsAdapter rocketsAdapter = new RocketsAdapter();
        recycler.setAdapter(rocketsAdapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewModel = new ViewModelProvider(getActivity())
                .get(RocketsViewModel.class);
        mViewModel.getRockets().observe(getViewLifecycleOwner(), new Observer<List<RocketJSON>>() {
            @Override
            public void onChanged(List<RocketJSON> rocketJSON) {
                if (rocketJSON!= null) {
                    rocketsAdapter.setRockets(rocketJSON);
                }else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Ошибка загрузки. Проверьте подключение к сети.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                swipeContainer.setRefreshing(false);
            }
        });
        if (mViewModel.getRockets().getValue() == null) {
            mViewModel.updateRockets();

        }
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.updateRocketsfromNetwork();
                

            }
        });
        mSearchBar=(EditText) view.findViewById(R.id.search);
        mSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String SearchText=s.toString();
                mViewModel.updateRocketsSearch(SearchText);
            }

            @Override
            public void afterTextChanged(Editable s) {


            }
        });
        mSearchBar.setText(savedSearchedText);
    }
    private class RocketsAdapter extends RecyclerView.Adapter<RocketsFragment.RocketsViewHolder> {
        private List<RocketJSON> mRockets = new ArrayList<>();
        public void setRockets(List<RocketJSON> rockets) {
            mRockets=rockets;
            notifyDataSetChanged();
        }
        @NonNull
        @Override
        public RocketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new RocketsFragment.RocketsViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.rocket_item, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull RocketsViewHolder holder, int position) {
             RocketJSON rocket=mRockets.get(position);
             holder.mTitle.setText(rocket.launcher_config.name);
             holder.mFamily.setText(rocket.launcher_config.family);
             holder.mSerialNumber.setText(rocket.serial_number);
             holder.mDetails.setText(rocket.details);
             holder.mFirstLaunch.setText(rocket.first_launch_date);
             holder.mLastLaunch.setText(rocket.last_launch_date);
            Glide.with(getContext())
                    .load(rocket.image_url)
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.BLACK))
                    .into(holder.mImage);

        }

        @Override
        public int getItemCount() {
            return mRockets.size();
        }
    }
    static class RocketsViewHolder extends RecyclerView.ViewHolder {
        protected TextView mTitle;
        protected TextView mFamily;
        protected TextView mSerialNumber;
        protected TextView mDetails;
        protected TextView mLastLaunch;
        protected TextView mFirstLaunch;
        protected ImageView mImage;
        public RocketsViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.rocket_title);
            mFamily = itemView.findViewById(R.id.rocket_family);
            mSerialNumber = itemView.findViewById(R.id.rocket_serial);
            mDetails= itemView.findViewById(R.id.rocket_details);
            mFirstLaunch= itemView.findViewById(R.id.rocket_first_launch_date);
            mLastLaunch = itemView.findViewById(R.id.rocket_last_launch_date);
            mImage = itemView.findViewById(R.id.rocket_image);
        }
    }
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("SearchText",mSearchBar.getText().toString());
    }
}