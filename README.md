[![Code Climate](https://lima.codeclimate.com/github/WoKak/LanguagePlatform/badges/gpa.svg)](https://lima.codeclimate.com/github/WoKak/LanguagePlatform)

Now jar file is the old version of the project (without database usage) - it will be updated later.

To run this project:
 
1. (database version - latest one)

At first PostgreSQL Server is needed. Then it is crucial to create database named plang.
Run jar file using command:

java -jar plang(latest_ver).jar

2. (old version)

Just open the jar file.
 
 -------------------------------------------------------------------------------------------------------
 
You also need base.txt file in both versions:

1.) file is needed for the operation: "create table" in the database

2.) program reads words from this file


(very simple one is provided in the same directory as jar file).

In order to change base just replace base.txt file (WARNING! words should be in the same order
as before).