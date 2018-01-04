package com.taotao.service;

import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface ItemService {
	
	//根据ID查询商品
	TbItem getItemById(Long itemId);

	EUDataGridResult getItemList(Integer page, Integer rows);
	
	//新增商品
	TaotaoResult creatItem(TbItem item, String desc, String itemParam) throws Exception ;

	//删除商品
	TaotaoResult deleteItem(String ids);

}
