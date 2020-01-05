package com.cmsc355.n9ne;


import android.os.SystemClock;

import androidx.test.espresso.ViewAction;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.core.StringContains;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

/**
 * Test class corresponds to user story number seven Road Trip. Scenarios are in order as listed on github.
 */
@RunWith(AndroidJUnit4.class)
public class StoryNum7RoadTrip {

    ViewAction setProgress = new StoryNum2GeographicPointTest().setProgress(50);
    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestResult = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);


    /**
     *  Given I am on the Halfway home page
     * And I press the start button
     * Then I will be on the settings page
     * When I fill in the the text entry boxes with my start location and first destination location
     * And I press the find midpoint button and check the Restaurant box
     * Then the midpoint between both locations and a nearby restaurant should appear where I can stop to eat halfway on my trip
     * When I press the settings button
     * Then I will be on the settings page
     * When I enter a new address replacing my starting address and press find midpoint with the gas station box checked
     * Then the maps page will display a gas station halfway between my last two stops
     */
    @Test
    public void testScenario1() {
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("New York City, NY"));
        onView(withId(R.id.locationA)).check(matches(withText("New York City, NY")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago, IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago, IL")));

        closeSoftKeyboard();
        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("5166 Pierce Rd"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Taco Bell"))));
        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(clearText());
        onView(withId(R.id.locationA)).perform(replaceText("Denver, CO"));
        onView(withId(R.id.locationA)).check(matches(withText("Denver, CO")));

        //closeSoftKeyboard();
        SystemClock.sleep(1500);
        onView(withId(R.id.checkBoxGasStation)).perform(click());
        //closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("439 S 286"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Midwest Farmers Cooperative"))));
    }

    /**
     * Given I am on the Halfway home page
     * And I press the start button
     * Then I will be on the settings page
     * When I fill in the the text entry boxes with my start location and first destination location
     * And I press the find midpoint button and check the Restaurant box
     * Then the midpoint between both locations and a nearby restaurant should appear where I can stop to eat halfway on my trip
     * When I press the settings button
     * Then I will be on the settings page
     * When I enter a new address replacing my starting address and press find midpoint with the gas station box checked
     * Then the maps page will display a gas station halfway between my last two stops
     * When I press the settings button
     * Then I will be on the settings page
     * When I enter a new address replacing my old end address and press find midpoint with the restaurant box checked
     * Then I will be on the settings page showing a final stopping point on my road trip
     */
    @Test
    public void testScenario2() {
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("New York City, NY"));
        onView(withId(R.id.locationA)).check(matches(withText("New York City, NY")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago, IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago, IL")));

        closeSoftKeyboard();
        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("5166 Pierce Rd"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Taco Bell"))));
        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(clearText());
        onView(withId(R.id.locationA)).perform(replaceText("Denver, CO"));
        onView(withId(R.id.locationA)).check(matches(withText("Denver, CO")));

        //closeSoftKeyboard();
        SystemClock.sleep(1500);
        onView(withId(R.id.checkBoxGasStation)).perform(click());
        //closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("439 S 286"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Midwest Farmers Cooperative"))));

        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationB)).perform(clearText());
        onView(withId(R.id.locationB)).perform(replaceText("San Diego, CA"));
        onView(withId(R.id.locationB)).check(matches(withText("San Diego, CA")));

        SystemClock.sleep(1500);
        onView(withId(R.id.checkBoxRestaurant)).perform(click());

        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("Tuba City, AZ"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Pizza Edge"))));

    }


    /**
     * Scenario 3
     * Given I am on the Halfway home page
     * And I press the start button
     * Then I will be on the settings page
     * When I fill in the the text entry boxes with my start location and first destination location
     * And I press the find midpoint button and check the gas station box
     * Then a gas station halfway between my start and stop point will be displayed so I can stop for gas on my trip
     * When I press the settings button
     * Then I will be on the settings page
     * When I enter a new address replacing my old start address and press find midpoint with the restaurant box checked
     * Then I will be on the maps page showing a suitable restaurant along my trip's route
     * When I press the share button next to the restaurant address on the maps page
     * Then I will be prompted to share the restaurant with a nearby friend who I want to meet
     */
    @Test
    public void testScenario3() {
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("New York City, NY"));
        onView(withId(R.id.locationA)).check(matches(withText("New York City, NY")));
        onView(withId(R.id.locationB)).perform(typeText("Chicago, IL"));
        onView(withId(R.id.locationB)).check(matches(withText("Chicago, IL")));

        closeSoftKeyboard();
        onView(withId(R.id.checkBoxGasStation)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("5166 Pierce Rd"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Kwik Fill"))));
        onView(withId(R.id.goToSettings)).perform(click());

        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(clearText());
        onView(withId(R.id.locationA)).perform(replaceText("Denver, CO"));
        onView(withId(R.id.locationA)).check(matches(withText("Denver, CO")));

        //closeSoftKeyboard();
        SystemClock.sleep(1500);
        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        //closeSoftKeyboard();
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("439 S 286"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("The Quonset Bar And Grill"))));

        onView(withId(R.id.shareSpecial)).perform(click());
    }
}
