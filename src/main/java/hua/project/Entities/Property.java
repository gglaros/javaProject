package hua.project.Entities;

import jakarta.persistence.*;

@Entity
@Table(name="property")
public class Property {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private Double rentPrice;

    @Column
    private Boolean status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="owner_id")
   private Owner owner;



}
