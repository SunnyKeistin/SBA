FROM node:11.3 as builder
RUN mkdir /usr/src/app
WORKDIR /usr/src/app
ENV PATH /usr/src/app/node_modules/.bin:$PATH
COPY package.json /usr/src/app/package.json
RUN npm install --registry https://registry.npm.taobao.org
COPY . /usr/src/app
RUN npm run build

FROM nginx:latest
RUN rm -f /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d/
COPY --from=builder /usr/src/app/dist/fsd-frontend /usr/share/nginx/html
EXPOSE 81
CMD ["nginx", "-g", "daemon off;"]