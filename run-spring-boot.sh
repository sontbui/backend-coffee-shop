#!/bin/bash

export SERVER_PORT=8088
export MONGO_URI="mongodb+srv://sonbuithanh306:sonbuidatabase2708@cluster-database-mongod.fs2ls.mongodb.net/?retryWrites=true&w=majority&appName=Cluster-DataBase-MongoDB"
export MONGO_DATABASE=database
export CLOUDINARY_CLOUD_NAME=your_cloud_name
export CLOUDINARY_API_KEY=your_api_key
export CLOUDINARY_API_SECRET=your_api_secret
export JWT_SECRET="L/wL4jvof6OEpsQcnvcprhW1rT0+m89GOo6v5lnZPcLZ5xR5qpO4Epwm51KSzpGUici20cpYmkYPbaJ65/jFLQ=="
export JWT_EXPIRATION_TIME=3600
export CORS_ALLOWED_ORIGINS="http://localhost:3000,http://yourfrontend.com"

./mvnw spring-boot:run
