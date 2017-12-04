# Echoes
Project for CMPUT 301 Fall 2017

## Team Members





### Sign up / Profile
	Create a new account
	Log in	
	Look at my profile/overview

### Habits / ToDo
	Create a new Habit
	Show it in the ToDo list
	Create a habit that will not be in the todo list
        Click the habit in the todo list to add a new habit event to habit history
        Edit an existing habit
        Delete a habit

### Habit Event
	Create a new Habit event
        Edit a habit event
        Delete a habit event
        Search word in habit events' comment
        Filter habit events with selected habit type
        Apply search and filter together
        Click show map in Habit History to just show the habit events with geolocation of the login user

### Geolocation
	Add a habit event to the map
        Click the map from bottom navigation, show all habit events of the login user and the habit events of the followings of the login user on the map. Click the highlight checkbox, highlight the habit events that are within 5km of the current location.

### Following
	Sign into another profile
	Follow the another user, the followed user should login to accept the following request.
        The followed user should add several habits and corresponding habit events, and some of these habit events should have geolocations.
        The user who has followings can see the followings habits and their most recent habit event for each following habit in Following. Clicking on the map button in Following, the habit events with geolocations followed by the user are displayed on the map, click on the highlight checkbox, locations that are more than 5km from the current location are displayed with more transparency.

### WOW factor
	Like the habit we made earlier
        Comment on the habit of followings, this following logins and check the comment of the habit in My Habits.

### Offline Behavior
	Show offline sync


### UI Feature Specification

        IME(Input Method Editor) 
              Sign in -> enter key to sign in  or click button sign in     no default soft keyboard show up
              Sign up -> enter key to jump to next edit line   
              Search  -> enter key to search
              Send    -> enter key to send message

        Interactive Animation Button
              Sign in/Sign up with animation circular progress bar animation also hit button to activate the activity transition circular reveal animation
              Habit History floating button with rotation animation and hide and show two additional buttons and will hide as scroll down the recycler view  and scroll up the recycler view to show up again
              My Habit add habit floating button will hide as scroll down the recycler view  and scroll up the recycler view to show up again also hit button to activate the activity transition circular reveal animation
              
        Activity Transition Animation
              Sign in / Sign up / My Habit  circular reveal animation as transiting to another activity

        Calendar view
              self design weekday view with circle and dot highlight the current weekday and month day

        Bottom Navigation Bar
              For all important activity can easily navigate through different activity by hitting the activity icon on the Bottom Navigation Bar 

        Allow slide to delete item in Recycler view

        Search edit text box

        Comment edit text box

        Recycler view item views

        Comment  layout design
