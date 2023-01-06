package com.backend.dao.answer;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.backend.dao.question.Question;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    private Boolean status;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Question question;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Answer answer = (Answer) o;
        return getId() != null && answer.getId() != null && getId().equals(answer.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
