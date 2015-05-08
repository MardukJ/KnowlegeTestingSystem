package ua.epam.rd.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mykhaylo Gnylorybov on 05.05.2015.
 * email: mail@marduk.ru
 * skype: marduk.ru
 */

@Entity
@Table(name = "exams")
public class Exam {
    @Id
    @GeneratedValue
    @Column(name = "id_exam")
    private Long id;

    @Column (name = "exam_name")
    private String name = "";

    @Enumerated (EnumType.STRING)
    @Column (name = "status")
    private ExamStatus status = ExamStatus.NEW;

    @Enumerated (EnumType.STRING)
    @Column (name = "scoring_algorithm")
    private ScoringAlgorithm scoringAlgorithm = ScoringAlgorithm.DEFAULT;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (name = "start_time")
    private Date startWindowOpen  = new Date(0);

    @Column (name = "max_late_time_min")
    private Integer maxLateTimeInMinutes = new Integer(0);

    @Column (name = "test_time_min")
    private Integer testTimeInMinutes = new Integer(30);

    @Column (name = "show_results")
    private Boolean showResults = Boolean.TRUE;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "id_user_creator")
    private User creator = null;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(name = "join_exam_users",
    joinColumns = {@JoinColumn(name = "id_exam_ref", referencedColumnName = "id_exam")},
    inverseJoinColumns = {@JoinColumn(name = "id_user_ref", referencedColumnName = "id_user")})
    private List <User> recievers = new LinkedList<User>();

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable(name = "join_exam_questions",
            joinColumns = {@JoinColumn(name = "id_exam_ref", referencedColumnName = "id_exam")},
            inverseJoinColumns = {@JoinColumn(name = "id_question_ref", referencedColumnName = "id_question")})
    private List <Question> questions = new LinkedList<Question>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        throw new UnsupportedOperationException();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ExamStatus getStatus() {
        return status;
    }

    public void setStatus(ExamStatus status) {
        throw new UnsupportedOperationException();
    }

    public ScoringAlgorithm getScoringAlgorithm() {
        return scoringAlgorithm;
    }

    public void setScoringAlgorithm(ScoringAlgorithm scoringAlgorithm) {
        this.scoringAlgorithm = scoringAlgorithm;
    }

    public Date getStartWindowOpen() {
        return startWindowOpen;
    }

    public void setStartWindowOpen(Date startWindowOpen) {
        this.startWindowOpen = startWindowOpen;
    }

    public Integer getMaxLateTimeInMinutes() {
        return maxLateTimeInMinutes;
    }

    public void setMaxLateTimeInMinutes(Integer maxLateTimeInMinutes) {
        this.maxLateTimeInMinutes = maxLateTimeInMinutes;
    }

    public Integer getTestTimeInMinutes() {
        return testTimeInMinutes;
    }

    public void setTestTimeInMinutes(Integer testTimeInMinutes) {
        this.testTimeInMinutes = testTimeInMinutes;
    }

    public Boolean getShowResults() {
        return showResults;
    }

    public void setShowResults(Boolean showResults) {
        this.showResults = showResults;
    }

    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    public List<User> getRecievers() {
        return recievers;
    }

    public void setRecievers(List<User> recievers) {
        throw new UnsupportedOperationException();
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        throw new UnsupportedOperationException();
    }
}
