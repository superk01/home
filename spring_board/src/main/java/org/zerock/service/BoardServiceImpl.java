package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.FileVO;
import org.zerock.domain.SearchCriteria;
import org.zerock.persistence.BoardDAO;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Inject
	private BoardDAO dao;
	
	
	
	@Transactional
	@Override
	public void create(BoardVO boardVO) throws Exception {
		System.out.println("Service-creat()");
		dao.create(boardVO);
		
		
		String[] files = boardVO.getFiles();
		if(files==null){
			return;
		}
		
		FileVO fileVO = new FileVO();
		System.out.println(dao.maxNum());
		fileVO.setBno(dao.maxNum());
		for(String fileName : files){
			fileVO.setFullName(fileName);
			dao.addAttach(fileVO);
		}

	}

	@Override
	public BoardVO read(Integer bno) throws Exception {
		dao.updateViewCnt(bno);
		return dao.read(bno);
	}

	@Override
	public List<BoardVO> listAll() throws Exception {
		
		return dao.listAll();
	}

	@Override
	public void update(BoardVO boardVO) throws Exception {
		dao.update(boardVO);

	}

	@Override
	public void delete(Integer bno) throws Exception {
		dao.delete(bno);
	}

	@Override
	public List<BoardVO> listCriteria(Criteria cri) throws Exception {
		System.out.println("service:"+cri.getPage());
		return dao.listPage(cri);
	}

	@Override
	public int listCountCriteria() throws Exception {
		
		return dao.countPaging();
	}

	@Override
	public List<BoardVO> listSearchCriteria(SearchCriteria cri) throws Exception {
		
		return dao.listSearch(cri);
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		
		return dao.listSearchCount(cri);
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		
		return dao.getAttach(bno);
	}
	
	
	
	
	
	

}
