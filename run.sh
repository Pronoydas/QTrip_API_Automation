#! /bin/bash
clear

mvn clean

mvn test

allure generate --clean  ./target/allure-results