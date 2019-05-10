package io.pivotal.hacker.news.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/news")
public class Controller {
    @Autowired
    RestTemplate restTemplate;

    private static List<Story> topTenStories = new ArrayList<Story>();

    @GetMapping("/top-news")
    public String getTopStory(Model model) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);


        String jsonStr = restTemplate.exchange("https://hacker-news.firebaseio.com/v0/topstories.json", HttpMethod.GET, entity, String.class).getBody();
        JSONArray allStories = new JSONArray(jsonStr);
        JSONArray topTen = new JSONArray();
        ObjectMapper objectMapper = new ObjectMapper();
        Story story = new Story();
        String jsonStrTopTen="";
        for(int i=0; i <10 ; i++){
            String jsonStoryStr = getJsonStory(allStories.get(i).toString());
            try {
                JsonNode rootNode = objectMapper.readTree(jsonStoryStr);
                JsonNode idNode = rootNode.path("id");
                JsonNode titleNode = rootNode.path("title");
                JsonNode urlNode = rootNode.path("url");
                story.setId(idNode.asText());
                story.setTitle(titleNode.asText());
                story.setUrl(urlNode.asText());
                topTenStories.add(story);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //story = objectMapper.readValue(jsonStoryStr, Story.class);

            JSONObject jsonStory = new JSONObject(jsonStoryStr);
            jsonStrTopTen = jsonStrTopTen + story.toString() + "\n";
            topTen.put(jsonStory);

        }
        model.addAttribute("topTenStories", topTenStories);
        return jsonStrTopTen;
        //return "top-news";
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
