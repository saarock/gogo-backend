package gogo.school.com.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "teachers")
@Getter
@Setter
@NoArgsConstructor
public class TeacherModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "teacher_name", nullable = false)
    private String teacherName;

    @Column(name = "teacher_address", nullable = false)
    private String teacherAddress;

    @Column(name = "teacher_phonenumber")
    private String teacherPhoneNumber;

    @Column(name = "teacher_mail", nullable = false)
    private String teacherMail;

    @Column(name = "password", nullable = false)
    private String teacherPassword;

    @Column(name = "RefereshToken")
    private String teacherRefershToken;

    @Column(name = "is_active", nullable = false)
    private boolean isActive = true;

    @Column(name = "is_admin", nullable = false)
    private boolean isAdmin = false;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonManagedReference("manage-teacher")
    private SchoolModel schoolModel;

    public TeacherModel(int id, String teacherName, String teacherAddress, String teacherPhoneNumber,
            String teacherMail, String teacherPassword, String teacherRefershToken, boolean isActive, boolean isAdmin) {
        this.id = id;
        this.teacherName = teacherName;
        this.teacherAddress = teacherAddress;
        this.teacherPhoneNumber = teacherPhoneNumber;
        this.teacherMail = teacherMail;
        this.teacherPassword = teacherPassword;
        this.teacherRefershToken = teacherRefershToken;
        this.isActive = isActive;
        this.isAdmin = isAdmin;
    }

}
