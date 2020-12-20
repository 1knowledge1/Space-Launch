package ru.easyspace.spacelaunch.test;

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

import java.util.List;

import ru.easyspace.spacelaunch.R;


public class QuestionFragment extends Fragment {

    private static final String TEST = "test";

    public static QuestionFragment newInstance(SpaceTest test) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEST, test);
        fragment.setArguments(bundle);
        return fragment;
    }

    private TextView questionText;
    private ImageView image;
    private Button answer1;
    private Button answer2;
    private Button answer3;
    private Button answer4;
    private Button next;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_question_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionText = (TextView) view.findViewById(R.id.test_question);
        image = (ImageView) view.findViewById(R.id.test_question_image);
        answer1 = (Button) view.findViewById(R.id.answer_1);
        answer2 = (Button) view.findViewById(R.id.answer_2);
        answer3 = (Button) view.findViewById(R.id.answer_3);
        answer4 = (Button) view.findViewById(R.id.answer_4);
        next = (Button) view.findViewById(R.id.next_question);
        Bundle args = getArguments();
        if (args != null) {
            SpaceTest test = args.getParcelable(TEST);
            initQuestion(view, test.getQuestions(), 0);

        }
    }

    private void initQuestion(@NonNull View view, List<Question> questions, int questionNumber) {
        Question question = questions.get(questionNumber);
        questionText.setText(question.getQuestion());
        Glide.with(getContext())
                .load(question.getImage())
                .centerCrop()
                .placeholder(new ColorDrawable(Color.BLACK))
                .into(image);
        answer1.setText(question.getAnswer1());
        answer2.setText(question.getAnswer2());
        answer3.setText(question.getAnswer3());
        answer4.setText(question.getAnswer4());
    }
}
