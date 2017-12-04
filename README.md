# Echoes
Project for CMPUT 301 Fall 2017

The Echoes is a simple, attractive, and easy-to-use app that assists users in forming Good habits. This app allows users to track, encourage, and share their habits as well as share their habit event locations.

## Team Members
	Hayden Bauder
	Mitchell Ballou
	Shan Lu
	Taijie Yang
	Xinrui Lu
	Zhaozhen Liang

### App Feature Specification
	1. User can create a new account or log in to look at his/her profile.
	2. User can create a new habit and add it to My Habits screen. The habit that the user created
	has a habit title (20 chars max), a reason (30 chars max), a start date and a scheduled plan.
	3. User can edit or delete the existing habits.
	4. User can see the overview with a statistical and visual habit status indicator of each habit 
	in My Habits.
	5. User can see all habits that need to do today from To Do screen.
	6. From To Do screen, user can click on the habit that need to to today to add a new habit event
	of this selected habit to Habit History.
	7. From Habit History, user can add a new habit evnet with the existing habit type, optional comment,
	optional photo, and optional location.
	8. User can edit or delete the existing habit events in Habit History.
	9. User can see all overviews of habit events in Habit History.
	10. User can filter habit events in Habit History with the given type of the habit and searched words 
	in habit events' comment.
	11. User can click map to show all habit events with locations on the map.
	12. User can search other users and send a following request to follow all habits with their most recent 
	habit events.
	13. User can grant the following requests from other users in Message screen.
	14. User can see all habits with most recent events from his/her followings in Following screen.
	15. Click the map button in Following screen, user can just see the most recent habit events of his/her 
	followings on the map.
	16. User can give kudos to his/her habits and the habits of his/her followings.
	17. User can leave comments to his/her habits and the habits of his/her followings.
	18. User can click the map from bottom navigation to see all habit events of the login user and the habit 
	events of the followings of the login user on the map. User can click the highlight checkbox to highlight the
	habit events that are within 5km of the current location.
	19. When the user is offline, user can also add/edit/delete habits and habit events, all changes made will be
	synchronized with online data once the user gets connectivity.

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
