package example.binle.hbm.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity annotated class is registered in hibernate.cfg.xml
 */
@Entity
@Table(name = "company")
public class Company {

    @Id
    private long id;

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

    @Override
    public String toString() {
        return "Company{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
