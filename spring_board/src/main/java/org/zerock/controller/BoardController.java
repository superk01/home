package org.zerock.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/board/*")
public class BoardController {
	
	@Inject
	private BoardService service;
	
	@RequestMapping(value="/register" ,method=RequestMethod.GET)
	public void registerGet(BoardVO boardVO, Model model)throws Exception {
		
	}
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPost(BoardVO boardVO)throws Exception{
		service.create(boardVO);
		return "redirect:/board/listAll";
	}
	@RequestMapping("/listAll")
	public void listAll(Model model)throws Exception{
		List<BoardVO> list = service.listAll();
		model.addAttribute("list", list);
		
		/*return "board/listAll";*/
	}
	/*@RequestMapping("/read")
	public String read(@RequestParam("bno") int bno, Model model)throws Exception{
		BoardVO boardVO = service.read(bno);
		model.addAttribute("boardVO", boardVO);
		
		return "board/read";
	}*/
	@RequestMapping(value="/modify", method=RequestMethod.GET)
	public void modifyGet(@RequestParam("bno") int bno, Model model)throws Exception{
		BoardVO boardVO=service.read(bno);
		model.addAttribute("boardVO", boardVO);
	}
	@RequestMapping(value="modify", method=RequestMethod.POST)
	public String modifyPost(BoardVO boardVO)throws Exception{
		service.update(boardVO);
		return "redirect:/board/listAll";
	}
	@RequestMapping("/remove")
	public String remove(@RequestParam("bno") int bno)throws Exception{
		service.delete(bno);
		return "redirect:/board/listAll";
	}
	
	@RequestMapping("/listPage")
	public void listPage(Criteria cri, Model model)throws Exception{
		System.out.println(cri.getPage());
		System.out.println(cri.getPerPageNum());
		model.addAttribute("list", service.listCriteria(cri));
		
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listCountCriteria());
		model.addAttribute("pageMaker", pageMaker);
				
	}
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public void readPage(@RequestParam("bno") int bno, @ModelAttribute("cri") Criteria cri, Model model)throws Exception {
		model.addAttribute(service.read(bno));
	}
	
	@RequestMapping(value="removePage", method=RequestMethod.POST)
	public String removePage(@RequestParam("bno") int bno, Criteria cri, RedirectAttributes rttr)throws Exception{
		service.delete(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		return "redirect:/board/listPage";
	}
	@RequestMapping(value="modifyPage", method=RequestMethod.GET)
	public void modifyPageGet(@RequestParam("bno") int bno, @ModelAttribute Criteria cri,Model model)throws Exception{
		BoardVO boardVO = service.read(bno);
		model.addAttribute(boardVO);
		
		/*return "board/modifyPage";*/
	}
	@RequestMapping(value="modifyPage", method=RequestMethod.POST)
	public String modifyPagePost(BoardVO boardVO, Criteria cri, RedirectAttributes rttr)throws Exception{
		System.out.println("컨트롤러의"+boardVO.getBno()+"번글"+boardVO.getWriter());
		
		service.update(boardVO);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		
		return "redirect:/board/listPage";
	}

}
