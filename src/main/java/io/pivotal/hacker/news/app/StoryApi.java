package io.pivotal.hacker.news.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StoryApi {
  private StoryRepository storyRepository ;
  private final RestTemplate restTemplate = new RestTemplate();


  public StoryApi(StoryRepository timeEntriesRepo ) {
    this.storyRepository = timeEntriesRepo;
  }

  private void getStories() throws IOException{
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<String>(headers);

    String jsonStr = restTemplate.exchange("https://hacker-news.firebaseio.com/v0/topstories.json", HttpMethod.GET, entity, String.class).getBody();
    JSONArray allStories = new JSONArray(jsonStr);
    storyRepository.deleteAll();
    for(int i=0; i <10 ; i++){
      ObjectMapper objectMapper = new ObjectMapper();

      Story story = new Story();
      String jsonStoryStr = getJsonStory(allStories.get(i).toString());
      JsonNode rootNode = objectMapper.readTree(jsonStoryStr);
      JsonNode idNode = rootNode.path("id");
      JsonNode titleNode = rootNode.path("title");
      JsonNode urlNode = rootNode.path("url");
      story.setId(idNode.asText());
      story.setTitle(titleNode.asText());
      if(urlNode.asText().equals("")){
        String hackerNewsUrl = "https://news.ycombinator.com/item?id=" + story.getId();
        story.setUrl(hackerNewsUrl);
      }else{
        story.setUrl(urlNode.asText());
      }
      storyRepository.create(story);

    }

  }

  private String getJsonStory(String id){
    String url = "https://hacker-news.firebaseio.com/v0/item/" + id + ".json";
    HttpHeaders headers = new HttpHeaders();
    headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
    HttpEntity<String> entity = new HttpEntity<String>(headers);

    String jsonStr = restTemplate.exchange(url, HttpMethod.GET, entity, String.class).getBody();
    return jsonStr;
  }
}
