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
    private String city;

    @Column
    private String description;

    @Column
    private String address;

    @Column
    private Double rentPrice;



    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="owner_id")
   private Owner owner;

    public Property(int id, String description, String address, Double rentPrice, Status status, Owner owner,String city) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.rentPrice = rentPrice;
        this.status = status;
        this.owner = owner;
        this.city = city;
    }

    public Property() {
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }



    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }


    @Override
    public String toString() {
        return "Property{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", rentPrice=" + rentPrice +
                ", status=" + status +
                ", owner=" + owner +
                '}';
    }
}
