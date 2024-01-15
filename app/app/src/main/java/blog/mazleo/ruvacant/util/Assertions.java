package blog.mazleo.ruvacant.util;

/** Assertions util class. */
public final class Assertions {
  public static <T> T assertNotNull(T nullable) {
    if (nullable == null) {
      throw new AssertionError();
    }
    return nullable;
  }
}
