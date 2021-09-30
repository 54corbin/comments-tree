#!/bin/sh
echo "build backend image....."

docker build -t comments-tree-backend ./comments-tree-backend
echo "build front image....."
docker build -t comments-tree-front ./comments-tree-front


docker run -d --rm --name backend -p 8099:8099 comments-tree-backend&
sleep 5
#后端下载依赖需要较长时间
echo "启动中......"
sleep 20
echo "启动过程中后端会下载大量依赖......"
sleep 20
echo "请耐心等待......"
sleep 30
echo "整个过程将持续约5分钟......"
sleep 60

docker run -d --rm --name front --link backend:backend -p 8098:8098 comments-tree-front
sleep 10

open http://127.0.0.1:8098

docker logs -f backend
