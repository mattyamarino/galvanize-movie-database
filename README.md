# galvanize-movie-database


|(URI)|(HTTP Method)|(HTTP Status)| (Description) |(Request) | (Response)
| ------------- | ------------- | ------------- | ------------- | ------------- | ------------- |
| "/movies/" | GET | 200 | returns all movies in database | | [{"title": "The Avengers","director": "Joss Whedon","actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth","release": "2012","description": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.","starRating": 0.0},{"title": "Superman Returns","director": "Bryan Singer","actors": "Brandon Routh, Kate Bosworth, Kevin Spacey, James Marsden","release": "2006","description": "Superman returns to Earth after spending five years in space examining his homeworld Krypton. But he finds things have changed while he was gone, and he must once again prove himself important to the world.","starRating": null} |
| "/movies/{title}" | GET | 200 | returns movie from database by title | | {"title": "The Avengers","director": "Joss Whedon","actors": "Robert Downey Jr., Chris Evans, Mark Ruffalo, Chris Hemsworth","release": "2012","description": "Earth's mightiest heroes must come together and learn to fight as a team if they are going to stop the mischievous Loki and his alien army from enslaving humanity.","starRating": 0.0} |
| "/movies/{title}" | GET | 401 | throws MovieNotFoundException if movie is not present in database | |
| "/movies/{title}" | PATCH | 200 | adds review to selected movie | {"rating": 3, "description": "this movie was excessively ok"} | |
| "/movies/{title}" | PATCH | 401 |throws InvalidReviewException if user doesnt provide rating or rating is not between 1 and 5 | {"rating": 7, "description": "this movie was excessively ok"} | |
