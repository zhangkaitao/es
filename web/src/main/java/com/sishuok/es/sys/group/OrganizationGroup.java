/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.sys.group;

import com.sishuok.es.common.entity.BaseEntity;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-4-19 上午7:11
 * <p>Version: 1.0
 */
@Entity
@DiscriminatorValue(value = "user")
public class OrganizationGroup extends Group {

}
