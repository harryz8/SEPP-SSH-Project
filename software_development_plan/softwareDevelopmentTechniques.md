# Software Development Techniques
## Version Control

We are going to use Git with GitHub.
The main branch is set up to require a review before a branch is merged, i.e. one other person from the team  must review a pull request before can be merged with main.

## Team Workflows

We will use GitHub's [kanban](https://github.com/users/harryz8/projects/1) to keep track of issues' statuses and who they are assigned to.

## Testing

### Manual Tests

We will carry out manual tests on a function during it's development.

### Unit Tests

We will carry out unit tests on the Java code using the JUnit module.
We will also carry out tests on the SQL database.

We plan to:
1. Write stubs for functions
2. Write tests for these functions
3. Then implement these functions, making them pass the tests

### Integration Tests

During meetings, we will test integration of issues completed during the previous half-week.

### Property Based Tests

### Formal Verification

### A/B Tests

We will run intensive testing through a beta release to a select percentage of users until enough
feedback is collected. If required, tweaks to the program will be made.

### Integration Tests

### Other Types of tests go here

## Build Tools

We are going to use Gradle as our build tool.

## Dependencies

- JavaFX
- JUnit
- org.postgresql:postgresql:42.6.0
- org.hibernate. ⬅️ not finised, see lecture notes

## Continuous Integration

We will use a GitHub workflow to automate continuous integration.

## Containerisation

Do we need to use containerisation?

Maybe we should write a container that manages the database so that when someone installs it through docker, the database doesn't have to be set up maually, and it hosts it on a port.

## Observability

## Orchestration
