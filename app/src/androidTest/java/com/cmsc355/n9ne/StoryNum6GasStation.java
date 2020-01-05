package com.cmsc355.n9ne;


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
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * Test class corresponds to user story number six Gas Station. Scenarios are in order as listed on github.
 */
@RunWith(AndroidJUnit4.class)
public class StoryNum6GasStation {

    ViewAction setProgress = new StoryNum2GeographicPointTest().setProgress(50);

    @Rule
    public ActivityTestRule<MainActivity> mainActivityTestResult = new ActivityTestRule<>(MainActivity.class);
    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    /**
     * Given I am on the home page of the Halfway app
     * When I press the 'start' button
     * Then I should be redirected to the 'settings' page
     * When I input the address for Chicago and Detroit in the two input locations
     * And When I select the 'Gas Station' setting
     * When I press the 'confirm' button
     * Then I should be redirected to the Map/Display page And the Halfway Point should be displayed on the map with a toast saying no Special Location.
     */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("Chicago, IL"));
        onView(withId(R.id.locationA)).check(matches(withText("Chicago, IL")));
        onView(withId(R.id.locationB)).perform(typeText("Detroit, MI"));
        onView(withId(R.id.locationB)).check(matches(withText("Detroit, MI")));
        onView(withId(R.id.checkBoxGasStation)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(containsString("Fulton, MI 49052, USA"))));
    }

    /**
     * Given I am on the home page of the Halfway app
     * When I press the 'start' button
     * Then I should be redirected to the 'settings' page
     * When I input the Address Richmond, VA and Short Pump, VA and select 'Gas Station'setting and click 'see on map'
     * Then I should be redirected tot he Map/Display page and the Halfway Point should be displayed on the map
     * When I select the 'share' button next to the address of the gas station that I was generated
     * Then I should be able to share the gas station address it through text to my friend.
     */
    @Test
    public void testScenario2 (){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("Richmond, VA"));
        onView(withId(R.id.locationA)).check(matches(withText("Richmond, VA")));
        onView(withId(R.id.locationB)).perform(typeText("Short Pump, VA"));
        onView(withId(R.id.locationB)).check(matches(withText("Short Pump, VA")));
        onView(withId(R.id.checkBoxGasStation)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.midPoint)).check(matches(withText(StringContains.containsString("7209 Brigham Rd, Richmond"))));
        onView(withId(R.id.specialLocation)).check(matches(withText(StringContains.containsString("Sheetz"))));

        onView(withId(R.id.shareSpecial)).perform(click());
    }

    /**
     * Given I am on the home page of the Halfway app
     * When I press the 'start' button
     * Then I should be redirected to the 'settings' page
     * When I select the icon next to Location A
     * Then the address should automatically enter my current location
     * When I input the address San Francisco, CA in Location B and select 'Gas Station' setting and click 'see on map'
     * Then I should be redirected to the Map/Display page and the Halfway Point should be displayed on the map
     * When I select the share button next to the address of the gas station
     * Then I should be able to share the location through text.
     *
     * Note we are limited in testing the testers location when using location
     */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.getLoc)).perform(click());
        onView(withId(R.id.locationB)).perform(typeText("San Francisco, CA"));
        onView(withId(R.id.checkBoxGasStation)).perform(click());
        closeSoftKeyboard();

        onView(withId(R.id.gotoMap)).perform(click());
        onView(withId(R.id.shareSpecial)).check(matches(isClickable()));

    }
}
