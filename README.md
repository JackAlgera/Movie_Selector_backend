# Movie_Selector_backend
Spring boot service that handles the backend API calls for the Movie Selector app.

To start the service : `docker-compose up -d`

To stop the service and remove local data : `docker-compose down -v`

Create new tagged image : `docker build -t jackalgera/video_selector_backend:latest .`

Push image to Docker : `docker push jackalgera/video_selector_backend:latest`

Delete dangling images : `docker image prune`

To connect to local DB : 

`psql -h localhost -U movie_selector_user -d movie_selector_db `

List tables with :
`\dt`
