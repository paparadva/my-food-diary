docker rm --force myfooddiary-db
docker run -d --name myfooddiary-db -p 5432:5432 -e POSTGRES_DB=myfooddiary -e POSTGRES_PASSWORD=postgres postgres:14