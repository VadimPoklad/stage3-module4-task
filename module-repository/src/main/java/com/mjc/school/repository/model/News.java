package com.mjc.school.repository.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@NamedQueries({
        @NamedQuery(name = "findAllNewsByTagIds", query = """
                SELECT n FROM News n
                LEFT JOIN n.tags t
                WHERE t.id IN (:ids)
                GROUP BY n
                HAVING COUNT(n) >= :numberTags
                """),
        @NamedQuery(name = "findAllNewsByTagNames", query = """
                SELECT n FROM News n
                LEFT JOIN n.tags t
                WHERE t.name IN (:names)
                GROUP BY n
                HAVING COUNT(n) >= :numberTags
                  """),
})
public class News implements BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime lastUpdateDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author")
    private Author author;

    @ManyToMany
    @JoinTable(name = "news_tags",
            joinColumns = @JoinColumn(name = "news_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @OneToMany(mappedBy = "news", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Comment> comments;
}