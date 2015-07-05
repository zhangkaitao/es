/**
 * auto code generation
 */
package com.sishuok.es.sys.bean.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sishuok.es.common.entity.BaseEntity;

/**
 * 不知道叫什么功能，可耻的用了自己的名字Entity
 * @author xxs
 * @version 2015-03-22
 */
@Entity
@Table(name = "sys_bean_item")
public class BeanItem extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
		
	private Bean bean;	// 归属表
	
    @Column(name = "name")
    private String name; 		// 属性名
    @Column(name = "classname")
    private String classname; 		// 属性名
	@Column(name = "display_name")
	private String displayName; 		// 属性名
	@Column(name = "comments")
	private String comments;	// 描述
	@Column(name = "java_type")
	private String javaType;	// JAVA类型
	@Column(name = "is_edit")
	private Boolean edit;		// 是否编辑字段（1：编辑字段）
	@Column(name = "is_list")
	private Boolean list;		// 是否列表字段（1：列表字段）
	@Column(name = "is_query")
	private Boolean query;		// 是否查询字段（1：查询字段）
	@Column(name = "query_type")
	private String queryType;	// 查询方式（等于、不等于、大于、小于、范围、左LIKE、右LIKE、左右LIKE）
	@Column(name = "show_type")
	private String showType;	// 字段生成方案（文本框、文本域、下拉框、复选框、单选框、字典选择、人员选择、部门选择、区域选择）
	@Column(name = "sort")
	private Integer sort;		// 排序（升序）
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClassname() {
		return classname;
	}
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
    public String getDisplayName() {
        return displayName;
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

	public Boolean getEdit() {
		return edit;
	}

	public void setEdit(Boolean edit) {
		this.edit = edit;
	}

	public Boolean getList() {
		return list;
	}

	public void setList(Boolean list) {
		this.list = list;
	}
	
	public Boolean getQuery() {
		return query;
	}

	public void setQuery(Boolean query) {
		this.query = query;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "beans", nullable = false, updatable = false)
	public Bean getBean() {
		return bean;
	}

	public void setBean(Bean bean) {
		this.bean = bean;
	}
	
    
}


