package it.elia.paginated.entity;

public class SomethingBuilder {
    private long id;
    private String firstName;
    private String lastName;
    private float avgScore;

    public SomethingBuilder setId(long id) {
        this.id = id;
        return this;
    }

    public SomethingBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public SomethingBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public SomethingBuilder setAvgScore(float avgScore) {
        this.avgScore = avgScore;
        return this;
    }

    public Something createSomething() {
        return new Something(id, firstName, lastName, avgScore);
    }
}