package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EUTreeNode;

public interface ItemCatService {
	//查询商品类目
	List<EUTreeNode> getCatList(Long parentId);
}
