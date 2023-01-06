package com.backend.dao.attempt;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.backend.dao.attemptAnswer.AttemptAnswer;
import com.backend.dao.question.Question;
import com.backend.security.entities.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@DynamicUpdate
public class Attempt {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = true)
    private Question question;

    @OneToMany(
            mappedBy = "attempt",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<AttemptAnswer> answers;

    private String type;
    private String description;
    private String content;

    private Boolean status;
    private String historyStatus;
    private Timestamp startDate;
    private Timestamp endDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Attempt)) return false;
        return id != null && id.equals(((Attempt) o).getId());
    }
}
