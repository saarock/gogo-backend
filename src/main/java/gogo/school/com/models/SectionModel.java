package gogo.school.com.models;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "section")
public class SectionModel {
    public SectionModel(int id, String name, ClassModel grade_class) {
        this.id = id;
        this.name = name;
        this.grade_class = grade_class;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JsonBackReference("class-section")
    private ClassModel grade_class;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "section")
    @JsonManagedReference
    private List<StudentModel> students;

}
