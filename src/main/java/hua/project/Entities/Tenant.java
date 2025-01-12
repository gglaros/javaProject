package hua.project.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
public class Tenant {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column
        private int id;

        @Column(nullable = false)
        @Size(min = 3, max = 50)
        private String firstName;

        @Column(nullable = false)
        private String lastName;

        @Column(nullable = false, unique = true)
        @Email
        private String email;

        @Column(nullable = false)
        private String phone;

        @OneToMany(mappedBy = "tenant", cascade = CascadeType.ALL, orphanRemoval = true)
        private List<TenantApplication> tenantApplications;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;



    public Tenant(int id, String firstName, String lastName, String email, String phone, List<TenantApplication> tenantApplications) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.tenantApplications = tenantApplications;
    }

    public Tenant() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

        public List<TenantApplication> getTenantApplications() {
            return tenantApplications;
        }

        public void setTenantApplications(List<TenantApplication> tenantApplications) {
            this.tenantApplications = tenantApplications;
    }

    @Override
    public String toString() {
        return "Tenant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}

