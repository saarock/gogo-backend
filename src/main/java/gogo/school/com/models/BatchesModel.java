package gogo.school.com.models;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SchoolBatch")
@Getter
@Setter
@NoArgsConstructor
public class BatchesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "batch_name", nullable = false)
    private String batchName;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("batches")
    private List<ClassModel> classes;

    @ManyToOne
    @JoinColumn(name = "school_id")
    @JsonBackReference("manage-batch")
    private SchoolModel schoolOfTheNepal;

    @ManyToOne
    @JoinColumn(name = "created_by")
    @JsonBackReference
    private TeacherModel teacherOfSchool;

    public BatchesModel(int id, String batchName, SchoolModel schoolModel) {
        this.id = id;
        this.batchName = batchName;
        this.schoolOfTheNepal = schoolModel;
        this.createdAt = new Date();
    }

    public void setCreatedAt() {
        this.createdAt = new Date();
    }

}
