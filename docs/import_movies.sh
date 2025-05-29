#!/bin/bash

TABLE_NAME="Movie"
JSON_FILE="movies_sample_data.json"

jq -c '.[]' "$JSON_FILE" | while IFS= read -r movie; do
  aws dynamodb put-item \
    --table-name "$TABLE_NAME" \
    --item "$(echo "$movie" | jq '{
      "id": {"S": .id},
      "platform": {"S": .platform},
      "movieId": {"S": .movieId},
      "title": {"S": .title},
      "director": {"S": .director},
      "year": {"N": (.year | tostring)},
      "releasedDate": {"S": .releasedDate},
      "genre": {"S": .genre},
      "actors": {"SS": .actors},
      "synopsis": {"S": .synopsis},
      "rating": {"S": .rating},
      "imdbId": {"S": .imdbId},
      "imdbRating": {"N": (.imdbRating | tostring)},
      "imageUrl": {"S": .imageUrl},
      "platforms": {"SS": .platforms},
      "duration": {"N": (.duration | tostring)}
    }')"
done

echo "✅ Películas insertadas en la tabla $TABLE_NAME."


