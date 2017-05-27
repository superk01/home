package org.zerock.persistence;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.FileVO;
import org.zerock.domain.SearchCriteria;

public interface BoardDAO {
	
	public void create(BoardVO boardVO)throws Exception;
	
	public BoardVO read(Integer bno)throws Exception;
	
	public List<BoardVO> listAll()throws Exception;
	
	public void update(BoardVO boardVO)throws Exception;
	
	public void delete(Integer bno)throws Exception;
	
	public List<BoardVO> listPage(Criteria cri)throws Exception;
	
	public int countPaging()throws Exception;
	
	public List<BoardVO> listSearch(SearchCriteria cri)throws Exception;
	
	public int listSearchCount(SearchCriteria cri)throws Exception;
	
	public void updateReplyCnt(Integer bno, int amount)throws Exception;
	
	public void updateViewCnt(Integer bno)throws Exception;
	
	public void addAttach(FileVO fileVO)throws Exception;
	
	public int maxNum()throws Exception;
	
	public List<String> getAttach(Integer bno)throws Exception;
	
	public void deleteAttach(Integer bno)throws Exception;
	
	public void replaceAttach(String fullName, Integer bno)throws Exception;
	
}
