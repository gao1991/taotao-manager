package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public String getItemParamByItemId(Long itemId) {
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list == null || list.size() == 0){
			return null;
		}
		TbItemParamItem itemParamItem =list.get(0);
		String patamData = itemParamItem.getParamData();
		//生成html
		//把规格参数json数据转换成java对象
		List<Map> jsonList = JsonUtils.jsonToList(patamData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<div class=\"Ptable-item\">\n" );
		for(Map m1:jsonList){
			sb.append("	<h3>"+m1.get("group")+"</h3>\n");
			List<Map> list2 = (List<Map>) m1.get("params");
			for(Map m2:list2){
				sb.append("	<dl>\n"                     );
				sb.append("		<dt>"+m2.get("k")+"</dt>\n"         );
				sb.append("		<dd>"+m2.get("v")+"</dd>\n"       );
				sb.append("	</dl>\n"                    );
			}
		}
		sb.append("</div>"  );
		return sb.toString();
	}

}
