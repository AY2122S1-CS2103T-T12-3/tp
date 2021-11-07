---
layout: page
title: Nicholas Loo's Project Portfolio Page
---

### Project: PlaceBook

PlaceBook (PB) is a desktop app for managing contacts and appointments, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).** If you can type fast, PB can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

### Summary of Contributions

* **New Feature**: Implemented the `Appointment` class so that PlaceBook can keep track of appointments for the user.
    * What it does: Each `Appointment` object represents an appointment that the user has made with people in their `Contacts`.
      An `Appointment` object contains a `UniquePersonList` to represent clients, an `Address` to represent location of the appointment,
      a `TimePeriod` to represent the planned appointment timing and duration, and a `String` to represent the description of the appointment.
    * Justification: This feature is important as it has a key role to play in allowing the user to keep track of and plan their appointments.
    * Highlights: This enhancement is important as it allows for all appointment related functionality.

* **New Feature**: Added the `addApp` command.
    * What it does: Allows the user to create `Appointment` objects to represent their upcoming appointments. 
    * Justification: This feature is a key function in allowing the user to keep track of their upcoming appointments.
    * Credits: The implementation is largely similar to the AB-3 add feature.

* **New Feature**: Implemented the Storage component for `Schedule`, `Appointment` and `TimePeriod`  
    * What it does: Allows the user to have the ability to save their current `Schedule` after every command.
    * Justification: This feature is crucial as the user would not need to re-enter their data during each separate use of PlaceBook.
    * Highlights: The enhancement is implemented by creating `JsonAdaptedAppointment` and `JsonAdaptedTimePeriod` which helps to represent the `Appointment`
      and its `TimePeriod` in a JSON file format for storage.
    * Credits: The implementation for these Storage features are largely similar to AB-3's Storage features.
    
* **New Feature**: Ensure no conflicts, and consistency between contacts and schedule when reading from JSON file
    * What it does: Checks through the saved data and ensures correctness of the data.
    * Justification: This feature is crucial as the user might make duplicates, or clashing appointments by accident in the JSON files.
    * Highlights: This enhancement is implemented by checking the validity of the JSON file format, and replacing the data with sample data if there are errors.
      Once JSON files has been correctly read, the persons in each appointment is checked to see if they exist in the contacts.

* **Other significant contribution**: Created some test JSON files such as `invalidAndValidAppointmentSchedule.json` to be used for Storage testing.
    * What I did: Modified valid JSON files to create different errors in their formatting to test the error detection of the Storage component. 
    * Justification: Users have the ability to modify their own JSON files and might cause errors in the file formatting, which may affect PlaceBook's ability to read the data.
    
* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=nwhloo99&tabRepo=AY2122S1-CS2103T-T12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
    * Created Issues to represent user stories.
    * Active contribution during weekly meeting.
    * Created some Issues to represent tasks.

* **Enhancements to existing features**:
    * Updated the clear feature to clear both `Person` data and `Appointment` data.
    * Wrote additional tests for existing Storage components.

* **Documentation**:
    * User Guide:
        * Added documentation for the feature `addApp`
        * Added the Reading the document section of the UG to help users better understand various syntaxes in the document.[\#136](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/136)
        * Updated the format for the Features section to separate Person-related features, Appointment-related features and general features.[\#136](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/136)
    * Developer Guide:
        * Wrote the Use Cases and User stories. [\#53](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/53)
        * Added some implementation details for the addApp command.[\#108](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/108)


* **Community**:
    * PRs reviewed (with non-trivial review comments):
      [\#76](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/76),
      [\#116](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/116),
      [\#183](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/183),
      [\#194](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/194),
      [\#197](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/197),
      [\#206](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/206),
    * Reported bugs and suggestions for other teams in the class
      (examples: [Reported bugs in group T10-2's team project during mock PE](https://github.com/nwhloo99/ped/issues?q=is:issue+is:open))
