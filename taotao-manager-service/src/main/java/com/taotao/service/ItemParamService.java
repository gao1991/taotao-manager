package com.taotao.service;

import com.taotao.common.pojo.TaotaoResult;

public interface ItemParamService {
	
	TaotaoResult getItemParamByCid(long cid);
	
	TaotaoResult saveItemParam(long cid, String itemParam);

}
