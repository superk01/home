package org.zerock.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.FileVO;
import org.zerock.domain.SearchCriteria;

@Repository
public class BoardDAOImpl implements BoardDAO {
	
	@Inject
	private SqlSession session;
	
	private static String namespace="org.zerock.mapper.BoardMapper";
	

	@Override
	public void create(BoardVO boardVO) {
		System.out.println("BoardDAOImpl:"+boardVO.getContent());
		session.insert(namespace+".create", boardVO);

	}

	   @Override
	   public BoardVO read(Integer bno) throws Exception {
	      return session.selectOne(namespace+".read", bno);
	   }

	@Override
	public List<BoardVO> listAll() {
		
		return session.selectList(namespace+".listAll");
	}

	@Override
	public void update(BoardVO boardVO) {
		session.insert(namespace+".update", boardVO);

	}

	@Override
	public void delete(Integer bno) {
		session.delete(namespace+".delete", bno);
	}

/*	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {
		System.out.println("DAO:"+cri.getPage());
		List<BoardVO> list = session.selectList(namespace+".listPage", cri);
		System.out.println("BoardDAO-list:"+list);
		
		return session.selectList(namespace+".listPage", cri);
	}*/
	
	@Override
	public List<BoardVO> listPage(Criteria cri) throws Exception {
		System.out.println("DAO:"+cri.getPage());
		List<BoardVO> list = session.selectList(namespace+".listPage", cri);
		System.out.println("BoardDAO-list:"+list);
		
		return session.selectList(namespace+".listAll", cri, new RowBounds(cri.getPage(), cri.getPerPageNum()));
	}

	@Override
	public int countPaging() throws Exception {
		
		return session.selectOne(namespace+".countPage");
		
	}

	@Override
	public List<BoardVO> listSearch(SearchCriteria cri) throws Exception {
		
		return session.selectList(namespace+".listSearch", cri, new RowBounds(cri.getPageStart(), cri.getPerPageNum()));
	}

	@Override
	public int listSearchCount(SearchCriteria cri) throws Exception {
		
		return session.selectOne(namespace+".listSearchCount", cri);
	}

	@Override
	public void updateReplyCnt(Integer bno, int amount) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bno", bno);
		map.put("amount", amount);
		
		session.selectOne(namespace+".updateReplyCnt", map);
	}

	@Override
	public void updateViewCnt(Integer bno) throws Exception {
		session.update(namespace+".updateViewCnt", bno);
	}

	@Override
	public void addAttach(FileVO fileVO) throws Exception {
		System.out.println("BoardDAO-addAttach"+fileVO);
		session.insert(namespace+".addAttach", fileVO);
		
	}

	@Override
	public List<String> getAttach(Integer bno) throws Exception {
		return session.selectList(namespace+".getAttach", bno);
	}

	@Override
	public int maxNum() throws Exception {
		return session.selectOne(namespace+".maxNum");
	}
	
	
	
	
	
	
	
	
	

}
