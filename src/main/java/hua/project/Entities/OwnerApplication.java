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

    @Column
    private String oFirstName;

    @Column
    private String oLastName;

    @Column
    private String oPhoneNumber;

    public OwnerApplication(int id, Status status, String oFirstName, String oLastName, String oPhoneNumber) {
        this.id = id;
        this.status = status;
        this.oFirstName = oFirstName;
        this.oLastName = oLastName;
        this.oPhoneNumber = oPhoneNumber;
    }

    public OwnerApplication() {
    }

    public String getoFirstName() {
        return oFirstName;
    }

    public void setoFirstName(String oFirstName) {
        this.oFirstName = oFirstName;
    }

    public String getoLastName() {
        return oLastName;
    }

    public void setoLastName(String oLastName) {
        this.oLastName = oLastName;
    }

    public String getoPhoneNumber() {
        return oPhoneNumber;
    }

    public void setoPhoneNumber(String oPhoneNumber) {
        this.oPhoneNumber = oPhoneNumber;
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
