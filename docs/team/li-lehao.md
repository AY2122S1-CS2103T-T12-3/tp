---
layout: page
title: Li Lehao's Project Portfolio Page
---

### Project: PlackBook

PlaceBook (PB) is a desktop app for managing contacts and appointments designed for salesperson and marketing person,
optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
If you can type fast, PB can get your contact management tasks done faster than traditional GUI apps.

### Summary of Contributions

Given below are my contributions to the project.

* **New Feature**: Added the ability to undo previous commands.
  * What it does: allows the user to enter an `undo` command, which let the PlackBook undo the previous command
    and go back to the state before executing the previous command.
  * Justification: This feature improves the user experience significantly because the user 
    can make mistakes in commands, such as deleting a contact accidentally, 
    and the app should provide a convenient way to rectify them. 
  * Highlights: This enhancement affects almost all existing commands and commands to be added in future, 
    and it is also related to almost all components of model.
    Therefore, it required an in-depth analysis of design alternatives. 
    The implementation too was challenging as it required changes to existing commands.

* **New Feature**: Added the `listApp` command.
  * What it does: the `listApp` command allows the user to let PlackBook display a list of all the appointments.
    In addition, users can also add keywords such as `time` and `description` after the `listApp` command,
    to request PlackBook to display the list sorted according to certain keywords.
  * Justification: This feature improves the user experience significantly because the user need a command
    to go back to the original appointment list after they use `findApp` command and some other commands.
    In addition, sorting the appointment list according to certain keywords significantly improves the convenience
    for users to look through the appointment list.

* **New Feature**: Implement the `TimePeriod` class so that PlaceBook can check potential time conflicts among the appointments.
  * What it does: each `TimePeriod` object represent a specific period of time. 
    It ensures that the recorded time period must be valid, that is, 
    the start date and time are valid, 
    the end date and time are valid, 
    and the end time must be after the start time. 
    At the same time, it provides a way to check whether there is a conflict between two time periods.
  * Justification: This feature is important because it allows PlaceBook to check potential time conflicts among all the
    appointments in the schedule, and checking time conflict is an essential feature of PlaceBook.

* **Other significant contribution**: Implement some test utilities such as `AppointmentBuilder` and `TypicalAppointments`.
  * What it does: These test utilities provide a way to quickly and concisely generate many meaningful appointment objects.
  * Justification: These test utilities are important because
    it is often necessary to create a lot of appointments during testing.
    These tools allow people to quickly create many meaningful appointment instances,
    avoiding the confusion and duplication of creating different instances everywhere.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&since=2021-09-17&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=false)
* **Project management**:
    * Recorded some important contents of the weekly team meeting.
    * Created some issues to represent tasks.

* **Enhancements to existing features**:
    * Updated the `Appointment` and `Schedule` class, allowing them to detact and remove the appointment
      with empty client list.
    * Wrote additional tests for `Appointment` class.

* **Documentation**:
    * User Guide:
        * Added screenshots to better display the product interface and better guide the users how to operate. [#136](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/136)
        * Edited the `addApp` example to better show users how to use this command. [#136](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/136)
    * Developer Guide:
        * Added implementation details of the `TimePeriod` feature. [#193](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/193)
        * Added implementation details of the `undo` feature. [#221](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/221)

* **Community**:
    * Reviewed PRs 
      [#217](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/217)
      [#140](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/140)
      [#126](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/126)
    * Reported bugs and suggestions for other teams in the class 
      (examples: [Reported bugs in group W11-3's team project during mock PE](https://github.com/Li-Lehao/ped/issues?q=is:issue+is:open))
