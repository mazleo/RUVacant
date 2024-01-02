package blog.mazleo.ruvacant.service.database;

import javax.inject.Inject;
import javax.inject.Singleton;

/** RuVacant database injected wrapper class. */
@Singleton
public final class RuVacantDatabase {

  private ApplicationDatabase database;

  @Inject
  RuVacantDatabase() {}

  public void initialize(ApplicationDatabase database) {
    this.database = database;
  }

  public ApplicationDatabase getDatabase() {
    return database;
  }
}
