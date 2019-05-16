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

    public NewsController(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
    }

    @GetMapping("/topTenList")
    public String getTopStory(Model model) {
        model.addAttribute("topTenList", storyRepository.list());
        return "topTenList";
    }



}
