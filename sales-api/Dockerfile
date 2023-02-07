FROM node:18.13.0
WORKDIR .
COPY package*.json ./
RUN yarn 
COPY . .
EXPOSE 8082
CMD ["node", "app.js"]