/**
 * auto code generation
 */
package com.sishuok.es.sys.bean.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sishuok.es.common.entity.BaseEntity;

/**
 * @author xxs
 * @version 2015-03-22
 */
@Entity
@Table(name = "sys_bean")
public class Bean extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
		
	@Column(name = "name")
	private String name; 	
	
	@Column(name = "classname")
	private String classname; 
	
	@Column(name = "allclassname")
	private String allclassname; 	
	
	@Column(name = "comments")
	private String comments; 	
	
	@OneToMany(mappedBy = "bean", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<BeanItem> beanItems = new ArrayList<BeanItem>();

	public String getClassname() {
		return classname;
	}
	
	public void setClassname(String classname) {
		this.classname = classname;
	}
	
	public String getAllclassname() {
		return allclassname;
	}
	
	public void setAllclassname(String allclassname) {
		this.allclassname = allclassname;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public List<BeanItem> getBeanItems() {
		return beanItems;
	}

	public void setBeanItems(List<BeanItem> beanItems) {
		this.beanItems = beanItems;
	}

}


