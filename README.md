# Coding Challenge - User Hierarchy

## Pre-requisites

Java JDK 11 needs to be installed before running the tests.

## Running Tests

Use the following command to run the tests.

```bash ./gradlew test jacoTestReport```

The test results are here ```build/reports/tests/test/index.html``` in your browser
The test coverage results are here ```build/reports/jacoco/test/html/index.html```

The class ```UserRoles``` provides the function ```getSubOrdinates``` which, when passed a user id, will return
a list of all their subordinates

## Assumptions and Design Decisions
It has been assumed that the provided role ids is a consecutive list of integers starting at 0. The design could
be enhanced to remove this assumption by providing a mapping between the node numbers used
in the graph and the role ids.

An adjacency list directed graph is used to model the tree structure of the roles. This structure was chosen as
it provides a fairly efficient mechanism for traversing the graph along with the flexibility to support a directed
acyclic structure. An alternative and potentially simpler solution, of modelling the role structure using 
containment where the role contains a list of its child nodes, was also considered however it was felt that this
was a less feasible design.