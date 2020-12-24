package ru.easyspace.spacelaunch.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
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

import java.util.ArrayList;
import java.util.List;

import ru.easyspace.spacelaunch.R;
import ru.easyspace.spacelaunch.StartFragmentListener;


public class QuestionFragment extends Fragment {

    private static final String TEST = "test";

    public static QuestionFragment newInstance(SpaceTest test) {
        QuestionFragment fragment = new QuestionFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(TEST, test);
        fragment.setArguments(bundle);
        return fragment;
    }

    private SpaceTest test;
    private TextView questionText;
    private ImageView image;
    private List<Button> answers = new ArrayList<>(4);
    private List<Integer> colors = new ArrayList<>(4);
    private Button next;
    private int currentQuestion;
    private int maxQuestionNumber;
    private int correctAnswerId;
    private int score = 0;
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_question_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        questionText = (TextView) view.findViewById(R.id.test_question);
        image = (ImageView) view.findViewById(R.id.test_question_image);
        answers.add((Button) view.findViewById(R.id.answer_1));
        answers.add((Button) view.findViewById(R.id.answer_2));
        answers.add((Button) view.findViewById(R.id.answer_3));
        answers.add((Button) view.findViewById(R.id.answer_4));
        for (int i = 0; i < answers.size(); i++) {
            colors.add(Color.GRAY);
        }
        next = (Button) view.findViewById(R.id.next_question);
        Bundle args = getArguments();
        if (args != null) {
            test = args.getParcelable(TEST);
            maxQuestionNumber = test.getQuestions().size();
            currentQuestion = 0;
            initQuestion(test.getQuestions(), currentQuestion);
        }
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < answers.size(); i++) {
                    answers.get(i).setBackgroundColor(colors.get(i));
                    answers.get(i).setClickable(false);
                }
                if (v.getId() != correctAnswerId) {
                    ((Button) v).setBackgroundColor(Color.RED);
                } else {
                    score++;
                }
                next.setVisibility(View.VISIBLE);
                currentQuestion++;
            }
        };
        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).setOnClickListener(listener);
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next.setVisibility(View.INVISIBLE);
                if (currentQuestion < maxQuestionNumber) {
                    initQuestion(test.getQuestions(), currentQuestion);
                } else {
                    startListener.startResultFragment(test, score);
                }
            }
        });
    }

    private void initQuestion(List<Question> questions, int questionNumber) {
        Question question = questions.get(questionNumber);
        questionText.setText(question.getQuestion());
        Glide.with(getContext())
                .load(question.getImage())
                .centerCrop()
                .placeholder(new ColorDrawable(Color.BLACK))
                .into(image);
        answers.get(0).setText(question.getAnswer1());
        answers.get(1).setText(question.getAnswer2());
        answers.get(2).setText(question.getAnswer3());
        answers.get(3).setText(question.getAnswer4());
        TypedValue typedValue = new TypedValue();
        TypedArray a = getContext().obtainStyledAttributes(typedValue.data, new int[] {R.attr.colorPrimary});
        int color = a.getColor(0, 0);
        a.recycle();
        for (int i = 0; i < answers.size(); i++) {
            answers.get(i).setBackgroundColor(color);
            answers.get(i).setClickable(true);
            colors.set(i, Color.GRAY);
        }
        setCorrectAnswerColor(question);
    }

    private void setCorrectAnswerColor(Question question) {
        String correct = question.getRight();
        if (correct.equals(question.getAnswer1())) {
            correctAnswerId = R.id.answer_1;
            colors.set(0, Color.GREEN);
        } else if (correct.equals(question.getAnswer2())) {
            correctAnswerId = R.id.answer_2;
            colors.set(1, Color.GREEN);
        } else if (correct.equals(question.getAnswer3())) {
            correctAnswerId = R.id.answer_3;
            colors.set(2, Color.GREEN);
        } else if (correct.equals(question.getAnswer4())) {
            correctAnswerId = R.id.answer_4;
            colors.set(3, Color.GREEN);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        startListener = null;
    }
}
