package com.backend.dao.question;

import com.backend.dao.answer.Answer;
import com.backend.dao.attempt.Attempt;
import com.backend.security.entities.User;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@DynamicUpdate
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String content;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User author;

    private String type;

    private String description;
    private Date creationDate;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<Answer> answers;

    @OneToMany(
            mappedBy = "question",
            orphanRemoval = false)
    private List<Attempt> attempts;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Question question = (Question) o;
        return getId().equals(question.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
