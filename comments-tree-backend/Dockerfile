FROM maven:3.8.2-openjdk-11
ENV LANG en_GB.UTF-8
RUN cat /etc/os-release
RUN java --version

RUN mkdir -p /comment_tree
WORKDIR /comment_tree
COPY . /comment_tree/comments-tree-backend
RUN ls /comment_tree/

EXPOSE 8099
#ENTRYPOINT /comment_tree/docker_entry.sh
CMD mvn clean spring-boot:run -f /comment_tree/comments-tree-backend
