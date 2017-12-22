package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.service.ContentCategoryService;
/**
 * 内容管理
 * @author ghs
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EUTreeNode> getCategoryList(long parentId) {
		// 根据parentid查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			//创建一个节点
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		return resultList;
	}
	
	/**
	 * 新增内容分类
	 */
	@Override
	public TaotaoResult insertContentCatgory(long parentId, String name) {
		// 创建一个pojo
		TbContentCategory contentCategory = new TbContentCategory();
		contentCategory.setName(name);;
		contentCategory.setIsParent(false);
		contentCategory.setStatus(1);	//1正常，2删除
		contentCategory.setParentId(parentId);;
		contentCategory.setSortOrder(1);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//添加记录
		contentCategoryMapper.insert(contentCategory);
		//查看父节点的isParent是否为true,如果不是则改为true
		TbContentCategory parentCate = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parentCate.getIsParent()) {
			parentCate.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parentCate);
		}
		return TaotaoResult.ok(contentCategory);
	}

	@Override
	public TaotaoResult deleteContentCatgory(long id) {
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		Long parentId = contentCategory.getParentId();
		// 删除当前节点记录
		contentCategoryMapper.deleteByPrimaryKey(id);
		//判断parentId对应的记录下是否还有子节点
		// 根据parentid查询节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
//		List<EUTreeNode> list = getCategoryList(pContentCategory.getId());
		if (list != null && list.size() > 0) {
			return TaotaoResult.ok();
		}
		TbContentCategory pContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
		pContentCategory.setIsParent(false);
		contentCategoryMapper.updateByPrimaryKey(pContentCategory);
		return TaotaoResult.ok();
	}

}
