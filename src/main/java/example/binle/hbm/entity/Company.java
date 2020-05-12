package example.binle.hbm.entity;

import javax.persistence.*;

/**
 * Entity annotated class is registered in hibernate.cfg.xml
 */
@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue  // default strategy is AUTO
    private long id;

    @ManyToOne
    @JoinColumn(name = "business_id", foreignKey = @ForeignKey(name="BUSINESS_ID_FK"))
    private Business business;

    // annotation of field is optional and useful for custom setting
    // if @Column annotation is not specified, property name will be used as the column name by default.
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Business getBusiness() {
        return business;
    }

    public void setBusiness(Business business) {
        this.business = business;
    }

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", business=" + business +
                ", name='" + name + '\'' +
                '}';
    }
}
