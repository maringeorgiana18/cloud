FROM node:14.15.5 AS builder
WORKDIR /app
COPY . .
RUN npm install && \
    npm run build --force
FROM nginx:alpine
COPY --from=builder /app/dist/* /usr/share/nginx/html/
COPY /nginx.conf /etc/nginx/conf.d/default.conf
