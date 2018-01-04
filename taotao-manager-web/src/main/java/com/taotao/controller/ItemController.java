package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId){
		TbItem item = itemService.getItemById(itemId);
		return item;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(Integer page,Integer rows){
		EUDataGridResult result = itemService.getItemList(page,rows);
		
		return result;
	}
	
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult creatItem(TbItem item, String desc, String itemParams) throws Exception {
		TaotaoResult result = itemService.creatItem(item, desc, itemParams);
		return result;
	}
	
	@RequestMapping(value="/item/delete",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult deleteItem(String ids) throws Exception {
		TaotaoResult result = itemService.deleteItem(ids);
		return result;
	}

}
