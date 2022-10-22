# My Food Diary Backend

My Food Diary is a nutrition calculator.

The service requires a Postgres database:

> docker run -d --name myfooddiary-db -p 5432:5432 -e POSTGRES_DB=myfooddiary -e POSTGRES_PASSWORD=postgres postgres:14

Run the app with:

> ./gradlew bootRun
