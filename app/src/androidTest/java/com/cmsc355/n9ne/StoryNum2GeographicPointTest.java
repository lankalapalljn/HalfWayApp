package com.cmsc355.n9ne;

import android.Manifest;
import android.view.View;
import android.widget.SeekBar;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Test class corresponds to user story number two GeographicTestPoint. Scenarios are in order as listed on github.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class StoryNum2GeographicPointTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.ACCESS_FINE_LOCATION); //allow App to access location

    /**
     * Scenario 1
     * Given that I am on the start page
     * When I select start
     * Then I will be taken to the settings screen
     * When I manually enter the location of Detroit and Chicago
     * And I do not select the restaurant check box
     * And I press the button to find midpoint
     * Then I will be on the maps screen showing a geographic location (closest address) of a point between Detroit and Chicago
     */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(typeText("Detroit MI"));
        onView(withId(R.id.locationA)).check(matches(withText("Detroit MI")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago IL")));
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("Detroit, MI"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Chicago, IL"))));

        // At this point cannot check map directly using Espresso, but can check displayed addresses
    }

    /**
     * Scenario 2
     * Given that I am on the start page
     * When I select start
     * Then I will be taken to the settings screen
     * When I enter the location for Detroit
     * And manually enter the location of Chicago
     * When I press Confirm
     * Then I will be on the maps screen showing the address of a location halfway between Chicago and Detroit
     * When I press the settings button
     * Then I will be back on the settings page
     * When I clear the address of Chicago
     * And I press find midpoint
     * Then I will be prompted to enter a valid location
     * When I enter another address where the blank entry was
     * And I press confirm
     * Then I will be on the maps page showing the new midpoint
     */
    @Test
    public void testScenario2(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(typeText("Detroit MI"));
        onView(withId(R.id.locationA)).check(matches(withText("Detroit MI")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago IL")));
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("Detroit, MI"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Chicago, IL"))));

        onView(withId(R.id.goToSettings)).perform(click());
        onView(withId(R.id.locationB)).perform(clearText());
        onView(withId(R.id.locationB)).check(matches(withText("")));

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locationB)).perform(typeText("Denver CO"));
        onView(withId(R.id.locationB)).check(matches(withText("Denver CO")));
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("Detroit, MI"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Denver, CO"))));
    }

    /**
     * Given that I am on the start page
     * When I select start
     * Then I will be taken to the settings screen
     * When I manually enter the location of Detroit and Denver
     * And I do not select the restaurant box
     * And I press Confirm
     * Then I will be on the maps screen showing an address halfway between Chicago and Denver
     * When I select the settings button
     * Then I will be taken back to the settings page
     * When I select the restaurant box
     * Then I will be on the maps display page showing the same halfway point with a nearby restaurant (if there is one)
     */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.locationA)).perform(typeText("Detroit MI"));
        onView(withId(R.id.locationA)).check(matches(withText("Detroit MI")));
        onView(withId(R.id.locationB)).perform(typeText("Denver CO"));
        onView(withId(R.id.locationB)).check(matches(withText("Denver CO")));
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("Detroit, MI"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Denver, CO"))));

        // Required to clear text and re-enter due to espresso issues, not represented in story
        // but test fails otherwise due to checkBox not checking
        onView(withId(R.id.goToSettings)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress(50));
        onView(withId(R.id.locationA)).perform(clearText());
        onView(withId(R.id.locationA)).check(matches(withText("")));
        onView(withId(R.id.locationB)).perform(clearText());
        onView(withId(R.id.locationB)).check(matches(withText("")));

        onView(withId(R.id.locationA)).perform(typeText("Detroit MI"));
        onView(withId(R.id.locationA)).check(matches(withText("Detroit MI")));
        onView(withId(R.id.locationB)).perform(typeText("Denver CO"));
        onView(withId(R.id.locationB)).check(matches(withText("Denver CO")));
        closeSoftKeyboard();
        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        // Sometimes espresso doesnt click 1st time, seemingly random
        //onView(withId(R.id.checkBoxRestaurant)).perform(click());
        onView(withId(R.id.checkBoxRestaurant)).check(matches(isChecked()));

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.locA)).check(matches(withText(containsString("Detroit, MI"))));
        onView(withId(R.id.locB)).check(matches(withText(containsString("Denver, CO"))));

        // Restaurant near midpoint
        onView(withId(R.id.specialLocation)).check(matches(withText(containsString("Casey's 702"))));

        // Map displays restaurant on map, check what address should be via text box
    }

    /**
     * Method to allow the progress bar to set to the halfway point.
     * @param progress integer value showing percent towards a or b
     * @return view update with percentage
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
