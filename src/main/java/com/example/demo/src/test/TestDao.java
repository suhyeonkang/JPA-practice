package com.example.demo.src.test;

import com.example.demo.src.test.model.GetMemoDto;
import com.example.demo.src.test.model.MemoDto;
import com.example.demo.src.test.model.PostCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.src.test.entity.Memo;
import com.example.demo.src.test.entity.Comment;

import javax.persistence.EntityManager;
import javax.sql.DataSource;
import java.util.List;

@RequiredArgsConstructor // 의존성 주입을 받기 위한 어노테이션
@Repository
public class TestDao {

    private final EntityManager entityManager;


    public void createMemo(Memo memo){
        if(memo.getId() == null) {
            entityManager.persist(memo);
        }else {
            entityManager.merge(memo);
        }

    }


    public List<Memo> checkMemo(String memo){  //메모가 여러개일 수 있으니까 List로 리턴값 설정
        // Memo 테이블 안의 memo 값을 받아온 memo 값과 비교해서 일치하는 값을 가져오겠다
        return entityManager.createQuery("select m from Memo as m where m.memo = :memo and m.state = 'ACTIVE'", Memo.class)
                .setParameter("memo", memo)
                .getResultList();

    }


    public List<Memo> getMemos() {

       return entityManager.createQuery("select m from Memo m left join fetch m.commentList where m.state = 'ACTIVE'", Memo.class)
               .getResultList();
//       return entityManager.createQuery("select m from Memo m where m.state = 'ACTIVE'", Memo.class)
//                .getResultList();
    }


   public Memo findMemo(Long id){
        return entityManager.find(Memo.class, id); //어떤 클래스의 엔티티를 조회할건지?
   }

   public void createComment(Comment comment){
        // 받아온 comment 엔티티 값의 id가 null일 수 있으니 분리 처리
       if(comment.getMemoId() == null){
           entityManager.persist(comment); // null이면 저장
       }else {
           entityManager.merge(comment); // null이 아니면 update
       }
   }

}
