# Software Development

## Requirements and Design

See the original [EDR](./SSH_EDR.pdf). Any subsequent changes made will be outlined in the repo documentation or [here](./EDRChanges.md).

## Testing

### Manual Tests

We will carry out manual tests on a function during its development.

### Unit Tests

We will carry out unit tests on the Java code using the JUnit module.
We will also carry out tests on the SQL database.

We plan to:
1. Write stubs for functions
2. Write tests for these functions
3. Then implement these functions, making them pass the tests

### Integration Tests

We are going to test the program as a whole using Python scripts that simulate different user inputs and verify outputs. We will also run unit tests each time two separately developed parts are integrated.

### Formal Verification

We don't think that this type of testing is applicable to this project as most of our functions have side effects and are not mathematically pure.

### A/B Tests

We will run intensive testing through a beta release to a select percentage of users until enough
feedback is collected. If required, tweaks to the program will be made.

## Deployment

We will set the latest version of the code as a release in the GitHub repository, and keep version changes tagged with a version number. This is a prototype so won't be properly deployed but makes sure that if someone wanted to download the latest version from the GitHub website, they can easily find it.

## Version Control

We are going to use Git with GitHub.
The main branch is set up to require a review before a branch is merged, i.e. one other person from the team  must review a pull request before can be merged with main.

## Team Workflows

We will use GitHub's [kanban](https://github.com/users/harryz8/projects/1) to keep track of issues' statuses and who they are assigned to.

## Build Tools

We are using Gradle as our build tool, and to handle dependencies.

## Dependencies

- JavaFX (is a plugin)
- JUnit
- org.postgresql:postgresql:42.6.0
- org.hibernate.orm:hibernate-core:6.6.3.Final
- jakarta
- jboss-logging
- fasterxml.classmate
- bytebuddy
- avast.docker-compose (is a plugin)

## Continuous Integration

We have used GitHub workflows to integrate this. When a pull request is made, the program is built on a system running the latest version of Ubuntu, hosted by GitHub. The pull request should not be merged unless the build is successful on Java 17, 21 and 23 and all tests pass. This is clearly shown in the pull request when somebody goes to approve it. 

## Containerisation

We are using containerisation allowing the postgres database to be bundled with the rest of the program when distributing and is useful when using Hibernate.
We made a docker file in our 5th of December meeting. We set it in our project file and modify it so that it suits our work after we'd faced some barriers in installation and setting things which are required up as we noticed some errors.

We are not going to use containerisation to distribute the program as it doesn't work very well with JavaFX.
