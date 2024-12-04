package hua.project.Entities;

import jakarta.persistence.*;

@Entity
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private Double rentPrice;

    @Column
    private Boolean status;

}
