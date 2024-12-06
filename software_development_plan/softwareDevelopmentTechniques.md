# Software Development Techniques
## Version Control

We are going to use Git with GitHub.
The main branch is set up to require a review before a branch is merged, i.e. one other person from the team  must review a pull request before can be merged with main.

## Team Workflows

We will use GitHub's [kanban](https://github.com/users/harryz8/projects/1) to keep track of issues' statuses and who they are assigned to.

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

We will write JUnit tests for functions that integrate others, when we are checking the integration of separately developed sections.

### Formal Verification

We don't think that this type of testing is applicable to this project as most of our functions have side effects and are not mathematically pure.

### A/B Tests

We will run intensive testing through a beta release to a select percentage of users until enough
feedback is collected. If required, tweaks to the program will be made.

## Build Tools

We are going to use Gradle as our build tool.

## Dependencies

- JavaFX
- JUnit
- org.postgresql:postgresql:42.6.0
- org.hibernate.orm:hibernate-core:6.6.3.Final

## Continuous Integration

We will use a GitHub workflow to automate continuous integration.

## Containerisation

We are going to use containerisation, because it allows the postgres database to be bundled with the rest of the program when distributing and is useful when using Hybernate.
We made a docker file in our 5th of December meeting. We set it in our project file and modify it so that it suits our work after we'd faced some barriers in installation and setting things which are required up as we noticed some errors.

## Observability

## Orchestration
