package blog.mazleo.ruvacant.ui.base;

import com.google.auto.value.AutoValue;
import java.util.List;

/** Base implementation for holding card values. */
@AutoValue
public abstract class BaseCardValue implements CardValue {

  public abstract String code();

  public abstract String title();

  public abstract String description();

  public abstract List<CardSegment> segments();

  public abstract CardValueColor colorTheme();

  public static Builder builder() {
    return new AutoValue_BaseCardValue.Builder();
  }

  @AutoValue.Builder
  public abstract static class Builder {
    public abstract Builder setCode(String code);

    public abstract Builder setTitle(String title);

    public abstract Builder setDescription(String description);

    public abstract Builder setSegments(List<CardSegment> segments);

    public abstract Builder setColorTheme(CardValueColor colorTheme);

    public abstract BaseCardValue build();
  }
}
