package ru.easyspace.spacelaunch;

import ru.easyspace.spacelaunch.launches.UpcomingLaunch;
import ru.easyspace.spacelaunch.test.SpaceTest;

public interface StartFragmentListener {
    void startDetailedLaunchFragment(UpcomingLaunch launch);
    void startQuestionFragment(SpaceTest test);
    void startResultFragment(SpaceTest test, int score);
    void returnTestFragment(String testName, int score);
}
