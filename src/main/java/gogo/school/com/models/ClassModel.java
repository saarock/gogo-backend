package gogo.school.com.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@Setter
@Getter
@NoArgsConstructor
@Table
@Entity(name = "classes")
public class ClassModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "batch")
    @JsonBackReference("batch-class")
    private BatchesModel batch;

    @OneToMany(mappedBy = "grade_class", cascade = CascadeType.ALL)
    @JsonManagedReference(value = "class-section")
    private List<SectionModel> sections;

    public ClassModel(int id, String name, BatchesModel batch) {
        this.id = id;
        this.name = name;
        this.batch = batch;
    }

}
