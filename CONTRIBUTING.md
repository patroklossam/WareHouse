# Contributing to Warehouse

## Reporting Issues

Please report issues through the public issue tracker.

## How to contribute
1. Fork the repo to your own profile.
2. Select an issue.
3. Create a new branch with the name "feature/issueNum_anyOtherNaming" (feature/3_myBranch).
4. Write your code.
5. Commit and push to your repo.
6. Compare and pull request against this repo's master branch.

## Tips
- Smaller changes are easier to review
- Write tests for your changes 
    - This project uses [JUnit 5](https://junit.org/junit5/)
    - Tests should go in the `test` directory. Mirror the same package structure in `test` as in `main`
    - Test classes meeting the regex *Test.java will automatically be included by the framework's test runner
    - In order to run the tests, you must have [Maven](https://maven.apache.org/) installed
    - Run the tests by running `mvn test` from the command-line.
- Document the fix in the code to make the code more readable
- File a PR with meaningful title, description and commit messages. A good example is [PR-3306](https://github.com/swagger-api/swagger-codegen/pull/3306)
- To close an issue (e.g. issue 1542) automatically after a PR is merged, use keywords "fix", "close", "resolve" in the PR description, e.g. `fix #51`. (Ref: [closing issues using keywords](https://help.github.com/articles/closing-issues-using-keywords/))