package ru.easyspace.spacelaunch.test;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;


public class SpaceTest implements Parcelable {
    private String name;
    private String image;
    private String description;
    private String successImage;
    private String failureImage;
    private List<String> success;
    private List<String> failure;
    private List<Question> questions;
    private int score;

    public SpaceTest() {}

    public SpaceTest(String name, String image, String description,
                     String successImage, String failureImage,
                     List<String> success, List<String> failure,
                     List<Question> questions, int score) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.successImage = successImage;
        this.failureImage = failureImage;
        this.success = success;
        this.failure = failure;
        this.questions = questions;
        this.score = score;
    }

    protected SpaceTest(Parcel in) {
        name = in.readString();
        image = in.readString();
        description = in.readString();
        successImage = in.readString();
        failureImage = in.readString();
        success = in.createStringArrayList();
        failure = in.createStringArrayList();
        questions = new ArrayList<Question>();
        in.readList(questions, Question.class.getClassLoader());
        score = in.readInt();
    }

    public static final Creator<SpaceTest> CREATOR = new Creator<SpaceTest>() {
        @Override
        public SpaceTest createFromParcel(Parcel in) {
            return new SpaceTest(in);
        }

        @Override
        public SpaceTest[] newArray(int size) {
            return new SpaceTest[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSuccessImage() {
        return successImage;
    }

    public void setSuccessImage(String successImage) {
        this.successImage = successImage;
    }

    public String getFailureImage() {
        return failureImage;
    }

    public void setFailureImage(String failureImage) {
        this.failureImage = failureImage;
    }

    public List<String> getSuccess() {
        return success;
    }

    public void setSuccess(List<String> success) {
        this.success = success;
    }

    public List<String> getFailure() {
        return failure;
    }

    public void setFailure(List<String> failure) {
        this.failure = failure;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(description);
        dest.writeString(successImage);
        dest.writeString(failureImage);
        dest.writeStringList(success);
        dest.writeStringList(failure);
        dest.writeList(questions);
        dest.writeInt(score);
    }
}
