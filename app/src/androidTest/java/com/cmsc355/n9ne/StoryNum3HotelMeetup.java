package com.cmsc355.n9ne;

import android.Manifest;

import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

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
 * Test class corresponds to user story number three Hotel Meetup. Scenarios are in order as listed on github.
 */
public class StoryNum3HotelMeetup {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION); //allow App to access location

    /**
     * Scenario 1
     * Given that I am on the start screen
     * When I press the start button
     * Then I will be taken to the settings page
     * When I enter Tacoma, WA as my location and I enter Seattle, WA as my friends location
     * And I set the halfway slider to 50% and I press confirm
     * Then I will be on the maps page showing a hotel halfway between me and my friend
     */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(typeText("Tacoma, WA"));
        onView(withId(R.id.locationA)).check(matches(withText("Tacoma, WA")));
        onView(withId(R.id.locationB)).perform(typeText("Seattle, WA"));
        onView(withId(R.id.locationB)).check(matches(withText("Seattle, WA")));
        closeSoftKeyboard();
        onView(withId(R.id.checkBoxHotel)).perform(click());

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("Tacoma, WA"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Seattle, WA"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Normandy Park"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Three Tree Point Bed & Breakfast"))));

        // At this point cannot check map directly using Espresso, but can check displayed addresses
    }

    /**
     * Scenario 2
     * Given that I am on the start screen
     * When I press the start button
     * Then I will be taken to the settings page
     * When I enter New York City as my location and I enter Akron, OH as my friends location
     * And I set the halfway slider to 50% and I press confirm
     * Then I will be on the maps page showing a hotel halfway between me and my friend
     * When I press the share button next to the hotel
     * Then I will be prompted to share the location with my friend
     */
    @Test
    public void testScenario2(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(typeText("New York City"));
        onView(withId(R.id.locationA)).check(matches(withText("New York City")));
        onView(withId(R.id.locationB)).perform(typeText("Akron, OH"));
        onView(withId(R.id.locationB)).check(matches(withText("Akron, OH")));
        closeSoftKeyboard();
        onView(withId(R.id.checkBoxHotel)).perform(click());

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("New York, NY"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Akron, OH"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("100 Hickory Hills Ln"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Econo Lodge"))));

        onView(withId(R.id.shareSpecial)).perform(click());

        // At this point cannot check map directly using Espresso, but can check displayed addresses
        // At this point cannot check map directly using Espresso, but can check displayed addresses
    }

    /**
     * Scenario 3
     * Given that I am on the start screen
     * When I press the start button
     * Then I will be taken to the settings page
     * When I enter New York City as my location and I enter Akron, OH as my friends location
     * And I set the halfway slider to 50% and I press confirm
     * Then I will be on the maps page showing a hotel halfway between me and my friend
     * When I press the settings button
     * Then I will be back on the settings page
     * When I then select the restaurant check box and I press see on map
     * Then I will be on the maps page showing a restaurant near the hotel we will stay at
     */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(typeText("New York City"));
        onView(withId(R.id.locationA)).check(matches(withText("New York City")));
        onView(withId(R.id.locationB)).perform(typeText("Akron, OH"));
        onView(withId(R.id.locationB)).check(matches(withText("Akron, OH")));
        closeSoftKeyboard();
        onView(withId(R.id.checkBoxHotel)).perform(click());

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("New York, NY"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Akron, OH"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("100 Hickory Hills Ln"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Econo Lodge"))));

        onView(withId(R.id.goToSettings)).perform(click());
        onView(withId(R.id.checkBoxHotel)).perform(click());
        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("New York, NY"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Akron, OH"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("100 Hickory Hills Ln"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Burger King"))));

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
