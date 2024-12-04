package hua.project.Entities;

import jakarta.persistence.*;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String FirstName;

    @Column
    private String LastName;

    @Column
    private String Email;

    @Column
    private String Phone;

}
