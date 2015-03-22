/**
 * auto code generation
 */
package com.sishuok.es.sys.xxs.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sishuok.es.common.entity.BaseEntity;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Entity
 * @author xxs
 * @version 2015-03-22
 */
@Entity
@Table(name = "sys_xxs_attribute")
public class XxsAttribute extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
		
	private Xxs xxs;	// 归属表
	
	@Column(name = "name")
	private String name; 		// 属性名
	@Column(name = "comments")
	private String comments;	// 描述
	private String javaType;	// JAVA类型
	private String isEdit;		// 是否编辑字段（1：编辑字段）
	private String isList;		// 是否列表字段（1：列表字段）
	private String isQuery;		// 是否查询字段（1：查询字段）
	private String queryType;	// 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
	private String showType;	// 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
	private Integer sort;		// 排序（升序）
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public Xxs getXxs() {
		return xxs;
	}

	public void setXxs(Xxs xxs) {
		this.xxs = xxs;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(String javaType) {
		this.javaType = javaType;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsList() {
		return isList;
	}

	public void setIsList(String isList) {
		this.isList = isList;
	}

	public String getIsQuery() {
		return isQuery;
	}

	public void setIsQuery(String isQuery) {
		this.isQuery = isQuery;
	}

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

    
}


