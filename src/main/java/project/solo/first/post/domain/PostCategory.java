package project.solo.first.post.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity @Getter
@NoArgsConstructor
@Table(name = "POST_CATEGORY")
public class PostCategory {

    @Id @GeneratedValue
    @Column(name = "post_category_id")
    private Long id;

    @OneToMany(mappedBy = "category")
    private List<Post> postList = new ArrayList<>();

    private String name;

    // 매핑된 list 에 데이터를 넣어주는 메서드
    public void mappingPost(Post post) {
        this.postList.add(post);
    }
}
