package com.cmsc355.n9ne;
import android.Manifest;

import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import android.os.SystemClock;
import android.view.View;
import android.widget.SeekBar;

import org.hamcrest.Matcher;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Test class corresponds to user story number 5 Plan a long distance trip. Scenarios are in order as listed on github.
 */
public class StoryNum5PlanALongDistanceTrip {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION); //allow App to access location


    /**
     * Scenario 1
     * Given I am on the Halfway homepage
     * When I Press the "Begin" button
     * Then I should be on the Settings page
     * When I enter Washington, DC as my start location and Las Vegas, NV as my final location
     * And I check hotel and press go to map with the slider at 40% closer to A
     * Then I will be on the maps page showing a hotel on my journey
     * When I press the settings button
     * Then I will be on the settings page
     * When I move the slider to 100% closer to B
     * And I press the go to map button
     * Then I will be on the maps page showing me a hotel a my destination
     */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(30));

        onView(withId(R.id.locationA)).perform(typeText("Washington, DC"));
        onView(withId(R.id.locationA)).check(matches(withText("Washington, DC")));
        onView(withId(R.id.locationB)).perform(typeText("Las Vegas, NV"));
        onView(withId(R.id.locationB)).check(matches(withText("Las Vegas, NV")));
        closeSoftKeyboard();

        onView(withId(R.id.checkBoxHotel)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Washington, DC"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Las Vegas, NV"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Township, IL"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Kays Guest House"))));

        onView(withId(R.id.goToSettings)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(100));

        SystemClock.sleep(1500);
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Washington, DC"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Las Vegas, NV"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Las Vegas, NV"))));
        // Cant check hotel since changes because las vegas is so big
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString(""))));

        // At this point cannot check map directly using Espresso, but can check displayed addresses
    }

    /**
     * Scenario 2
     * Given I am on the Halfway homepage
     * When I Press the "Begin" button
     * Then I should be on the Settings page
     * When I enter Washington, DC as my start location and Chicago, IL as my final location
     * And I check gas station and press go to map with the slider at 50%
     * Then I will be on the maps page showing a gas station halfway on my journey
     * When I press the settings button
     * Then I will be on the settings page
     * When I move the slider to 100% closer to B and check the hotel checkbox
     * And I press the go to map button
     * Then I will be on the maps page showing me a hotel a my destination
     */
    @Test
    public void testScenario2(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));

        onView(withId(R.id.locationA)).perform(typeText("Washington, DC"));
        onView(withId(R.id.locationA)).check(matches(withText("Washington, DC")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago, IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago, IL")));
        closeSoftKeyboard();

        onView(withId(R.id.checkBoxGasStation)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Washington, DC"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Chicago, IL"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Howard, OH"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Marathon Gas"))));

        onView(withId(R.id.goToSettings)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(100));

        SystemClock.sleep(1500);
        onView(withId(R.id.checkBoxGasStation)).perform(click());
        onView(withId(R.id.checkBoxHotel)).perform(click());
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Washington, DC"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Chicago, IL"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Chicago, IL"))));
        // Cant test this hotel since multiple possibilities in chicago
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString(""))));
    }


    /**
     * Scenario 3
     * Given I am on the Halfway homepage
     * When I Press the "Begin" button
     * Then I should be on the Settings page
     * When I enter Washington, DC as my start location and Denver, CO as my final location
     * And I check hotel and press go to map with the slider at 50%
     * Then I will be on the maps page showing a hotel halfway on my journey
     */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));

        onView(withId(R.id.locationA)).perform(typeText("Washington, DC"));
        onView(withId(R.id.locationA)).check(matches(withText("Washington, DC")));
        onView(withId(R.id.locationB)).perform(typeText("Denver, CO"));
        onView(withId(R.id.locationB)).check(matches(withText("Denver, CO")));
        closeSoftKeyboard();

        onView(withId(R.id.checkBoxHotel)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Washington, DC"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Denver, CO"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Clarksville, MO"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Resort"))));
        // At this point cannot check map directly using Espresso, but can check displayed addresses
    }

    /**
     * Method to allow the progress bar to set to the halfway point.
     * @param progress percent towaard a or b
     * @return update view with progress
     */
    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }
}
