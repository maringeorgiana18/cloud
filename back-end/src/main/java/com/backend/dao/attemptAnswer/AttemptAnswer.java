package com.backend.dao.attemptAnswer;

import com.backend.dao.attempt.Attempt;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class AttemptAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;
    private Boolean selected;
    private Boolean status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Attempt attempt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AttemptAnswer attemptAnswer = (AttemptAnswer) o;
        return getId() != null && attemptAnswer.getId() != null && getId().equals(attemptAnswer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
