## About
Welcome!  
The project you are looking at is an integration test suite designed for my application made in microservices technology.

## Microservices & Front End
In order to run my application properly please use these projects:
- Configuration server - https://github.com/AlbertHeesch/cloud,
- Discovery - https://github.com/AlbertHeesch/discovery,
- Gateway - https://github.com/AlbertHeesch/gateway,
- Rates Back End - https://github.com/AlbertHeesch/DentClinicAppRates,
- Front End - https://github.com/AlbertHeesch/DentClinicAppView.

## Requirements:

Java 11

Gradle

## How to run
Build your gradle with `gradlew build` in terminal.

Run the projects in order:
- Configuration server,
- Discovery,
- Gateway,
- DentAppClinic & DentAppClinicRates,
- DentAppClinicView.

Then you can run one of the tests.

## How to configure
My application is using Google Chrome and FireFox to perform the tests. Please download drivers corresponding to Google Chrome/ FireFox version that is present on your computer.
Once you have done it, remember to enter driver's location in `WebDriverConfig` class.

