package tuk.mentor.domain.survey.entity;

public enum SurveyType {

    LECTURE("강의평가"),
    MENTOR("멘토평가"),
    SYSTEM("시스템평가");

    private final String surveyType;

    SurveyType(String surveyType) {
        this.surveyType = surveyType;
    }
}
