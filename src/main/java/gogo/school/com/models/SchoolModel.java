package gogo.school.com.models;

import java.util.Date;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SchoolOfTheNepal")
@Getter
@Setter
@NoArgsConstructor
public class SchoolModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "school_name", nullable = false)
    private String schoolName;

    @Column(name = "school_address", nullable = false)
    private String schoolAddress;

    @Column(name = "school_email", nullable = false, unique = false)
    private String schoolEmail;

    @Column(name = "school_phone_number", nullable = false, unique = true)
    private String schoolPhoneNumber;

    @OneToMany(mappedBy = "schoolOfTheNepal", cascade = CascadeType.REMOVE)
    @JsonManagedReference("manage-batch")
    private Set<BatchesModel> batches;

    @OneToMany(mappedBy = "schoolModel", cascade = CascadeType.REMOVE)
    @JsonBackReference("manage-teacher")
    private Set<TeacherModel> teachers;

    @Column(name = "created_at")
    private Date createdDate;

    @Column(name = "updated_at")
    private Date updateDate;

    public SchoolModel(String schoolName, String schoolAddress, String schoolEmail, String schoolPhoneNumber,
            Date update_date) {
        this.schoolName = schoolName;
        this.schoolAddress = schoolAddress;
        this.schoolEmail = schoolEmail;
        this.schoolPhoneNumber = schoolPhoneNumber;
        this.createdDate = new Date();
        this.updateDate = update_date;
    }

    @PrePersist
    public void onCreate() {
        this.createdDate = new Date();
        this.updateDate = new Date();
    }

    @PreUpdate
    public void onUpdate() {
        this.updateDate = new Date();
    }
}
