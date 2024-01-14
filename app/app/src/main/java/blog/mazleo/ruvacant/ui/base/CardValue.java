package blog.mazleo.ruvacant.ui.base;

import java.util.List;

/** Holds the cards' information. */
public interface CardValue {

  String code();

  String title();

  String description();

  List<CardSegment> segments();

  CardValueColor colorTheme();
}
