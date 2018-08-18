package it.elia.paginated.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "SOMETHING")
public class Something {
    @Id
    @Column(name="ID", nullable=false)
    private long id;

    @Column(name="FIRST_NAME")
    private String firstName;

    @Column(name="LAST_NAME")
    private String lastName;

    @Column(name="AVG_SCORE")
    private float avgScore;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public float getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(float avgScore) {
        this.avgScore = avgScore;
    }

    public Something(long id, String firstName, String lastName, float avgScore) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avgScore = avgScore;
    }

    public Something() {
    }
}
