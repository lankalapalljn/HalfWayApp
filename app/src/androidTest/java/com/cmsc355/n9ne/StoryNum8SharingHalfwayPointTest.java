package com.cmsc355.n9ne;


import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.ViewAction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

/**
 * Test class corresponds to user story number eight Sharing Halfway Point. Scenarios are in order as listed on github.
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class    StoryNum8SharingHalfwayPointTest {

    ViewAction setProgress = new StoryNum2GeographicPointTest().setProgress(50);
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(android.Manifest.permission.ACCESS_FINE_LOCATION);

    /*
    * Scenario 1
    * Given I am on the Halfway home page
    * When I select the start button
    * Then I will be on the settings page
    * When I enter Chicago in a text box and Detroit in the other text box
    * And I press find midpoint
    * Then I will be on the maps page showing the new midpoint.
    * When I select the "share_pic"
    * Then a pop up should come up allowing me to text the address of the midpoint to my friend
    */
    @Test
    public void testScenario1(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.locationA)).perform(typeText("Chicago, IL")).perform(closeSoftKeyboard());
        onView(withId(R.id.locationB)).perform(typeText("Detroit, MI")).perform(closeSoftKeyboard());
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.share)).check(matches(isClickable())); // Do not understand how to properly test bottomsheet
        // so just checking if the share button is clickable and a bottomsheet is apppearing to share
    }

    /*
    * Scenario 2
    * Given I am on the Halfway home page
    * When I select the start button
    * Then I will be on the settings page
    * When I select the icon next to Location A the address is autofilled with my current location
    * And then I fill in location B with my friends location Detroit, MI
    * When I press find midpoint
    * Then I will be on the maps page showing the new midpoint.
    * When I select the "share_pic"
    * Then a pop up should come up allowing me to text the address of the midpoint to my friend
    */
    @Test
    public void testScenario2(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.getLoc)).perform(click());
        onView(withId(R.id.locationB)).perform(typeText("Detroit, MI")).perform(closeSoftKeyboard());
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.share)).check(matches(isClickable()));// Do not understand how to properly test bottomsheet
        // so just checking if the share button is clickable and a bottomsheet is apppearing to share
    }

    /*
    * Scenario 3
    * Given I am on the Map screen
    * When I select the Setting button
    * Then I will be on the settings page
    * When I enter two locations Chicago in a text box and Detroit in the other text box
    * And I press find midpoint
    * Then I will be on the maps page showing the new midpoint.
    * When I select the "share_pic"
    * Then a pop up should come up allowing me to text the address of the midpoint to my friend
    */
    @Test
    public void testScenario3(){
        onView(withId(R.id.begin)).perform(click());
        onView(withId(R.id.slider)).perform(setProgress);
        onView(withId(R.id.getLoc)).perform(click());
        onView(withId(R.id.locationB)).perform(typeText("Detroit, MI")).perform(closeSoftKeyboard());
        onView(withId(R.id.checkBoxRestaurant)).perform(click());
        onView(withId(R.id.gotoMap)).perform(click());

        onView(withId(R.id.share)).check(matches(isClickable()));// Do not understand how to properly test bottomsheet
        // so just checking if the share button is clickable and a bottomsheet is apppearing to share
    }
}
