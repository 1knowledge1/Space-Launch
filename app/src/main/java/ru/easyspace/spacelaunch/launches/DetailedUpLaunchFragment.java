package ru.easyspace.spacelaunch.launches;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import ru.easyspace.spacelaunch.R;

public class DetailedUpLaunchFragment extends Fragment {
    private static final String LAUNCH = "launch";

    public static DetailedUpLaunchFragment newInstance(UpcomingLaunch launch) {
        DetailedUpLaunchFragment fragment = new DetailedUpLaunchFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(LAUNCH, launch);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.up_launch_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            UpcomingLaunch launch = args.getParcelable(LAUNCH);
            if (launch.getTitle() != null) {
                ((TextView) view.findViewById(R.id.launch_detail_title)).setText(launch.getTitle());
            } else {
                ((TextView) view.findViewById(R.id.launch_detail_title)).setText(R.string.default_launch_title);
            }
            if (launch.getRocket() != null) {
                ((TextView) view.findViewById(R.id.detail_rocket_name)).setText(launch.getRocket());
            } else {
                ((TextView) view.findViewById(R.id.detail_rocket_name)).setText(R.string.default_launch_rocket);
            }
            if (launch.getAgency() != null) {
                ((TextView) view.findViewById(R.id.detail_agency_name)).setText(launch.getAgency());
            } else {
                ((TextView) view.findViewById(R.id.detail_agency_name)).setText(R.string.default_launch_agency);
            }
            if (launch.getPad() != null) {
                ((TextView) view.findViewById(R.id.detail_pad_name)).setText(launch.getPad());
            } else {
                ((TextView) view.findViewById(R.id.detail_pad_name)).setText(R.string.default_launch_pad);
            }
            if (launch.getLocation() != null) {
                ((TextView) view.findViewById(R.id.detail_pad_location)).setText(launch.getLocation());
            } else {
                ((TextView) view.findViewById(R.id.detail_pad_location)).setText(R.string.default_launch_location);
            }
            if (launch.getStartDate() != null) {
                ((TextView) view.findViewById(R.id.detail_start_date)).setText(launch.getStartDate());
            } else {
                ((TextView) view.findViewById(R.id.detail_start_date)).setText(R.string.default_launch_date);
            }
            if (launch.getStartTime() != null) {
                ((TextView) view.findViewById(R.id.detail_start_time)).setText(launch.getStartTime());
            } else {
                ((TextView) view.findViewById(R.id.detail_start_time)).setText(R.string.default_launch_time);
            }
            if (launch.getDescription() != null) {
                ((TextView) view.findViewById(R.id.launch_detail_description)).setText(launch.getDescription());
            } else {
                ((TextView) view.findViewById(R.id.launch_detail_description)).setText(R.string.default_launch_title);
            }
            Glide.with(getContext())
                    .load(launch.getImage())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.BLACK))
                    .into((ImageView) view.findViewById(R.id.launch_detail_image));
            Glide.with(getContext())
                    .load(launch.getMapImage())
                    .centerCrop()
                    .placeholder(new ColorDrawable(Color.BLACK))
                    .into((ImageView) view.findViewById(R.id.launch_detail_location_image));
        }
    }
}
