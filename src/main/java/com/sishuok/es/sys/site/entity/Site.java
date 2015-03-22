/**
 * auto code generation
 */
package com.sishuok.es.sys.site.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.sishuok.es.common.entity.BaseEntity;

/**
 * 站点管理Entity
 * @author xxs
 * @version 2015-03-17
 */
@Entity
@Table(name = "sys_site")
public class Site extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
		
	public Site() {
		super();
	}
	
	@Column(name = "name")
	private String name; 	

	@Column(name = "remarks")
    private String remarks;

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
	
}


