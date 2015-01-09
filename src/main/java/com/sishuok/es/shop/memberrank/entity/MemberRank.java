/**
 * auto code generation
 */
package com.sishuok.es.shop.memberrank.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import com.sishuok.es.common.entity.BaseEntity;

/**
 * 会员等级管理Entity
 * @author xxs
 * @version 2015-01-03
 */
@Entity
@Table(name = "shop_member_rank")
public class MemberRank extends BaseEntity<Long> {
	
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


