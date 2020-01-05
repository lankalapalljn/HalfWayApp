package com.cmsc355.n9ne;

import android.os.SystemClock;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringContains.containsString;

/**
 * Test class corresponds to user story number 10 Finding Restaurant Near Midway Point. Scenarios are in order as listed on github.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class StoryNum10FindingRestaurantTest {

    ViewAction setProgress = new StoryNum2GeographicPointTest().setProgress(50);
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestResult = new ActivityTestRule<>(MainActivity.class);

    public ActivityTestRule<MapsActivity> mapActivityTestResult = new ActivityTestRule<>(MapsActivity.class);

    /**
     * Given I am on the Halfway home page
     * And I press the start button
     * Then I should be on the settings page
     * When I fill in the the text entry boxes with two locations of interest
     * And I press the find midpoint button and check the Restaurant box
     * Then the midpoint between both locations and a nearby restaurant should appear
     */
    @Test
    public void testScenario1(){
         // Note that it is not actually possible to check the markers on google maps!!!!!
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(click());
        onView(withId(R.id.locationA)).perform(typeText("Richmond Virginia"));
        onView(withId(R.id.locationB)).perform(typeText("Kansas"));
        onView(withId(R.id.checkBoxRestaurant)).perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(containsString("County Rd 200 E, Grayville"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(containsString("Mimmos"))));
    }

    /**
     * Given I am on the Halfway home page
     * And I press the start button
     * Then I should be on the settings page
     * When I fill in the the text entry boxes with two locations of interest
     * And I press the find midpoint button and check the Restaurant box
     * Then the midpoint between both locations and a nearby restaurant should appear
     * When I press the settings button
     * Then I will be on the settings page
     * When I enter a two new addresses and press find midpoint with the restaurant box checked
     * Then the maps page will display the new midpoint with a new nearby restaurant
     */
    @Test
    public void testScenario2(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(click());
        onView(withId(R.id.locationA)).perform(typeText("The Sunken Well Tavern Fredericksburg"));
        onView(withId(R.id.locationB)).perform(typeText("Fredericksburg train station"));

        onView(withId(R.id.checkBoxRestaurant)).perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(containsString("622 Kenmore Ave"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(containsString("Grapevine"))));
        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.locationA)).perform(replaceText("Mount Ranier Washington"));
        onView(withId(R.id.locationB)).perform(replaceText("Portland Oregon"));

        SystemClock.sleep(1500);
        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(containsString("Cougar, WA"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(containsString("Cougar Bar"))));

    }

    /**
     * Given I am on the Halfway home page
     * And I press the start button
     * Then I should be on the settings page
     * When I fill in the the text entry boxes with two locations of interest
     * And I press the find midpoint button and check the Restaurant box
     * Then the midpoint between both locations and a nearby restaurant should appear
     * When I press the settings button
     * Then I will be on the settings page
     * When I enter a two new addresses and press find midpoint with the restaurant box checked
     * Then the maps page will display the new midpoint with a new nearby restaurant
     * When I press the share button
     * Then I will be prompted to share the midpoint
     */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(click());
        onView(withId(R.id.locationA)).perform(typeText("The Sunken Well Tavern Fredericksburg"));
        onView(withId(R.id.locationB)).perform(typeText("Fredericksburg train station"));

        onView(withId(R.id.checkBoxRestaurant)).perform(click());

        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(containsString("622 Kenmore Ave"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(containsString("Grapevine"))));
        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.locationA)).perform(replaceText("Mount Ranier Washington"));
        onView(withId(R.id.locationB)).perform(replaceText("Portland Oregon"));

        SystemClock.sleep(1500);
        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(containsString("Cougar, WA"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(containsString("Cougar Bar"))));

        // check to see if we can click the share button
        Espresso.onView(withId(R.id.share)).check(matches(isClickable()));
    }
}
