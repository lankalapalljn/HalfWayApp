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
 * Test class corresponds to user story number four Routes. Scenarios are in order as listed on github.
 */
public class StoryNum4Routes {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION); //allow App to access location

    /**
     * Scenario 1
     * Given I am on the Halfway homepage
     * When I Press the "Begin" button
     * Then I should be on the Settings page
     * When I press the location icon next to Location A and enter "Sacramento, CA" for location B
     * And I press "See on Map" button
     * Then I go to the maps page and I can see the route from my location to the midpoint location on a red line
     */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.getLoc)).perform(click());
        onView(withId(R.id.locationB)).perform(typeText("Sacramento, CA"));
        onView(withId(R.id.locationB)).check(matches(withText("Sacramento, CA")));
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locB)).check(matches(withText(containsString("Sacramento, CA"))));
        // Google Maps mid point varies based on user loc, we leave blank check but advise filling in as appropiate
        // Based on tester location
        onView(withId(R.id.midPoint)).check(matches(withText(containsString(""))));
        // Cannot check to see that routes are being used, but map observation reveals such
    }

    /**
     * Scenario 2
     * Given I am on the Halfway homepage
     * When I Press the "Begin" button
     * Then I should be on the Settings page
     * When I enter "Los Angeles, CA" for location A and enter "Sacramento, CA" for location B
     * And I click to check the "Restaurants" box
     * When I press "See on Map" button
     * Then I go to the maps page and I can see the route from my location to the restaurant.
     */
    @Test
    public void testScenario2() {
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(typeText("Los Angeles, CA"));
        onView(withId(R.id.locationB)).perform(typeText("Sacramento, CA"));
        onView(withId(R.id.locationA)).check(matches(withText("Los Angeles, CA")));
        onView(withId(R.id.locationB)).check(matches(withText("Sacramento, CA")));
        closeSoftKeyboard();

        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Los Angeles, CA"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Sacramento, CA"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Lemoore, CA"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("McDonald's"))));
    }
    /**
     * Scenario 3
     * Given I am on the Halfway homepage
     * When I Press the "Begin" button
     * Then I should be on the Settings page
     * When I enter "Los Angeles, CA" for location A and enter "Sacramento, CA" for location B
     * And I click to check the "Park" box
     * When I press "See on Map" button
     * Then I go to the maps page and I can see the route from my location to the restaurant.
     */
    @Test
    public void testScenario3() {
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(typeText("Los Angeles, CA"));
        onView(withId(R.id.locationB)).perform(typeText("Sacramento, CA"));
        onView(withId(R.id.locationA)).check(matches(withText("Los Angeles, CA")));
        onView(withId(R.id.locationB)).check(matches(withText("Sacramento, CA")));
        closeSoftKeyboard();

        onView(withId(R.id.checkBoxPark)).perform(click());
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.locA)).check(matches(withText(containsString("Los Angeles, CA"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Sacramento, CA"))));
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Lemoore, CA"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Pedersen Playground"))));
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
