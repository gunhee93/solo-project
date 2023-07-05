package project.solo.first.post.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "POST_CATEGORY")
public class PostCategory {

    @Id @GeneratedValue
    @Column(name = "post_category_id")
    private Long id;

    @Column(name = "category_name")
    private String name;

    private Long parentId;


    public PostCategory(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }
}
