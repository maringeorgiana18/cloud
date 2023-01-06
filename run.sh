docker rm -f bookshop
docker rm -f booklibrary
docker rm -f authserver

docker image rm proiect_idp_bookservice_booklibrary
docker image rm proiect_idp_bookservice_bookshop
docker image rm proiect_idp_bookservice_authserver

cd Bookshop
mvn clean install -DskipTests
cd ..
cd Booklibrary
mvn clean install -DskipTests
cd ..
cd Authserver
mvn clean install -DskipTests

docker-compose up -d
