package ru.easyspace.spacelaunch.test;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;

import ru.easyspace.spacelaunch.R;
import ru.easyspace.spacelaunch.StartFragmentListener;

public class ResultFragment extends Fragment {

    private static final String TEST = "test";
    private static final String SCORE = "score";

    public static ResultFragment newInstance(SpaceTest test, int score) {
        ResultFragment fragment = new ResultFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEST, test);
        bundle.putInt(SCORE, score);
        fragment.setArguments(bundle);
        return fragment;
    }

    private SpaceTest test;
    private int score = 0;
    private StartFragmentListener startListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof StartFragmentListener) {
            startListener = (StartFragmentListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement OnReturnTestFragmentListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_result, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            test = args.getParcelable(TEST);
            score = args.getInt(SCORE);
            int maxScore = test.getQuestions().size();
            StringBuilder builder = new StringBuilder();
            builder.append(score).append('/').append(maxScore);
            ((TextView) view.findViewById(R.id.test_score)).setText(builder.toString());

            if (score >= 0.7 * maxScore) {
                initViews(view, test.getSuccess().get(0), test.getSuccess().get(1), test.getSuccessImage());
            } else {
                initViews(view, test.getFailure().get(0), test.getFailure().get(1), test.getFailureImage());
            }
        }
        ((Button) view.findViewById(R.id.test_end)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (test != null) {
                    startListener.returnTestFragment(test.getName(), score);
                } else {
                    startListener.returnTestFragment(null, score);
                }
            }
        });
    }

    private void initViews(@NonNull View view, String result, String description, String image) {
        ((TextView) view.findViewById(R.id.test_result)).setText(result);
        ((TextView) view.findViewById(R.id.test_result_description)).setText(description);
        Glide.with(getContext())
                .load(image)
                .centerCrop()
                .placeholder(new ColorDrawable(Color.BLACK))
                .into((ImageView) view.findViewById(R.id.test_result_image));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        startListener = null;
    }
}
