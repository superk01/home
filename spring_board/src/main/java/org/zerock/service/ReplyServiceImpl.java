package org.zerock.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.persistence.BoardDAO;
import org.zerock.persistence.ReplyDAO;

@Service
public class ReplyServiceImpl implements ReplyService {
	
	@Inject
	private ReplyDAO replyDAO;
	@Inject
	private BoardDAO boardDAO;

	@Override
	public List<ReplyVO> list(Integer bno) throws Exception {
		
		return replyDAO.list(bno);
	}

	@Transactional
	@Override
	public void create(ReplyVO replyVO) throws Exception {
		replyDAO.create(replyVO);
		boardDAO.updateReplyCnt(replyVO.getBno(), 1);

	}

	@Override
	public void update(ReplyVO replyVO) throws Exception {
		replyDAO.update(replyVO);
		

	}

	@Transactional
	@Override
	public void delete(Integer rno) throws Exception {
		int bno = replyDAO.getBno(rno);
		replyDAO.delete(rno);
		boardDAO.updateReplyCnt(bno, -1);
	}

	@Override
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri) throws Exception {
		return replyDAO.listReplyPage(bno, cri);
	}

	@Override
	public int count(Integer bno) throws Exception {
		return replyDAO.count(bno);
	}
	
	

}
