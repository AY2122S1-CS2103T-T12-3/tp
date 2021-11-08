---
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



* **Other significant contribution**: Implemented the `EditAppDescriptorBuilder` test utility.
    * What it does: The test utility provides a way to quickly and concisely generate EditAppDescriptor objects used for testing.
    * Justification: These test utilities are important because it is necessary to create instances of EditAppDescriptors during testing, and the builder
      will help to create these instances more easily.


* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=necrowolf&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=NecroWolf28&tabRepo=AY2122S1-CS2103T-T12-3%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)


* **Project management**:
    * Recorded minutes for weekly meetings in the shared document for the team's easy reference.


* **Enhancements to existing features**:
    * Updated the `CommandTestUtil` class, providing more examples for valid and invalid appointment fields and example use inputs.
    * Added additional syntax to the `CliSyntax` class.


* **Documentation**:
    * User Guide:
        * Added documentation for the `delApp`, `findTags` and `editApp` features [\#124](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/124)
        * Expand FAQ section [\#137](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/137)
        * Fixed documentation errors [\#182](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/182)
    * Developer Guide:
        * Added use case documentation for the feature `find`[\#114](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/114)
        * Added Commands breakdown [\#138](https://github.com/AY2122S1-CS2103T-T12-3/tp/pull/138)
    * Performed Quality Assurance on the final products of the User Guide and Developer Guide.


* **Community**:
    * Reviewed PRs
    * Reported possible bugs and feature implementation issues for teammates'
      


