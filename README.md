# CS-Projects
Computer Science Projects made using Python and Java.


## Python Personal Project - Car Parking Management System
* Through this project, I created a car parking system that stores records about parking spots available for cars by using Python as the front-end application and MySQL as the back-end database.
* Although the code provided is only for 10 parking spots, it is for demonstration purposes and the value can be increased to thousands of records as well.
* There are 4 main modules provided in this system - Park Car, Remove Car, Car Type Details, and Data Analysis.
* The back-end database on MySQL utilizes one table to store the data on each parking spot.
* The program first creates the database and table in case they do not exist followed by the list of modules present in the system. Entering the corresponding value of each module makes the program enter the specific module, followed by the intended functionality as mentioned below. 
### Park Car
* This module allows the administrator to enter the details of the car to park it at the designated parking spot. If the number of days or parking spot is less than 1 or greater than 10 and if a duplicate spot is entered, an error will be given. If all the constraints are met, the car will be parked at the designated parking spot.

The image below shows the process of adding a new car to the records:
  
![image](https://github.com/SiddharthN21/CS-Projects/assets/112213674/253eca9a-e57b-4fa1-85a7-78f1cb4491df)
### Remove Car
* This module allows the administrator to remove the required car by entering the designated parking spot (from 1 to 10).

The image below shows the process of removing a car from the records:

![image](https://github.com/SiddharthN21/CS-Projects/assets/112213674/289b87e0-f170-41a8-b5a9-4f0f32c04f7b)
### Car Type Details
* This module allows the administrator to view the details of each car of a particular car type by entering the required car type. The output will be displayed in python as a data frame containing the details of the car.

The image below shows the process of viewing the details of a particular car type:

![image](https://github.com/SiddharthN21/CS-Projects/assets/112213674/03693067-5105-46dc-be0f-1427c28a281a)
### Data Analysis
* This module allows the administrator to see how long each parking spot in the parking lot is used. The output is displayed as a line graph with the x-axis as the parking spot number (from 1 to 10) and y-axis as the number of days (from 1 to 10).

The image below shows the process viewing the graph of parking spots used in the lot:

![image](https://github.com/SiddharthN21/CS-Projects/assets/112213674/42b3ce6b-62f5-4953-afb9-a2f933798753)

### MySQL Tables
* The database used is called parking which has one table called car.
* The parking_spot field holds the primary key for the table and none of the fields can hold NULL values.
* The data is stored and retrieved using the MySQL Connector API and SQL Alchemy Library.

The images below show the table within the parking database:

![image](https://github.com/SiddharthN21/CS-Projects/assets/112213674/ea73d6e0-3999-400f-8af0-0bde0a2366ff)

![image](https://github.com/SiddharthN21/CS-Projects/assets/112213674/5bc2a054-2436-4e64-a32c-dd24fa80f1ac)



