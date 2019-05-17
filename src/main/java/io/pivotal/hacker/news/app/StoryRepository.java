package io.pivotal.hacker.news.app;

import java.util.List;

public interface StoryRepository {
  public List<Story> list() ;
  public void deleteAll();
  public Story add(Story story);
}
