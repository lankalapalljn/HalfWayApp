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
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Test class corresponds to user story number one Meet Closer to Me. Scenarios are in order as listed on github.
 */
public class StoryNum1MeetCloserToMe {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION); //allow App to access location

    /**
     * Scenario 1
     * Given I am on the start screen
     * When I press start
     * Then I will be on the settings page
     * When I enter my location as Detriot MI and my friends location as Chicago IL,
     * And select Restaurant and slide the slider 25% closer to me
     * When I press confirm
     * Then I will be on the maps page showing the location as specified
     */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(25));
        onView(withId(R.id.locationA)).perform(typeText("Detroit MI"));
        onView(withId(R.id.locationA)).check(matches(withText("Detroit MI")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago IL")));
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("Detroit, MI"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Chicago, IL"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("10977 Phal Rd, Grass"))));

        // At this point cannot check map directly using Espresso, but can check displayed addresses
    }

    /**
     * Scenario 2
     * Given I am on the start screen
     * When I press Start
     * Then I will be on the settings page
     * When I enter my location as Denver CO
     * And enter my friends location as Chicago IL
     * And select Restaurant and slide the slider 25% closer to me
     * When I press confirm
     * Then I will be on the maps page showing the best restaurant meeting location
     */
    @Test
    public void testScenario2(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(25));

        onView(withId(R.id.locationA)).perform(typeText("Denver CO"));
        onView(withId(R.id.locationA)).check(matches(withText("Denver CO")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago IL")));
        closeSoftKeyboard();

        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Denver, CO"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Chicago, IL"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("72052 US-83, McCook"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Pizza Hut"))));
        // At this point cannot check map directly using Espresso, but can check displayed addresses
    }


    /**
     * Scenario 3
     * Given I am on the start screen
     * When I press start
     * Then I will be on the settings page
     * When I enter my location as Richmond, VA and my friends location as Virginia Beach, VA
     * And select Restaurant and slide the slider 25% closer to me
     * When I press confirm
     * Then I will be on the maps page showing the best meeting location
     * When I press settings
     * Then I will be on the settings page
     * When I enter a new location for a different friend as Charlottesville, VA and press Confirm
     * Then I will be on the maps page showing the new location
     * When I press the share location button next to the displayed Restaurant name
     * Then I will be prompted to share that address
     */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(25));

        onView(withId(R.id.locationA)).perform(typeText("Richmond, VA"));
        onView(withId(R.id.locationA)).check(matches(withText("Richmond, VA")));
        onView(withId(R.id.locationB)).perform(typeText("Virginia Beach, VA"));
        onView(withId(R.id.locationB)).check(matches(withText("Virginia Beach, VA")));
        closeSoftKeyboard();

        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Richmond, VA"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Virginia Beach, VA"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("8700 VA-155, Providence"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Culs Courthouse Grille"))));

        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.locationB)).perform(clearText());
        onView(withId(R.id.locationB)).perform(typeText("Charlottesville, VA"));
        onView(withId(R.id.locationB)).check(matches(withText("Charlottesville, VA")));
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Richmond, VA"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Charlottesville, VA"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("1522 Hermitage Rd"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Lola's Farmhouse Bistro 1840"))));

        onView(withId(R.id.shareSpecial)).perform(click());
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
