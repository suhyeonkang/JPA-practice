package com.example.demo.src.test;

import com.example.demo.common.BaseException;
import com.example.demo.common.BaseResponse;
import com.example.demo.src.test.model.GetMemoDto;
import com.example.demo.src.test.model.MemoDto;
import com.example.demo.src.test.model.PostCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.common.BaseResponseStatus.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
public class TestController {

    private final TestService testService;


    /**
     * 로그 테스트 API
     * [GET] /test/log
     * @return String
     */
    @ResponseBody
    @GetMapping("/log")
    public String getAll() {
        System.out.println("테스트");
        return "Success Test";
    }

    /**
     * 메모 생성 API
     * [POST] /test/memos
     * @return BaseResponse<String>
     */
    // Body
    @ResponseBody
    @PostMapping("/memos")
    public BaseResponse<String> createMemo(@RequestBody MemoDto memoDto) {
        if(memoDto.getMemo() == null){
            return new BaseResponse<>(TEST_EMPTY_MEMO);
        }
        try{
            testService.createMemo(memoDto);

            String result = "생성 성공!!";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 메모 리스트 조회 API
     * [GET] /test/memos
     * @return BaseResponse<List<TestDto>>
     */
    //Query String
    @ResponseBody
    @GetMapping("/memos")
    public BaseResponse<List<GetMemoDto>> getMemos() {
        try{
        List<GetMemoDto> getMemoDtoList = testService.getMemos();
        return new BaseResponse<>(getMemoDtoList);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    /**
     * 메모 정보 변경 API
     * [PATCH] test/memos/{memoId}
     * @return BaseResponse<String>
     */
    @ResponseBody
    @PatchMapping("/memos/{memoId}")
    public BaseResponse<String> modifyMemo(@PathVariable("memoId") Long memoId, @RequestBody MemoDto memoDto){
        try {
            if(memoDto.getMemo() == null || memoDto.getMemo().equals("")) {
                throw new BaseException(TEST_EMPTY_MEMO);
            }
            testService.modifyMemo(memoId, memoDto);

            String result = "수정 성공!!";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 코멘트 생성 API
     * [POST] /test/comments
     * @return BaseResponse<String>
     * */
    @ResponseBody
    @PostMapping("/comments")
    public BaseResponse<String> createComment(@RequestBody PostCommentDto postCommentDto) {
        if(postCommentDto.getComment() == null){
            return new BaseResponse<>(TEST_EMPTY_COMMENT);
        }
        try{
            testService.createComment(postCommentDto);

            String result = "생성 성공!!";
            return new BaseResponse<>(result);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
