run:
	docker-compose -f postgres-compose.yml up -d && \
	docker pull javac7/big-data-challenge:0.1 && \
	docker run -d --name big-data-challenge --restart=always \
	-v `pwd`/datasets/:/datasets \
	--net="host" javac7/big-data-challenge:0.1
setup:
	echo "docker image ready to run"

test:
	echo "missing docker run tests"
