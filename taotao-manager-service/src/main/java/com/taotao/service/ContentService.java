package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {

	TaotaoResult insertContent(TbContent content);

	List<TbContent> getContentList(Long categoryId);

}
