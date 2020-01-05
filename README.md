Iteration 3 Report - 12/11/19

In iteration three we decided to add more implementations and also decided to focus on the User Interface. We added a new option, Hotels, for the user to choose from. Also we added a feature for the user to alter the distance for the midpoint to be generated. If the user decides to make the meet up point closer to them or the other location then they can alter it by using the slider widget. Furthermore, we tried to make the User Interface more appealing by adding colors to the text and changing the overall theme of the background of the app to being dark. We also added the HalfWay logo to the home page of our app making our app look more professional. Also we added routes to our map so that you can see the route the user can take from their location to the midpoint.

The last User Stories we tackled were Routes, Hotel Meetup, Plan a Long Distance road trip, and Meet Closer to Me. The Routes was to test the usage and implementation of the routes feature we added. Plan a Long Distance story was a to test all the features of the app. Hotel Meetup was to test the usage of the Hotel filter to find nearby hotels for the user to stay at that are near the midpoint. The Meet Closer to Me story tests the implementation of the slider method and how it works together with the other types of location filter. 

Also during this iteration we also used the Lint tool to find any errors, bugs, and stylistic problems. No bugs and errors appeared. There were just many warnings. We tried to fix as many warning as we can without altering the code function. A lot of the typos we got were about names in our xml which did not seem important because they did not make the code hard to decipher so it seemed unneccessary, especially since they were warnings. Additionaly issues include that lint suggests we change our Async activity to a static method to avoid static resource leaks, however in doing this we would have had to of changed variables to static variables which we could not do as that would alter functionality of our app and may have lead to more resource use in the long run. We have a fair number of TextView internalization warnings which we were able to fix many of using string res variables, however the remaining unfixed values could not be changed due to functionality requirements. We also received warnings for bidirectional text saying we should not use drawableLeft attribute, however this attribute was desired. We also received a warning for not supporting Firebase App Indexing, however this change did not appear to be neccesary for our app. Finally, we received warnings for not providing an autofill trait for text entry boxes this is on purpose as that is the functionality we desire. 

A couple of difficulties we faces were understanding Lint and how to properly use lint. We wanted to use Lint to fix a lot of the warnings without changing altering the function of the code. Also we faced a little bit of difficulty learning how to make the User Interface very appealing. We researched how to add color and change the theme. As well as a proper implementatation of the slider tool to calcualate the range between two locations, A and B. Also of note is that some of our tests involve using user location. If a test is failing this could be due to testers location changing when using their location, however this should be compensated for in the tests.




Iteration 2 Report - 11/14/19

In the second iteration we decided to add more features to the app. At the end of iteration 1 our app was able to generate a midpoint address and restaurants near it. We decided to add more options of the user. Now the user can decided between Restaurants, Parks, and Gas Stations near the midpoint and a location will generate displaying that and its address. This added more functionality to the app. Furthermore, we were able to make the app share the address of this special location through text. We now have 2 share button and one shares the midpoint address and the other shares the special location address, special location determined by user from the settings page. This way the user can send the address to another person. In this iteration the 3 user stories we have completed are the Park Meetup user story, Road Trip user story, and the Gas Station user story. These user stories increased the functionality of the app.

The design method we implemented was the Observer design implementation. We created an interface and also created a class that would use the interface and generate a unique url depending on what what checked off by the user in the user interface. This would create a unique url for the app to use to generate an address for either a gas station, park, or restaurant. This would then be displayed on the maps activity allowing the user to view and share. 

A couple of difficulties we faces was learning how to implement an effective way to generte the different addresses based off what the user selects. We accomplished this through the interface and the implementation of the SpecialPoint java class. The class allowed us to generate the URLs based off what the user wants to use. 

There are some additions to the UI of the app. For starters, the app settings page now was 2 more check boxes and you can either check one box or check none. Also in the maps page there are now 4 markers. 2 for the inputted addresses, 1 is the midpoint location, and 1 is the special location, if selected to generate in the settings page. Also in the maps page there is now another button that also says 'share' and it is next to the special location address and this allows you to share the special location through messages on your phone.



Iteration 1 Report  -   10/22/19

In this iteration of our app we implemented functionality to allow two locations to be entered and subsequently have a midpoint (the address closest to the midpoint) displayed on a map. The user stories that we were fully able to implement in this iteration include: Geographic Point (story #2) where two locations are entered and the midpoint address is displayed without any other requirements for the search, Find a Restaurant near the halfway point (story #10) where a restaurant close to the midpoint is displayed on a map, and Sharing Halfway Point (story #8) where the user is able to share their results via a text or other method of communication. During the next iterations we plan to add features such as using your current location and finding other types of locations near the midpoint. Some of these features have buttons in the app but are not intended to be considered as part of this iteration. These features are represented in some of the stories that are still open. 

When running the app on an emulator some problems may be faced. First when using your current location, the emulator may not work as intended. A prompt will show up the first time you try this, so it is best to allow location before running tests. Occasionally and randomly you may get an alert saying that a location you entered is not valid. Make sure the address you entered is a valid address, and if the problem persists clearing the emulator storage/memory and performing a cold boot may help.

The tests for the app are aimed at ensuring the three user stories and scenarios were satisfied in this iteration. Tests are broken into three classes corresponding to each user story. Within each class there will be three methods, each method corresponding to a different scenario, where the user may be taking different paths through the app to acheive similar goals as desired by the specified user story. An example test class name is StoryNum8SharingHalfwayPoint which has the tests for our scenarios in story #8. Certain roadblocks were encountered when conducting the scenario tests as we used Espresso which is a tool meant to test the UI. Given that we used a map showing waypoints there was no perfect way to test this. In an effort to make sure locations were correct, we tested text boxes surrounding the map which displayed the exact locations of interest. Additionally, when testing for sharing the midpoint, we are relying on the user to enter information in the text such as a persons phone number and message contents, therefore we went as far as testing to make sure the prompt for sharing location would be displayed. Also, mysteriously and semmingly randomly, when running Espresso tests concerning the check box, sometimes the box will not check when clicked or views with esstablished ids cannot be identified. If these errors occur during testing, we ask you to wipe memory and cold boot as well as close background tasks on your computer. Using a different emulator may help too, all tests have been proven to work on our end. 

Finally for a description of our UI. On the maps page up to four addresses may appear. The top address is the location of the midpoint, the next address directly under the map will show restaurant location if applicable. The next two addresses under that are the addresses entered in the text boxes. These text boxes were integral in testing maps and location processes. 

 
