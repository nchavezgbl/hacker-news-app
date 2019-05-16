package io.pivotal.hacker.news.app;

import java.util.List;

public interface StoryRepository {
  public List<Story> list() ;
  public void deleteAll();
  public Story create(Story story);
}
