package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;
@Service
public class ItemParamServiceImpl implements ItemParamService {
	
	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Override
	public TaotaoResult getItemParamByCid(long cid) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
	//	criteria.andIdEqualTo(cid);
		criteria.andItemCatIdEqualTo(cid);
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		//判断是否查询到结果
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok(list.get(0));
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult saveItemParam(long cid, String itemParam) {
		// 创建TbItemParam对象
		TbItemParam param = new TbItemParam();
		param.setItemCatId(cid);
		param.setParamData(itemParam);
		param.setCreated(new Date());
		param.setUpdated(new Date());
		//向数据库中添加数据
		itemParamMapper.insert(param);
		return TaotaoResult.ok();
	}

}
