package org.zerock.service;

import java.util.List;

import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;

public interface ReplyService {

	public List<ReplyVO> list(Integer bno)throws Exception;
	
	public void create(ReplyVO replyVO)throws Exception;
	
	public void update(ReplyVO replyVO)throws Exception;
	
	public void delete(Integer rno)throws Exception;
	
	public List<ReplyVO> listReplyPage(Integer bno, Criteria cri)throws Exception;
	
	public int count(Integer bno)throws Exception;
}
