package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
/**
 * 内容分类管理
 * @author ghs
 *
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {
	
	@Autowired
	private ContentCategoryService contentCategoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getContentCatList(@RequestParam(value="id",
			defaultValue="0")Long parentId) {
		List<EUTreeNode> list = contentCategoryService.getCategoryList(parentId);
		return list;
	}
	
	/**
	 * 新增内容分类
	 * @author ghs
	 *
	 */
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult createContentCategory(Long parentId, String name){
		TaotaoResult result = contentCategoryService.insertContentCatgory(parentId, name);
		return result;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCatgory(Long id){
		TaotaoResult result = contentCategoryService.deleteContentCatgory(id);
		return result;
	}

}
