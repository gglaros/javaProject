package hua.project.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="owner")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String email;

    @Column
    private String phone;

    @OneToMany(mappedBy = "owner", cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REMOVE,CascadeType.REFRESH})
    public List<Property> properties;

    @OneToMany( mappedBy = "owner" ,cascade= {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REMOVE,CascadeType.REFRESH})
    public List<OwnerApplication> ownerApplications;

    public Owner(int id, String firstName, String lastName, String email, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }


    public Owner() {
    }

    public List<OwnerApplication> getOwnerApplications() {
        return ownerApplications;
    }

    public void setOwnerApplications(List<OwnerApplication> ownerApplications) {
        this.ownerApplications = ownerApplications;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
