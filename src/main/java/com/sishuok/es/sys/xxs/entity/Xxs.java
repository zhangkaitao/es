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
@Table(name = "sys_xxs")
public class Xxs extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
		
	@Column(name = "name")
	private String name; 	
	
	@Column(name = "classname")
	private String classname; 	
	
	@Column(name = "comments")
	private String comments; 	

	@Column(name = "remarks")
    private String remarks;

	
	public String getClassname() {
		return classname;
	}
	
	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}


