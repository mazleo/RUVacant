package blog.mazleo.ruvacant.util;

import java.util.Arrays;

/** Formats strings. */
public final class StringFormatter {
  public static String capitalize(String string) {
    return Arrays.stream(string.split(" "))
        .map(
            word ->
                String.format(
                    "%s%s",
                    word.substring(0, 1).toUpperCase(),
                    word.substring(1, word.length()).toLowerCase()))
        .reduce("", (str1, str2) -> str1 + " " + str2, (str1, str2) -> str1 + " " + str2);
  }

  public static String limitChars(String string, int numChars) {
    if (string.length() > numChars) {
      return String.format("%s%s", string.substring(0, numChars), "...");
    }
    return string;
  }
}
