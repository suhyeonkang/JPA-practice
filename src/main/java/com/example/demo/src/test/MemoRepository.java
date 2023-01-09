package com.example.demo.src.test;

import com.example.demo.common.BaseEntity;
import com.example.demo.src.test.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemoRepository extends JpaRepository<Memo, Long> { // 첫번째 인자는 어떤 엔티티에 대한 repository인지, 두번째 인자는 id 값의 타입

    //Memo를 통해서 State 값을 통해서 memo의 List를 가져오겠다.
    List<Memo> findByMemoAndState(String memo, BaseEntity.State state);

    List<Memo> findAllByState(BaseEntity.State state);

    Optional<Memo> findByIdAndState(Long memoId, BaseEntity.State state);
}
