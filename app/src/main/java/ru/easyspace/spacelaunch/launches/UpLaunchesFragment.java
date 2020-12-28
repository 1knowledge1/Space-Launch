package ru.easyspace.spacelaunch.launches;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import ru.easyspace.spacelaunch.R;
import ru.easyspace.spacelaunch.StartFragmentListener;

import static android.content.Context.ALARM_SERVICE;

public class UpLaunchesFragment extends Fragment {

    private UpLaunchesViewModel upLaunchesViewModel;
    private SwipeRefreshLayout swipeContainer;
    private StartFragmentListener startListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StartFragmentListener) {
            startListener = (StartFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement StartFragmentListener");
        }
    }

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
        final UpLaunchesAdapter adapter = new UpLaunchesAdapter(startListener);
        recycler.setAdapter(adapter);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        Observer<List<UpcomingLaunch>> observer = new Observer<List<UpcomingLaunch>>() {
            @Override
            public void onChanged(List<UpcomingLaunch> upcomingLaunches) {
                if (upcomingLaunches != null ) {
                   // upcomingLaunches.add(0,new UpcomingLaunch());
                    adapter.setLaunches(upcomingLaunches);
                } else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Ошибка загрузки. Проверьте подключение к сети.",
                            Toast.LENGTH_LONG)
                            .show();
                }
                swipeContainer.setRefreshing(false);
            }
        };

        upLaunchesViewModel = new ViewModelProvider(getActivity())
                .get(UpLaunchesViewModel.class);
        upLaunchesViewModel.getFromDatabase();
        upLaunchesViewModel.getLaunches().observe(getViewLifecycleOwner(), observer);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                upLaunchesViewModel.refresh();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        startListener = null;
    }

    private class UpLaunchesAdapter extends RecyclerView.Adapter<UpLaunchesViewHolder> {

        private List<UpcomingLaunch> mLaunches = new ArrayList<>();
        private StartFragmentListener startListener;

        UpLaunchesAdapter(StartFragmentListener listener) {
            startListener = listener;
        }

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
            if (launch.getTitle() != null) {
                holder.mTitle.setText(launch.getTitle());
            } else {
                holder.mTitle.setText(R.string.default_launch_title);
            }
            if (launch.getRocket() != null) {
                holder.mRocket.setText(launch.getRocket());
            } else {
                holder.mRocket.setText(R.string.default_launch_rocket);
            }
            if (launch.getAgency() != null) {
                holder.mAgency.setText(launch.getAgency());
            } else {
                holder.mAgency.setText(R.string.default_launch_agency);
            }
            if (launch.getPad() != null) {
                holder.mPad.setText(launch.getPad());
            } else {
                holder.mPad.setText(R.string.default_launch_pad);
            }
            if (launch.getLocation() != null) {
                holder.mLocation.setText(launch.getLocation());
            } else {
                holder.mLocation.setText(R.string.default_launch_location);
            }
            if (launch.getStartDate() != null) {
                holder.mDate.setText(launch.getStartDate());
            } else {
                holder.mDate.setText(R.string.default_launch_date);
            }
            if (launch.getStartTime() != null) {
                holder.mTime.setText(launch.getStartTime());
            } else {
                holder.mTime.setText(R.string.default_launch_time);
            }
            Glide.with(getContext())
                    .load(launch.getImage())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.BLACK))
                    .into(holder.mImage);
            holder.itemView.setOnClickListener(view -> {
                startListener.startDetailedLaunchFragment(launch);
            });
            holder.mNotificationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!launch.getIsNotificated()) {
                        Calendar calendar = Calendar.getInstance();
                        if(position==0||position==1) {
                            calendar.add(Calendar.SECOND, 5);
                        }else{
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
                            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss' UTC'", Locale.US);
                            try {
                                Date  dt_strt=dateFormat.parse(launch.getStartDate());
                                Date  tm_strt=timeFormat.parse(launch.getStartTime());
                                calendar.setTime(dt_strt);
                                Calendar calendar_time= Calendar.getInstance();
                                calendar_time.setTime(tm_strt);
                                calendar.set(Calendar.HOUR_OF_DAY,calendar_time.get(Calendar.HOUR_OF_DAY));
                                calendar.set(Calendar.MINUTE,calendar_time.get(Calendar.MINUTE));
                                calendar.set(Calendar.SECOND,calendar_time.get(Calendar.SECOND));
                            } catch (ParseException e) {

                                calendar.add(Calendar.SECOND, 5);
                            }
                        }
                        AlarmManager am = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
                        Intent intent = new Intent(getActivity(), UpLaunchesNotifictionReciever.class);
                        intent.putExtra("Title",launch.getTitle());
                        intent.putExtra("Text",launch.getLocation());
                        intent.putExtra("ID",position);
                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), position,
                                intent, PendingIntent.FLAG_CANCEL_CURRENT);
                        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
                        upLaunchesViewModel.updateNotifiction(new ArrayList<>(mLaunches),position,Boolean.TRUE);

                    }else{
                        Intent intent = new Intent(getActivity(), UpLaunchesNotifictionReciever.class);
                        PendingIntent sender = PendingIntent.getBroadcast(getActivity(), position,
                                intent, 0);
                        AlarmManager  am= (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
                        am.cancel(sender);
                        sender.cancel();
                        upLaunchesViewModel.updateNotifiction(new ArrayList<>(mLaunches),position,Boolean.FALSE);
                    }


                }

            });

            if(launch.getIsNotificated()){
                holder.mNotification.check(R.id.notific);
            }else{
                holder.mNotification.uncheck(R.id.notific);
            }
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
        protected MaterialButtonToggleGroup mNotification;
        protected  MaterialButton mNotificationButton;
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
            mNotification=itemView.findViewById(R.id.toggleButtonGroup);
            mNotificationButton=itemView.findViewById(R.id.notific);
        }
    }
}
