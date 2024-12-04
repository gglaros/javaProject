package hua.project.Entities;

import jakarta.persistence.*;

@Entity
public class OwnerApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

   private Boolean status;

    public OwnerApplication(int id, Boolean status) {
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

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
