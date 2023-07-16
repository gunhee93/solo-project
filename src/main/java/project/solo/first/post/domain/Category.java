package project.solo.first.post.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Post> postList = new ArrayList<>();

    public void mappingPost(Post post) {
        this.postList.add(post);
    }

    @Builder
    public Category(String name) {
        this.name = name;
    }

    public static Category of(String name) {
        return Category.builder()
                .name(name).build();
    }
}