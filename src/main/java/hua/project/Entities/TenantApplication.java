package hua.project.Entities;
import jakarta.persistence.*;

@Entity
public class TenantApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id", nullable = false)
    private Property property;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column
    private double rentPrice;

    @Enumerated(EnumType.STRING)
    private Visit visit;

    public TenantApplication(int id, Status status, Property property, Owner owner, Tenant tenant, double rentPrice, Visit visit) {
        this.id = id;
        this.status = status;
        this.property = property;
        this.owner = owner;
        this.tenant = tenant;
        this.rentPrice = rentPrice;
        this.visit = visit;
    }

    public TenantApplication() {
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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public Tenant getTenant() {
        return tenant;
    }

    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Visit getVisit() {
        return visit;
    }

    public void setVisit(Visit visit) {
        this.visit = visit;
    }

    @Override
    public String toString() {
        return "TenantApplication{" +
                "id=" + id +
                ", status=" + status +
                ", property=" + property +
                ", owner=" + owner +
                ", tenant=" + tenant +
                ", rentPrice=" + rentPrice +
                ", visit=" + visit +
                '}';
    }
}
