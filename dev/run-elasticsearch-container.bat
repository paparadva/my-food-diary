docker rm --force elasticsearch
docker run --name elasticsearch -d -p 9200:9200 -e "discovery.type=single-node" -e "ES_JAVA_OPTS=-Xmx2g" docker.elastic.co/elasticsearch/elasticsearch:7.17.6