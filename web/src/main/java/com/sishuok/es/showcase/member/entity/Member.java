/**
 * auto code generation
 */
package com.sishuok.es.showcase.member.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.sishuok.es.common.entity.BaseEntity;

/**
 * 会员Entity
 * @author xxs
 * @version 2014-11-01
 */
@Entity
@Table(name = "member")
public class Member extends BaseEntity<Long> {
	
	private static final long serialVersionUID = 1L;
		
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


