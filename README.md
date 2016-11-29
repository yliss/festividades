README


This file describe how to get install the web application Festivities. Or you can find all the application already deploy in Amazon Web Services In the URL http://ec2-35-161-102-173.us-west-2.compute.amazonaws.com/FestivitiesWeb/.
To install locally you need:

All source files (.ear, .sql, festivites_loadTest.xml)

this README

PostmanClient.zip

festivities.raml.xml


Homework - readme.txt


1. Do festivities.sql on a mysql Database.

2. Install Application Server Jboss EAP 6.1 availbe to download here:[https://drive.google.com/open?id=0B5OGy5MWuuMhVDI2Tjk5ZWxVdEU]


3. Install driver to database on Jboss

4. Create a Datasource on Jboss:

	 Go to the panel of the console click on “Connectors—>DataSources”
   
	 In the right side panel you will see a button “Add” click on this button.
   
	 In Wizard “Step 1/3: Datasource Attributes”
   
	 Enter the DataSource Name as “java:/jdbc/festivities”
   
	 Enter JNDI Name as “java:/jdbc/festivities”.
   
	 Now in the next Section “Step 2/3: JDBC Driver” you will see all the installed drivers details. 
   
	 In next section provide the Database url (jdbc:mysql://localhost:3306/festivities),  username and password.
   
	 Now you just have to enable the newly created datasource and if everything is configured properly you would see it green as shown
   
   
5. Install aplicacion the file .EAR
	Login in the Administration Console 
	In the left side panel you will see a button “Applications” click on this button to expand options.
	Navigate to the Application type you want to add new resource.  Select Enterprise Application (EAR)s


Profiling Results:

Watch the describing of the Services in the file [festivities.raml.xml]

Using a Client like Postman go head lauch request to Rest Service using the URL http://ec2-35-161-102-173.us-west-2.compute.amazonaws.com/FestivitiesWeb/rest/Festivities
  Please see the examples in PostmanClient.zip. Import all the package in your postman. [https://www.getpostman.com/]

In the URL http://ec2-35-161-102-173.us-west-2.compute.amazonaws.com/FestivitiesWeb/   you can load the file [festivites_loadTest.xml]
