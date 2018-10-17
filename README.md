# WareHouse
This is an open-source warehouse management system.

## Features
* Registration of products (Brand,Type...)
* Registration, deletion of storage rooms
* Registration, update and deletion of customers
* Customer specific discount percentage 
* Quantity per product in each storage room
* Summary table for all products, total quantity and storage rooms that they exist in
* Search function based on Product Code, Brand or Type
* Exact or approximate search
* Reporter frame with products that are in low quantities (shortage)

## How to contribute
1. fork the repo to your own profile
2. select an issue
3. create a new branch with the name "feature/issueNum_anyOtherNaming" (feature/3_myBranch)
4. write your code
5. commit and push to your repo
6. compare and pull request against this repo's master

## Tools used in this project
* Netbeans and java 8
* SQLite studio

## Test
* This project uses [JUnit 5](https://junit.org/junit5/)
* Tests should go in the `test` directory. Mirror the same package structure in `test` as in `main`
* Test classes meeting the regex *Test.java will automatically be included by the framework's test runner
* In order to run the tests, you must have [Maven](https://maven.apache.org/) installed
* Run the tests by running `mvn test` from the command-line.

# Important
* **Do not forget** to commit also the maven dependencies file if you add new dependencies!
* **Never commit** changes to the storage.db file, nor changes to the application main password



