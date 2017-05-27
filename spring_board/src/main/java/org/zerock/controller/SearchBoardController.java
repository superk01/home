package org.zerock.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageMaker;
import org.zerock.domain.SearchCriteria;
import org.zerock.service.BoardService;

@Controller
@RequestMapping("/sboard/*")
public class SearchBoardController {
	@Inject
	private BoardService service;
	
	@RequestMapping(value="/register" ,method=RequestMethod.GET)
	public void registerGet(BoardVO boardVO, Model model)throws Exception {
		
	}
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String registerPost(BoardVO boardVO)throws Exception{
		service.create(boardVO);
		return "redirect:/sboard/list";
	}
	
	@RequestMapping(value="list", method=RequestMethod.GET)
	public void listPage(@ModelAttribute("cri") SearchCriteria cri, Model model)throws Exception{
		/*model.addAttribute("list", service.listCriteria(cri));*/
		model.addAttribute("list", service.listSearchCriteria(cri));
		
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(service.listSearchCount(cri));
		
		model.addAttribute("pageMaker", pageMaker);
				
	}
	@RequestMapping(value="/readPage", method=RequestMethod.GET)
	public void readPage(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri, Model model)throws Exception {
		model.addAttribute(service.read(bno));
	}
	@RequestMapping(value="removePage", method=RequestMethod.POST)
	public String removePage(@RequestParam("bno") int bno, SearchCriteria cri, RedirectAttributes rttr)throws Exception{
		service.delete(bno);
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		return "redirect:/sboard/list";
	}
	@RequestMapping(value="modifyPage", method=RequestMethod.GET)
	public void modifyPageGet(@RequestParam("bno") int bno, @ModelAttribute("cri") SearchCriteria cri,Model model)throws Exception{
		BoardVO boardVO = service.read(bno);
		model.addAttribute(boardVO);
		
		/*return "board/modifyPage";*/
	}
	@RequestMapping(value="modifyPage", method=RequestMethod.POST)
	public String modifyPagePost(BoardVO boardVO, SearchCriteria cri, RedirectAttributes rttr)throws Exception{
		/*System.out.println("컨트롤러의"+boardVO.getBno()+"번글"+boardVO.getWriter());*/
		
		service.update(boardVO);
		
		rttr.addAttribute("page", cri.getPage());
		rttr.addAttribute("perPageNum", cri.getPerPageNum());
		rttr.addAttribute("searchType", cri.getSearchType());
		rttr.addAttribute("keyword", cri.getKeyword());
		
		return "redirect:/sboard/list";
	}
	@RequestMapping("/getAttach/{bno}")
	@ResponseBody
	public List<String> getAttach(@PathVariable("bno") Integer bno)throws Exception{
		return service.getAttach(bno);
	}
	
}
