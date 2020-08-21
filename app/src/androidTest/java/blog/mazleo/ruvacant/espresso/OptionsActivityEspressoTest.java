package blog.mazleo.ruvacant.espresso;

import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import blog.mazleo.ruvacant.R;
import blog.mazleo.ruvacant.activities.OptionsActivity;

import static androidx.test.espresso.Espresso.onView;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.action.ViewActions;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class OptionsActivityEspressoTest {

    @Rule
    public ActivityTestRule<OptionsActivity> activityTestRule = new ActivityTestRule<>(OptionsActivity.class);

    @Test
    public void selectSemesterOption() {
        onView(ViewMatchers.withId(R.id.options_semester_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Summer 2020")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_semester_button)).check(ViewAssertions.matches(ViewMatchers.withText("Summer 2020")));

        onView(ViewMatchers.withId(R.id.options_semester_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Spring 2020")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_semester_button)).check(ViewAssertions.matches(ViewMatchers.withText("Spring 2020")));

        onView(ViewMatchers.withId(R.id.options_semester_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Winter 2019")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_semester_button)).check(ViewAssertions.matches(ViewMatchers.withText("Winter 2019")));

        onView(ViewMatchers.withId(R.id.options_semester_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Fall 2019")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_semester_button)).check(ViewAssertions.matches(ViewMatchers.withText("Fall 2019")));
    }

    @Test
    public void selectCampusOption() {
        onView(ViewMatchers.withId(R.id.options_campus_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("New Brunswick")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_campus_button)).check(ViewAssertions.matches(ViewMatchers.withText("New Brunswick")));

        onView(ViewMatchers.withId(R.id.options_campus_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Newark")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_campus_button)).check(ViewAssertions.matches(ViewMatchers.withText("Newark")));

        onView(ViewMatchers.withId(R.id.options_campus_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Camden")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_campus_button)).check(ViewAssertions.matches(ViewMatchers.withText("Camden")));
    }

    @Test
    public void selectLevelOption() {
        onView(ViewMatchers.withId(R.id.options_level_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Undergraduate")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_level_button)).check(ViewAssertions.matches(ViewMatchers.withText("Undergraduate")));

        onView(ViewMatchers.withId(R.id.options_level_button)).perform(ViewActions.click());
        onView(ViewMatchers.withText("Graduate")).perform(ViewActions.click());
        onView(ViewMatchers.withId(R.id.options_level_button)).check(ViewAssertions.matches(ViewMatchers.withText("Graduate")));
    }
}
