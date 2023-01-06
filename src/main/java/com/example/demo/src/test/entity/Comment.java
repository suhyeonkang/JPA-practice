package com.example.demo.src.test.entity;

import com.example.demo.common.BaseEntity;
import com.example.demo.src.test.model.PostCommentDto;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PUBLIC) // 기본생성자
@EqualsAndHashCode(callSuper = false)
@Getter    //Setter를 주지 않는 이유는 Memo 엔티티를 다른 레이어에서 사용할 때 원치 않는 값으로 변경되는 것을 막기 위함, 멤버 변수를 바꾸어야 할 때는 별도의 메서드를 만들어서 사용함
@Entity
@Table(name= "COMMENT") //name을 별도로 지정하지 않으면 클래스 이름이 기본값으로 설정됨
public class Comment extends BaseEntity {

    @Id //PK 값을 의미
    @Column(name ="memoId", nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // id 값이 자동으로 생성되도록 설정하는 어노테이션
    private Long memoId;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "memoId")
    private Memo memo;

    public void makeComment(PostCommentDto postCommentDto, Memo memo){
        this.memo = memo;
        this.comment = postCommentDto.getComment();
    }

}
