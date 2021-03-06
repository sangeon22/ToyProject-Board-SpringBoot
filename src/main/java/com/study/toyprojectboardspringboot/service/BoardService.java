package com.study.toyprojectboardspringboot.service;

import com.study.toyprojectboardspringboot.entity.Board;
import com.study.toyprojectboardspringboot.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    // 게시글 작성 처리 및 파일 업로드
    public void write(Board board, MultipartFile file) throws IOException {

        // 업로드 파일 저장 경로
        String projectPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\files";

        // 식별자 파일 랜덤 Name 생성
        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();

        File saveFile = new File(projectPath, fileName);
        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){
        return boardRepository.findAll(pageable);
    }

    // BoardRepository interface에서 선언한 메소드 정의 - 검색기능처리
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }




    // 특정 게시글 불러오기
    public Board boardView(Integer id){
        return boardRepository.findById(id).get();
    }

    // 특정 게시글 삭제
    public void boardDelete(Integer id) {
        boardRepository.deleteById(id);
    }

}
