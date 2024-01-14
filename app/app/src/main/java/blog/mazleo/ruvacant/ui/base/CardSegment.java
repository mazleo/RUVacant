package blog.mazleo.ruvacant.ui.base;

/** A card segment. */
public final class CardSegment {

  public static int GREEN_DEFAULT = 0xFF56FBBF;
  public static int RED_DEFAULT = 0xFFCA6F6F;

  private int percentage;
  private int color;

  public CardSegment(int percentage, int color) {
    this.percentage = percentage;
    this.color = color;
  }

  public int getPercentage() {
    return percentage;
  }

  public int getColor() {
    return color;
  }
}
