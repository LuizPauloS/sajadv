### Multi Stage Build ###
### Estágio 1 - Obter o source e gerar o build ###
FROM node:12.16.1-buster-slim AS builder

WORKDIR /app

RUN npm install && npm -v

COPY . /app
RUN $(npm bin)/ng build --configuration=production

### Estágio 2 - Subir o source para o servidor NGINX
FROM nginx:1.14.1-alpine
LABEL maintainer = 'Luiz Paulo da Silva'

## Copy our default nginx config
COPY nginx/default.conf /etc/nginx/conf.d/

## Remove default nginx website
RUN rm -rf /usr/share/nginx/html/*

## From ‘builder’ stage copy over the artifacts in dist folder to default nginx public folder
COPY --from=builder /app/dist/* /usr/share/nginx/html
RUN ls /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]