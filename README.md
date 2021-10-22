![Build Status](https://github.com/patroklossam/WareHouse/actions/workflows/build.yml/badge.svg)
[![codecov](https://codecov.io/gh/patroklossam/WareHouse/branch/master/graph/badge.svg)](https://codecov.io/gh/patroklossam/WareHouse)

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

## Tools used in this project
* Netbeans and java 8
* SQLite studio

## Testing

See [CONTRIBUTING.md](./CONTRIBUTING.md)

# Important
* **Do not forget** to commit also the maven dependencies file if you add new dependencies!
* **Never commit** changes to the storage.db file, nor changes to the application main password
