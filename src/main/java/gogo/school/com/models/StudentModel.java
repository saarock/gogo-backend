package gogo.school.com.models;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "student")
public class StudentModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "email")
    private String email;

    @Column(name = "PhoneNumber")
    private String phoneNumber;

    @Column(name = "isActive")
    private boolean isActive;

    @Column(name = "parentPhoneNumber")
    private String parentPhoneNumber;

    @Column(name = "parentEmail")
    private String parentEmail;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "section_id", nullable = false)
    @JsonBackReference
    private SectionModel section;

    @ManyToMany(mappedBy = "students")
    private List<AttendanceModel> addendances;

    @Column(name = "parent_name")
    private String parentName;

    public StudentModel(int id, String name, String address, String email, String phoneNumber, boolean isActive,
            String parentPhoneNumber, String parentEmail, Date dateOfBirth, SectionModel section, String parentName) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.parentPhoneNumber = parentPhoneNumber;
        this.parentEmail = parentEmail;
        this.dateOfBirth = dateOfBirth;
        this.section = section;
        this.parentName = parentName;
    }

}
