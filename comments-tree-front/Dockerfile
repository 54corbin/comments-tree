FROM node:12.22.4-alpine3.14
ENV LANG en_GB.UTF-8
RUN cat /etc/os-release
RUN npm -v

RUN mkdir -p /comments-tree-front
WORKDIR /comments-tree-front
COPY . /comments-tree-front
RUN ls /comments-tree-front

EXPOSE 8098
CMD npm run dev
