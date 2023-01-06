#!/bin/bash

# Create virtual machines locally
# docker-machine create --driver virtualbox myvm1
# docker-machine create --driver virtualbox myvm2
# docker-machine create --driver virtualbox myvm3

# Create the master
# docker-machine ssh myvm1
# docker swarm init --advertise-addr 192.168.99.103

# Join workers (from worker vms)
# docker swarm join --token SWMTKN-1-6az97y1e4d86vgz3syzui68k4ptob5r67ii0ba8txdut3ogwss-5gef6cofmoo4arw6xemsriiqh 192.168.99.101:2377
# docker node ls

# docker swarm join --token SWMTKN-1-3pojdzac0vxxcxjl9px6drkxpf9y5h86kw9qq5pjd02miderds-7jlexl0kjn3hpsa0djti5prth 192.168.0.8:2377

# On host
# docker-machine scp -r proiect_cloud/ myvm1:.

# On master
# Delete cached docker images
docker stack rm cloud
docker stack rm portainer
docker system prune -a

# Create service to host local docker images
docker service create --name registry --publish published=5000,target=5000 registry:2

cd Licenta-backend
docker build -f Dockerfile -t backend:latest . 
docker tag backend localhost:5000/backend
docker push localhost:5000/backend
cd ..

cd licenta-front-end
docker build -f Dockerfile -t frontend:latest . 
docker tag frontend localhost:5000/frontend
docker push localhost:5000/frontend
cd ..

cd procesator
docker build -f Dockerfile -t procesator:latest . 
docker tag procesator localhost:5000/procesator
docker push localhost:5000/procesator
cd ..

docker stack deploy -c docker-swarm.yml cloud
docker stack deploy -c docker-portainer.yml portainer

# verify on master
for i in {0..4}
do
	sleep 10
	docker stack ps cloud
done


