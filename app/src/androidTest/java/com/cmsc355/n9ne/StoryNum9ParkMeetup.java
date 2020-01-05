package com.cmsc355.n9ne;

import android.os.SystemClock;

import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test class corresponds to user story number nine Park Meetup. Scenarios are in order as listed on github.
 */
public class StoryNum9ParkMeetup {

    ViewAction setProgress = new StoryNum2GeographicPointTest().setProgress(50);
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestResult = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    /**
     * Given that I am on the start screen
     * When I press the start button
     * Then I will be taken to the settings page
     * When I click the location icon
     * Then my address is automatically generated for the first location
     * When I enter in second address as San Fransisco, CA and I select park
     * And I press the see on map button
     * Then I will be on the maps page showing my location, my friends location, and the closest meeting location that is a park halfway between us
     *
     * Note we cannot check location of tester so we are limited in testing here
     */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.getLoc)).perform(click());
        onView(withId(R.id.locationB)).perform(typeText("San Francisco, CA"));
        onView(withId(R.id.checkBoxPark)).perform(click());
        closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.shareSpecial)).check(matches(isClickable()));
    }

    /**
     * Given that I am on the start screen
     * When I press the start button
     * Then I will be taken to the settings page
     * When I enter san Diego, CA and San Fransisco, CA as my two addresses
     * And I press the see on map button with the park box checked
     * Then I will be on the maps page showing my location, my friends location, and the closest meeting location that is a park halfway between us
     * When I press the settings button
     * Then I will be on the settings page
     * When I enter a new address, Portland, Oregon, for my friend and press find midpoint with the park box still checked
     * Then the maps page will display the new midpoint with a new nearby park
     * When I press the share button next to the park address
     * Then I will be prompted to share that location with my friend
     */
    @Test
    public void testScenario2(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("San Diego, CA"));
        onView(withId(R.id.locationA)).check(matches(withText("San Diego, CA")));
        onView(withId(R.id.locationB)).perform(typeText("San Fransisco, CA"));
        onView(withId(R.id.locationB)).check(matches(withText("San Fransisco, CA")));

        closeSoftKeyboard();
        onView(withId(R.id.checkBoxPark)).perform(click());
        closeSoftKeyboard();
        SystemClock.sleep(1500);
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("McKittrick, CA"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Trailhead to Palette McKittrick"))));
        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.locationB)).perform(clearText());
        onView(withId(R.id.locationB)).perform(replaceText("Portland, OR"));
        onView(withId(R.id.locationB)).check(matches(withText("Portland, OR")));

        //closeSoftKeyboard();
        closeSoftKeyboard();
        SystemClock.sleep(1500);
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Spooner Lake Trail"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Spooner Lake - Lake Tahoe"))));
        onView(withId(R.id.shareSpecial)).perform(click());
    }


    /**
     * Given that I am on the start screen
     * When I press the start button
     * Then I will be taken to the settings page
     * When I enter San Diego, CA and San Fransisco, CA as my two addresses
     * And I press the see on map button with the park box checked
     * Then I will be on the maps page showing my location, my friends location, and the closest meeting location that is a park halfway between us
     * When I press the settings button
     * Then I will be on the settings page
     * When check the checkbox for restaurant and press the find on map button
     * Then the maps page will display a restaurant near our park meetup location that we can go to after visiting the park
     */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("San Diego, CA"));
        onView(withId(R.id.locationA)).check(matches(withText("San Diego, CA")));
        onView(withId(R.id.locationB)).perform(typeText("San Fransisco, CA"));
        onView(withId(R.id.locationB)).check(matches(withText("San Fransisco, CA")));

        closeSoftKeyboard();
        onView(withId(R.id.checkBoxPark)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("McKittrick, CA"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Trailhead to Palette McKittrick"))));
        onView(withId(R.id.goToSettings)).perform(click());

        SystemClock.sleep(1500);
        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        //closeSoftKeyboard();
        closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("McKittrick, CA"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Taco Bell 1121 Kern St"))));
    }
}
