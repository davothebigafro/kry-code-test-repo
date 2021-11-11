# Web Service Poller

## How to run locally

Run the frontend application and the backend server simultaneously.

Further details are inside the 2 sub-directories on how to run the individual components.

## Demo

[Click here for demo](https://frontend-7gfnechdsa-uc.a.run.app/) hosted in Google Cloud (Can sometimes take a little while to load so give it some time).

## Architecture

![Architecture Diagram](readme-images/GCPArchitechture.png)

## Features
- User can add and delete services with a url and name.
- User can update a service's url 
- Present when a service was last added or deleted with a timestamp
- URL validation on the frontend
- Poller results are automatically displayed, the app refreshes content periodically
- Examples of unit tests, on the frontend and the backend

## Wishlist
- I would like to add Multi-User support
- I would like to set up a MySQL database 
- I would like to extensively unit test my frontend React Components
- I would like to extensively unit test my backend Java classes
- I would like to add some end-to-end tests using [Cypress](https://www.cypress.io/)
- I would like to style my React Components using a nice UI library
