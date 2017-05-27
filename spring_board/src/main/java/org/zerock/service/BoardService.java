package org.zerock.service;

import java.util.List;

import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.SearchCriteria;

public interface BoardService {
	
	public void create(BoardVO boardVO)throws Exception;
	
	public BoardVO read(Integer bno)throws Exception;
	
	public List<BoardVO> listAll()throws Exception;
	
	public void update(BoardVO boardVO)throws Exception;
	
	public void delete(Integer bno)throws Exception;
	
	public List<BoardVO> listCriteria(Criteria cri)throws Exception;
	
	public int listCountCriteria()throws Exception;
	
	public List<BoardVO> listSearchCriteria(SearchCriteria cri)throws Exception;
	
	public int listSearchCount(SearchCriteria cri)throws Exception;
	
	public List<String> getAttach(Integer bno)throws Exception;
	
	
}
