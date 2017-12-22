package com.taotao.service.impl;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;
	
	@Autowired
	private TbItemDescMapper itemDescMapper;
	
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Value("${SYNCH_SOLR_URL}")
	private String SYNCH_SOLR_URL;
	

	@Override
	public TbItem getItemById(Long itemId) {
		//TbItem item = itemMapper.selectByPrimaryKey(itemId);
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list != null && list.size() > 0){
			TbItem item = list.get(0);
			return item;
		}
		return null;
	}

	@Override
	public EUDataGridResult getItemList(Integer page, Integer rows) {
		TbItemExample example = new TbItemExample();
		//分页处理
		PageHelper.startPage(page, rows);
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EUDataGridResult result = new EUDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(list);
		return result;
	}

	@Override
	public TaotaoResult creatItem(TbItem item, String desc, String itemParam) throws Exception {
		// item补全
		//1、生成itemId
		Long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//2、商品状态，1-正常，2-下架，3-删除
		item.setStatus((byte) 1);
		item.setCreated(new Date());
		item.setUpdated(new Date());
		itemMapper.insert(item);
		TaotaoResult result = insertItemDesc(itemId, desc);
		if(result.getStatus()!=200){
			throw new Exception();
		}
		//添加规格参数
		result = insertItemParam(itemId, itemParam);
		if(result.getStatus()!=200){
			throw new Exception();
		}
		try {
			HttpClientUtil.doGet(SYNCH_SOLR_URL);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}
	
	private TaotaoResult insertItemDesc(Long itemId, String desc){
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		itemDescMapper.insert(itemDesc);
		return TaotaoResult.ok();
	}
	
	//插入商品规格
	private TaotaoResult insertItemParam(Long itemId, String itemParam){
		//创建pojo
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}

}
