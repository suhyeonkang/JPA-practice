package com.example.demo.src.test;

import com.example.demo.common.BaseException;
import com.example.demo.src.test.entity.Comment;
import com.example.demo.src.test.model.GetMemoDto;
import com.example.demo.src.test.model.MemoDto;
import com.example.demo.src.test.model.PostCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.example.demo.src.test.entity.Memo;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;

import static com.example.demo.common.BaseResponseStatus.*;

@RequiredArgsConstructor
@Transactional    //JPA는 트랜잭션 단위로 동작하기 때문에 이 어노테이션을 반드시 붙여야 함 (import 주의! springframework로 import 되어야 함)
@Service
public class TestService {

    private final TestDao testDao;

    public void createMemo(MemoDto memoDto) throws BaseException {

        Memo memo = new Memo();
        memo.makeMemo(memoDto);
        //중복
        if(checkMemo(memoDto.getMemo()) == 1){
            throw new BaseException(POST_TEST_EXISTS_MEMO);
        }
        try{
            testDao.createMemo(memo); // POINT
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)   // 조회만 하는 곳에는 readonly 설정하기
    public int checkMemo(String memo) throws BaseException{
        try{
            List<Memo> memoList = testDao.checkMemo(memo);
            return memoList.size(); // 메모리스트의 개수를 리턴
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    @Transactional(readOnly = true)  //조회만 하는 곳에는 readonly 설정하기
    public List<GetMemoDto> getMemos() throws BaseException{
        try{
            List<Memo> memoList = testDao.getMemos();
            List<GetMemoDto> getMemoDtoList = new ArrayList<>();
            for(Memo memo : memoList) {          // memoList에서 하나씩 꺼내서 GetMemoDto에 넣어줌
                GetMemoDto dto = new GetMemoDto(memo);
                getMemoDtoList.add(dto);
            }
            return getMemoDtoList;


        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyMemo(Long memoId, MemoDto memoDto) throws BaseException{
        try{
            // 업데이트 문을 직접 날리는게 아니라 변경 감지에 의해서 엔티티값을 가지고 오고 엔티티값의 특정한 데이터를 수정해주면 변경감지에 의해 자동으로 변경을 인식한다음 업데이트 문을 날려줌
            Memo memo = testDao.findMemo(memoId);
            if(memo == null){
                throw new BaseException(MODIFY_FAIL_MEMO);
            }

            memo.updateMemo(memoDto);


        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void createComment(PostCommentDto postCommentDto) throws BaseException {

        try{
            Memo memo = testDao.findMemo(postCommentDto.getMemoId()); // 메모를 찾아서 받아온다음 변수에 넣어줌
            Comment comment = new Comment(); // jpa entity manager의 메서드를 활용하기 위해서는 해당되는 엔티티를 넣어주어야 함 (postCommentDto를 comment로 바꿔주는 작업)
            comment.makeComment(postCommentDto, memo);  // 데이터베이스에 값을 저장하는 작업

            testDao.createComment(comment); // 커멘트를 직접 데이터 베이스에 저장

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
