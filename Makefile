run:
	mkdir -p /home/datasets/ && \
	sudo chmod 777 /home/datasets/ && \
	docker-compose -f postgres-compose.yml up -d && \
	docker run -d --name big-data-challenge --restart=always \
	-v /home/datasets/:/opt/docker/bin/datasets/ \
	--net="host" javac7/big-data-challenge:0.1
setup:
	docker pull javac7/big-data-challenge:0.1
test:
	docker exec -it big-data-challenge bash -c "sbt test"
