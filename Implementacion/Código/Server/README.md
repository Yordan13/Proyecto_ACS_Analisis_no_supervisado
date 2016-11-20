# APP 
![] (/pics/app.png)

This angular app works with a backend to upload files, run a process on Java (a .jar) and show the process on the client side, and when the process is done it shows a download link. 


#How to run 
To run, first install the packages: 

    npm install 

Then run the angular app 

    npm start 

And run the backend.js 

    node backend.js 
    
# How to use 

### Change the mock video converter for a real file processor 

See example code at /videoConverterMockTest

The requierments are: 

  1- Print progress with *system.out.print(<integer>)*  from 0 to 100 ( this is the porcentual number for the client-side progress bar) 
  
  2- Do not print 100 until the file to download is ready
  
  3- Save the resulting file in the /downloads folder with the same name of the uploaded file 
  
  4- You need to modify backend.js:42 to put the appropriated .jar and parameters for you
  
# How it works
 
 
 ![](pics/comunicationDiagram.png)
 
1-Angular uploads the file using *ng2-file-upload* package, to the backend.js
 
    see /app/uploader/simple-demo.ts
  
2-The backend calls a .jar to modify the file 

   	 see backend.js
    
 3-The backend asynchronously reads the prints of the .jar 
 
    see backend.js

4-The backend creates an api with the information of the progress based on the name of the uploaded file and angular read the progress using a service with http module 
	
    see backend.js and app/uploadder/app.service.ts

5-The backend generate url to download the file when the .jar prints 100, and angular generate a link to download the file, and angular show the link when the api returns 100
	
    see backend.js and app/uploadder/simple-demo.ts
    
------------------
# Learned lessons 

#### JS calls Java async Process

https://drive.google.com/open?id=0B569OJeOrFAySmZBc2xSNFAtVVk

####JS basics (Dictionaries )

https://drive.google.com/open?id=0B569OJeOrFAyR09ncmQtTElndDQ

####Calling a Observer with time interval

https://drive.google.com/open?id=0B569OJeOrFAyaTFUYmVjR0NlMjQ

####Angular2 a http service

https://drive.google.com/open?id=0B569OJeOrFAySW93WldzNkhzUlU

####A simple API using Node and Express 4

https://drive.google.com/open?id=0B569OJeOrFAySFpxZEJUVUY1X3M

####ExpressJS download

https://drive.google.com/open?id=0B569OJeOrFAyY1V1elNqcURhcE0

# Encounter errrors 

(No provider for Http, ConnectionBackend)
https://drive.google.com/open?id=0B569OJeOrFAydElHX08xa1Y4STg

# Tools 

Random user api generator: 

https://randomuser.me/



