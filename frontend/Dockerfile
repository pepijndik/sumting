FROM node:lts-alpine as develop-stage
# develop stage
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .

# build stage
FROM develop-stage as build-stage
RUN npm run build

# production stage
FROM nginx:1.23.2-alpine as production-stage
COPY nginx/nginx.conf /etc/nginx/nginx.conf
## Remove default nginx index page
RUN rm -rf /usr/share/nginx/html/*

COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80/tcp
EXPOSE 443/tcp
CMD ["nginx", "-g", "daemon off;"]