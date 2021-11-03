---
layout: page
title: Chen Yanyu's Project Portfolio Page
---

### Project: PlaceBook

PlaceBook (PB) is a desktop app for managing contacts and appointments, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).** If you can type fast, PB can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: Added Light Theme/ Dark Theme toggle function
  * What it does: allows the user to change the theme fo the application.
  * Justification: This feature improves the user experience as it allows users to work with greater eye comfort in various
    lighting situation, such as bright office, outdoor, or dark cafe. This may be especially needed by our target audience: 
    sales and management executive, due to their volatile work environment.
  * Highlights: This enhancement is implemented with another .css stylesheet. So each style can be adjusted independently 
    in each stylesheet. Moreover, in the light theme stylesheet, I used css global variables, so it will be easier to add 
    even more themes in the future just by changing the values of each global variable without needing to adjust the color 
    of each class separately. I believe this will improve the maintainability of the UI design.

* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.
  * What it does: allows the user to navigate to previous or next commands using up/down keys.
  * Justification: This feature improves the user experience as it improves user's efficiency in repetitive tasks such as 
    adding or deleting multiple appointments/clients.
  * Highlights: This enhancement is quite independent of the existing system, and it is implemented in a way that adds 
    minimal dependencies. This makes this feature and the whole system easily maintainable in the future.
  * Credits: I reused parts of the code from my own CS2103T independent project.

* **New Feature**: Urgency display in appointment list
  * What it does: allows the user to view appointments with different colors for different levels of urgency.
  * Justification: This feature will help the user better visualise the priority of their appointments.
  * Highlights: the implementation sets translucent background for each appointment card, so the theme of 
    software can be seen. This reduces future developers' need to specify new urgency colors for each theme, which 
    improves maintainability.

* **New Feature**: `findApp`
  * What it does: allows the user to find appointments with the given keyword.
  * Justification: this is a must-have feature for users to find specific appointments in the list easily.
  * Credits: the implementation is largely similar to the AB-3 find feature.

* **Other significant contribution**: implement UI for appointment list with dropdown list for clients in each appointment.
  * What I did: refactored the UI components, added appointment list, added dropdown list for clients in each appointment.
  * Justification: re-organise the layout to give a business outlook. The addition of the dropdown list is also essential 
    as it helps users to prepare for the meeting easily without going back to the client list to find the clients.
  
* **Code contributed**: [RepoSense link]()

* **Project management**:
  * Set up GitHub organisation and many other administrative structures such as milestones, issues and CI.
  * Proposed agenda for weekly meeting and conduct weekly meeting with active contribution.
  * Managed releases `v1.3` `v1.3b` (2 releases) on GitHub
  
  
* **Enhancements to existing features**:
  * Updated the UI layout
  * (to be edited)Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())


* **Documentation**:
  * (to be edited)
  * User Guide:
    * Added documentation for the feature `findApp`[\#101](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/101)
    * Added documentation for the features `command history navigation` and `theme toggle`.[\#140](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/140)
  * Developer Guide:
    * Added implementation details of the `delete` feature. [\#106](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/106)
    * Update uml for model and storage. [\#134](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/134)


* **Community**:
  * (to be edited)
  * PRs reviewed (with non-trivial review comments): 
    [\#84](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/84), 
    [\#116](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/116), 
    [\#117](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/117), 
    [\#69](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/69), 
    [\#185](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/185), 
  * Contributed to forum discussions (examples: [1](https://github.com/nus-cs2103-AY2122S1/forum/issues/158#issuecomment-909827199), [2](), [3](), [4]())
  * Reported bugs and suggestions for other teams in the class (examples: [1](), [2](), [3]())
  * Some parts of the history feature I added was adopted by several other class mates ([1](), [2]())

