package com.sishuok.es.sys.site.utils;

import java.util.List;

import com.sishuok.es.common.utils.CacheUtils;
import com.sishuok.es.common.utils.StringUtils;
import com.sishuok.es.generate.utils.SpringContextHolder;
import com.sishuok.es.sys.site.entity.Site;
import com.sishuok.es.sys.site.service.SiteService;



public class SiteUtils {
		
	private static SiteService siteService = SpringContextHolder.getBean(SiteService.class);

	private static final String CMS_CACHE = "cmsCache";
	
	/**
	 * 获得站点列表
	 */
	public static List<Site> getSiteList(){
		@SuppressWarnings("unchecked")
		List<Site> siteList = (List<Site>)CacheUtils.get(CMS_CACHE, "siteList");
		if (siteList == null){
			siteList = siteService.findAll();
			CacheUtils.put(CMS_CACHE, "siteList", siteList);
		}
		return siteList;
	}
	
	/**
	 * 获得站点信息
	 * @param siteId 站点编号
	 */
	public static Site getSite(Long siteId){
		Long id = (long) 1;
		if (StringUtils.isNotBlank(String.valueOf(siteId))){
			id = siteId;
		}
		for (Site site : getSiteList()){
			if (site.getId().equals(id)){
				return site;
			}
		}
		return siteService.findOne(id);
	}
	
}
