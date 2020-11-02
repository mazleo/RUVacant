package blog.mazleo.ruvacant.repository;

import java.util.List;

import blog.mazleo.ruvacant.model.CourseInfoCollection;
import blog.mazleo.ruvacant.model.Option;
import blog.mazleo.ruvacant.model.Subject;
import blog.mazleo.ruvacant.service.webservice.CourseInfoWebService;
import blog.mazleo.ruvacant.service.webservice.SubjectsWebService;

public class CoursesRepository implements RepositoryInstance {
    private CoursesViewModel coursesViewModel;
    private SubjectsWebService subjectsWebService;
    private CourseInfoWebService courseInfoWebService;
    private Option selectedOptions;

    public CoursesRepository(CoursesViewModel coursesViewModel) {
        this.coursesViewModel = coursesViewModel;
        this.subjectsWebService = null;
        this.courseInfoWebService = null;
        this.selectedOptions = null;
    }

    public CoursesRepository(CoursesViewModel coursesViewModel, Option selectedOptions) {
        this.coursesViewModel = coursesViewModel;
        this.selectedOptions = selectedOptions;
        this.subjectsWebService = null;
        this.courseInfoWebService = null;
    }

    public void initiateSubjectsServiceDownload(Option selectedOption) {
        this.subjectsWebService = new SubjectsWebService(this);
        this.subjectsWebService.downloadSubjects(selectedOption);
    }

    public void onSubjectsDownloadComplete(List<Subject> subjects) {
        passDownloadedSubjects(subjects);

        this.subjectsWebService.cleanUp();
        this.subjectsWebService = null;

        initiateCourseInfoServiceDownload(subjects, selectedOptions);
    }

    private void passDownloadedSubjects(List<Subject> subjects) {
        this.coursesViewModel.onSubjectsRetrieved(subjects);
    }

    private void initiateCourseInfoServiceDownload(List<Subject> subjects, Option selectedOptions) {
        this.courseInfoWebService = new CourseInfoWebService(this);
        this.courseInfoWebService.downloadCourseInfos(subjects, selectedOptions);
    }

    public void onCourseInfosDownloadComplete(List<CourseInfoCollection> courseInfos) {
        passDownloadedCourseInfos(courseInfos);

        this.courseInfoWebService.cleanUp();
        this.courseInfoWebService = null;

        cleanUp();
    }

    private void passDownloadedCourseInfos(List<CourseInfoCollection> courseInfos) {
        this.coursesViewModel.onCourseInfosRetrieved(courseInfos);
    }

    public void passError(Throwable e) {
        this.coursesViewModel.passError(e);
    }

    public void cleanUp() {
        this.coursesViewModel = null;

        this.selectedOptions = null;

        if (this.subjectsWebService != null) {
            this.subjectsWebService.cleanUp();
            this.subjectsWebService = null;
        }

        if (this.courseInfoWebService != null) {
            this.courseInfoWebService.cleanUp();
            this.courseInfoWebService = null;
        }
    }
}
