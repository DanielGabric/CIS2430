Text file Format: example input file input.txt in submission

Activity type (either s,h, or o)
Title
startTime (same as endTime)
endTime  (of the form (YYYY:MM:DD:HH:mm) (where YYYY>=2014, 1 <= MM <= 12, DD <= 31 (according to month), 0 <= HH <= 59, 0 <= mm <= 59)
comment  (if empty leave an empty line)
location (only if activity type is o)


1. The problem I am trying to solve is to create a fully functional dayplanner. This dayplanner 
needs to be able to schedule, School activities, Other Activities, and Home Activities.
The dayplanner needs to be user friendly and needs to be robust in its dealings
with wrong user input. Also needs to use hashmaps, textfiles and a superclass
2.Some assumptions/limitations
-the user cannot enter the max day in February on a leap year
-the user is using this program within years 2014-9999
-the user can enter two activities that overlap
-the user can have a start and end time that are the same
-the user can enter a start time that is at a later time than the end time
3. how to compile, run, and use
-navigate to the ******_a2 directory
-in the terminal type in "javac dayplanner/*.java"
-now type in "java dayplanner.DayPlanner filePath" where filePath is the file path to the input/output file
-to use the program follow the prompts within the program
4.Test plan
These tests case tests every single condition in the program 
INPUT:
q
OUTPUT: Bye, see you another time!
expected OUTPUT: Bye, see you another time!

INPUT1:

add
HOME
This is a title

2014:10:111:10:10

2014:10:10:10:10
201555555:10:10:10:10
2015:10:10:10:10
this is a comment
a
sdsjak

oTHer

This is another title

2014:12:10:10:10

2016:11:11:11:11
this is a random comment

THIS IS A LOCATION
SEarCh



S
title



search

2014:12:10:10:10
2016:11:11:11:11

s

2014:10:10:10:10

home
search


2016:11:11:11:11

search




quit

output:
To add an activity type add. To search for an activity type search. To quit type quit.
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter a title of the activity(short description):
Please enter a date in 2014 or on.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date, and/or a date after the start date.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter a comment on the activity (i.e. longer description):
Successfully added a new activity!
To add an activity type add. To search for an activity type search. To quit type quit.
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter a title of the activity(short description):
Please enter a date in 2014 or on.
Enter a title of the activity(short description):
Please enter a date in 2014 or on.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date, and/or a date after the start date.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date, and/or a date after the start date.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter a comment on the activity (i.e. longer description):
Please enter a location for this 'other' activity.
Please enter a location for this 'other' activity.
Successfully added a new activity!
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
NOTHING!
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is another title Start time: 2014:12:10:10:10 End time: 2016:11:11:11:11 Comment: this is a random comment Location: THIS IS A LOCATION
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is a title Start time: 2014:10:10:10:10 End time: 2015:10:10:10:10 Comment: this is a comment
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is another title Start time: 2014:12:10:10:10 End time: 2016:11:11:11:11 Comment: this is a random comment Location: THIS IS A LOCATION
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is a title Start time: 2014:10:10:10:10 End time: 2015:10:10:10:10 Comment: this is a comment
Title: This is another title Start time: 2014:12:10:10:10 End time: 2016:11:11:11:11 Comment: this is a random comment Location: THIS IS A LOCATION
To add an activity type add. To search for an activity type search. To quit type quit.
Bye, see you another time!

expected output:
To add an activity type add. To search for an activity type search. To quit type quit.
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter a title of the activity(short description):
Please enter a date in 2014 or on.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date, and/or a date after the start date.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter a comment on the activity (i.e. longer description):
Successfully added a new activity!
To add an activity type add. To search for an activity type search. To quit type quit.
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter what type of activity you want to enter.
(h for home, s for school, o for other)
Enter a title of the activity(short description):
Please enter a date in 2014 or on.
Enter a title of the activity(short description):
Please enter a date in 2014 or on.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date. 
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date, and/or a date after the start date.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Please enter a non empty date, and/or a date after the start date.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter a comment on the activity (i.e. longer description):
Please enter a location for this 'other' activity.
Please enter a location for this 'other' activity.
Successfully added a new activity!
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
NOTHING!
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is another title Start time: 2014:12:10:10:10 End time: 2016:11:11:11:11 Comment: this is a random comment Location: THIS IS A LOCATION
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is a title Start time: 2014:10:10:10:10 End time: 2015:10:10:10:10 Comment: this is a comment
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is another title Start time: 2014:12:10:10:10 End time: 2016:11:11:11:11 Comment: this is a random comment Location: THIS IS A LOCATION
To add an activity type add. To search for an activity type search. To quit type quit.
Please enter a few keywords you want to search for, delimited with spaces.
Please enter a start time in the form YYYY:MM:DD:HH:mm.
Please enter a end time in the form YYYY:MM:DD:HH:mm.
Enter what type of Activity
The results are:
Title: This is a title Start time: 2014:10:10:10:10 End time: 2015:10:10:10:10 Comment: this is a comment
Title: This is another title Start time: 2014:12:10:10:10 End time: 2016:11:11:11:11 Comment: this is a random comment Location: THIS IS A LOCATION
To add an activity type add. To search for an activity type search. To quit type quit.
Bye, see you another time!



5.Improvements
- I would make it so that no two activities can overlap
- I would make it so that an activity has to be a minimum of 1 minute in length
- I would extend the minimum and maximum years to 0000- infinity, since we don't know when
  this program is going to be used and whether time machines exist then
- I would adjust the isGoodDate to work for leap years too
- I would make the start time have to be before the end time