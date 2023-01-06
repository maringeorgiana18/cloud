docker stop $(docker ps -a -q)
docker rm $(docker ps -a -q)

docker image prune


# sudo docker stop $(sudo docker ps -a -q)
# sudo docker rm $(sudo docker ps -a -q)
# sudo docker image prune
# sudo docker system prune -a
