---
layout: page
title: Yap Teng Chiong's Project Portfolio Page
---

### Project: PlaceBook

PlaceBook (PB) is a desktop app for managing contacts and appointments, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).** If you can type fast, PB can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: `addApp`
    * What it does: Allows the user to add new Appointments to Placebook
    * Justification: This is a must-have feature for users to begin using the Appointment management feature.
    * Highlights: This feature contains checks to ensure that no duplicate appointments are added.
    * Credits: the implementation is similar to the AB-3 add feature.

* **New Feature**: List sorting
    * What it does: Allows the user sort the list based on either the timing and date of appointments or the description
                    alphabetically
    * Justification: This is a helpful feature for users as it allows them to better view their appointments and improves 
                     the overall user experience.
    * Highlights: This feature assists the user in sorting their appointments and can be expanded upon in the future to 
                  include other conditions
    * Credits: the implementation was based on filter function in AB-3

* **New Feature**: Ensures no conflicting timings when adding appointments
    * What it does: Ensures that there are no clashing timings when adding new Appointments 
    * Justification: This is an important feature for users as it streamlines their workflow. By informing the users of
                     clashes in their appointments when adding them, they are able to immediately rectify this problem
    * Credits: the implementation of timePeriod was done by LeHao

* **Other significant contribution**: 
  1. Implemented Schedule Class to model a list of Appointments.
      * What it does: Models a real life schedule in the system, containing functions to manage appointments.
      * Justification: Sets the groundwork for future additions to the appointment manager of Placebook.
     
  2. Integrated the timePeriod class into the overall application.
      * What it does: ensures that Time Period works properly with the rest of the application
      * Justification: Allows for the usage of time period in future additions, streamlining the management of start and
                       ending date times of appointments.
      * Credits: timePeriod class was coded by LeHao

  3. Removed all mentions of the old AB-3 from the code.
      * What it does: Makes the code more coherent to our design for Placebook. 

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/#breakdown=true&search=tchiong)

* **Project management**:

* **Enhancements to existing features**:

* **Documentation**:

* **Community**: