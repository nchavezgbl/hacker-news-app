package io.pivotal.hacker.news.app;

import java.util.List;

public class InMemoryStoryRepository implements StoryRepository {

  private List<Story> storyList;

  public InMemoryStoryRepository(){

  }

  @Override
  public Story create(Story story){
    Story newStory = new Story(story.getId(), story.getTitle(),story.getUrl());
    storyList.add(newStory);
    return newStory;
  }
  @Override
  public List<Story> list() {
    return storyList;
  }
  @Override
  public void deleteAll(){
    storyList.clear();
  }
}
