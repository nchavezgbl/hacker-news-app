package io.pivotal.hacker.news.app;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
@RequestMapping("/news")
public class NewsController {

    private StoryRepository storyRepository ;
    private List<Story> topTenList;

    public NewsController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @GetMapping("/topTenList")
    public String getTopStory(Model model) {
        try {
            topTenList = getStories();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(topTenList.isEmpty()){
            topTenList.add(new Story("1","story","url"));
        }else{
            System.out.println("!*!*!*!*!*!*!*!*!*!*!*!");
            System.out.println(topTenList.size());
            System.out.println("!*!*!*!*!*!*!*!*!*!*!*!");
        }
        //storyRepository.list()
        model.addAttribute("topTenList", topTenList);
        return "topTenList";
    }

    public List<Story> getStories() throws IOException{
        List<Story> stories = new ArrayList<Story>();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String jsonStr = new RestTemplate().exchange("https://hacker-news.firebaseio.com/v0/topstories.json", HttpMethod.GET, entity, String.class).getBody();
        JSONArray allStories = new JSONArray(jsonStr);
        //storyRepository.deleteAll();
        stories.clear();
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
            //storyRepository.add(story);
            stories.add(story);
        }
        return stories;
    }

    private String getJsonStory(String id){
        String url = "https://hacker-news.firebaseio.com/v0/item/" + id + ".json";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<String>(headers);

        String jsonStr = new RestTemplate().exchange(url, HttpMethod.GET, entity, String.class).getBody();
        return jsonStr;
    }



}
