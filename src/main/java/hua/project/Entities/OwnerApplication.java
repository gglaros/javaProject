package hua.project.Entities;

import jakarta.persistence.*;

@Entity
public class OwnerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

   @Enumerated(EnumType.STRING)
   private Status status;

    public OwnerApplication(int id, Status status) {
        this.id = id;
        this.status = status;
    }

    public OwnerApplication() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
