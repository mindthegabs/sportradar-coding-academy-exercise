
<h1 align="center">
  <br>
  <br>
  Event Calendar
  <br>
</h1>

<h4 align="center">A simple event calendar created as part of the coding academy application.</h4>

## Features

* Show calendar events
* Add calendar events

## How To Use
<ol>
  <li>Change the database settings and credentials in line 39 of '/src/main/java/com/sportradar/academy/util/DBUtil.java' to your desired database software and add the respective dependency if you are not using MYSQL.</li>
  <li>Create the tables for the database by running 'create.sql' and insert testdata by running 'insert.sql'. You can find the files in '/database/'.</li>
  <li>Run the program.</li>
  <li>Access the calender by using this url:'http://localhost:8080/events?orderedBySport=false'.</li>
  <li>You can click the button 'Add new event 'at the bottom to be forwarded to the form where you can add an event to the calendar. The  'Show Details' buttons on the right do not have any functionality yet.</li>
  <li>Make sure to enter valid data and click on the button 'Add'. You will be directed to the calender starting page again.</li>
  <li>Order the events by sport by changing the url to 'http://localhost:8080/events?orderedBySport=true'. </li>
  
</ol>


