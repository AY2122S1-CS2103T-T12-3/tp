layout: page
title: Chai Yew Meng's Project Portfolio Page
---

### Project: PlaceBook

PlaceBook (PB) is a desktop app for managing contacts and appointments, optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).** If you can type fast, PB can get your contact management tasks done faster than traditional GUI apps.

Given below are my contributions to the project.

* **New Feature**: `findTags`
    * What it does: allows the user to find contacts with tags that match the given keyword(s).
    * Justification: This is an important feature if users want to create appointments with groups of people with the same
      tags, for example people from the same company.
    * Credits: the implementation is largely similar to the AB-3 find feature.

* **New Feature**: `editApp`
    * What it does: allows the user to edit at least one field in an existing appointment.
    * Justification: This is an important feature if users want to edit an appointment without having to delete the existing
      one and creating a new one.
    * Credits: the implementation is slightly similar to the AB-3 edit feature, but includes important additions as well.

* **New Feature**: `delApp`
    * What it does: allows the user to delete an existing application based on its index shown on the app.
    * Justification: This is a must-have feature for users to delete appointments.
    * Credits: the implementation is slightly similar to the AB-3 delete feature, but includes important additions as well.

* **New Feature**: Added a history command that allows the user to navigate to previous commands using up/down keys.
    * What it does: allows the user to navigate to previous or next commands using up/down keys.
    * Justification: This feature improves the user experience as it improves user's efficiency in repetitive tasks such as
      adding or deleting multiple appointments/clients.
    * Highlights: This enhancement is quite independent of the existing system, and it is implemented in a way that adds
      minimal dependencies. This makes this feature and the whole system easily maintainable in the future.
    * Credits: I reused parts of the code from my own CS2103T independent project.

* **Other significant contribution**: implement UI for appointment list with dropdown list for clients in each appointment.
    * What I did: refactored the UI components, added appointment list, added dropdown list for clients in each appointment.
    * Justification: re-organise the layout to give a business outlook. The addition of the dropdown list is also essential
      as it helps users to prepare for the meeting easily without going back to the client list to find the clients.

* **Code contributed**: [RepoSense link]()

* **Project management**:
    *

    * Set up GitHub organisation and many other administrative structures such as milestones, issues and CI.
    * Proposed agenda for weekly meeting and conduct weekly meeting with active contribution.
    * Managed releases `v1.3` `v1.3b` (2 releases) on GitHub


* **Enhancements to existing features**:
    * Updated the UI layout
    * (to be edited)Wrote additional tests for existing features to increase coverage from 88% to 92% (Pull requests [\#36](), [\#38]())


* **Documentation**:
    * (to be edited)
    * User Guide:
        * Added documentation for the feature `findTags`[\#101](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/101) //TODO: change to the correct one
        * Added documentation for the features `command history navigation` and `theme toggle`.[\#140](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/140)
    * Developer Guide:
        * Added implementation details of the `delete` feature. [\#106](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/106)
        * Update uml for model and storage. [\#134](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/134)
    * Performed Quality Assurance on the final products of the User Guide and Developer Guide.


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