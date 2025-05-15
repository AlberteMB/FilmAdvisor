# 🎬 FilmAdvisor
The FilmAdvisor is a web-based application designed to help users discover, filter, and manage movies across different streaming platforms. This document provides a high-level introduction to the system architecture, core components, and key features of the FilmAdvisor system.

##System Purpose
FilmAdvisor serves as a centralized movie recommendation and management platform that allows users to:

- Browse and filter movies by various criteria (genre, platform, year)
- Discover random movie recommendations
- View detailed movie information
- Track watched movies and save favorites
- For detailed information about the complete system architecture, see System Architecture.

## 🚀 Tech Stack
### Backend

- **Java 21**

- **Spring Boot 3.4.3**

- **Vaadin Hilla** (Vaadin + TypeScript with secure endpoints)

- **AWS DynamoDB** – NoSQL database for storing movies, metadata, and availability.

- **PostgreSQL** – For user authentication and profile persistence (used alongside DynamoDB).

- **Streaming Availability API** – To fetch real-time streaming availability.

- **IMDb** – For optional rating integration.

### Frontend

- Vaadin: Version 24.6.6 for the UI framework
- Accordion filter components for genres and other preferences.

##  🔧 Features
- **User Profiles:** Users can define their preferred streaming platforms and filter settings.

- **Random Movie Discovery:**

    - Filter by *genre*, *release year*, and *platform*.

    - Choose to display 3, 6, or 9 movies.

- **Streaming Availability:** View where each movie is available to watch.

- **Interactive Lists:**

    - Mark movies as *watched* or *discarded*.

    - Give watched movies a *like* or *dislike*.

- **Temporary Retry ("Try Again") Button:** Get a fresh set of suggestions without saving them.

- **Detailed Movie View:** Expand to show synopsis, IMDb/Filmaffinity links, and platform links.

- **Direct Search:** Search by title, director, actor, or genre.

---

# Custom project from Hilla

This project can be used as a starting point to create your own Hilla application with Spring Boot.
It contains all the necessary configuration and some placeholder files to get you started.

## Running the application

The project is a standard Maven project. To run it from the command line,
type `mvnw` (Windows), or `./mvnw` (Mac & Linux), then open
http://localhost:8080 in your browser.

You can also import the project to your IDE of choice as you would with any
Maven project.

## Deploying to Production

To create a production build, call `mvnw clean package -Pproduction` (Windows),
or `./mvnw clean package -Pproduction` (Mac & Linux).
This will build a JAR file with all the dependencies and front-end resources,
ready to be deployed. The file can be found in the `target` folder after the build completes.

Once the JAR file is built, you can run it using
`java -jar target/myapp-1.0-SNAPSHOT.jar` (NOTE, replace
`myapp-1.0-SNAPSHOT.jar` with the name of your jar).

## Project structure

<table style="width:100%; text-align: left;">
  <tr><th>Directory</th><th>Description</th></tr>
  <tr><td><code>src/main/frontend/</code></td><td>Client-side source directory</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.html</code></td><td>HTML template</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>index.ts</code></td><td>Frontend 
entrypoint, bootstraps a React application</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>routes.tsx</code></td><td>React Router routes definition</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>MainLayout.tsx</code></td><td>Main 
layout component, contains the navigation menu, uses <a href="https://hilla.dev/docs/react/components/app-layout">
App Layout</a></td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>views/</code></td><td>UI view 
components</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>themes/</code></td><td>Custom  
CSS styles</td></tr>
  <tr><td><code>src/main/java/&lt;groupId&gt;/</code></td><td>Server-side 
source directory, contains the server-side Java views</td></tr>
  <tr><td>&nbsp;&nbsp;&nbsp;&nbsp;<code>Application.java</code></td><td>Server entry-point</td></tr>
</table>

## Useful links

- Read the documentation at [hilla.dev/docs](https://hilla.dev/docs/).
- Ask questions on [Stack Overflow](https://stackoverflow.com/questions/tagged/vaadin) or join our [Forum](https://vaadin.com/forum).
- Report issues, create pull requests in [GitHub](https://github.com/vaadin/hilla).
# FilmAdvisor
