/**
 * Copyright (c) 2005-2012 https://github.com/zhangkaitao
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.sishuok.es.showcase.product.web.controller;

import com.sishuok.es.common.entity.enums.BooleanEnum;
import com.sishuok.es.common.entity.search.SearchOperator;
import com.sishuok.es.common.entity.search.Searchable;
import com.sishuok.es.common.web.bind.annotation.PageableDefaults;
import com.sishuok.es.common.web.controller.BaseCRUDController;
import com.sishuok.es.showcase.product.entity.Category;
import com.sishuok.es.showcase.product.entity.Product;
import com.sishuok.es.showcase.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * <p>User: Zhang Kaitao
 * <p>Date: 13-1-28 下午4:29
 * <p>Version: 1.0
 */
@Controller
@RequestMapping(value = "/showcase/product/product")
public class ProductController extends BaseCRUDController<Product, Long> {

    public ProductController() {
        setResourceIdentity("showcase:product");
    }

    @Override
    protected void setCommonData(Model model) {
        model.addAttribute("booleanList", BooleanEnum.values());
    }

    @RequestMapping(value = "/category-{categoryId}", method = RequestMethod.GET)
    @PageableDefaults(sort = "id=desc")
    public String listByCategory(Searchable searchable, @PathVariable("categoryId") Category category, Model model) {
        if (category != null) {
            model.addAttribute("category", category);
            searchable.addSearchFilter("category.id", SearchOperator.eq, category.getId());
        }
        return super.list(searchable, model);
    }

    //@PathVariable("category") 会把数据放到request attribute中，如果想绑定到spring:form上，必须把数据放到model中
    //因为request里的数据没有BindResult 不能得到自动类型转换
    //当然也可以使用spring:expression完成单独的转换
    @RequestMapping(value = "/category-{categoryId}/create", method = RequestMethod.GET)
    public String showCreateForm(Model model, @PathVariable("categoryId") Category category) {
        String result = super.showCreateForm(model);

        if (category != null) {
            Product m = (Product) model.asMap().get("m");
            m.setCategory(category);
        }
        return result;
    }

    @RequestMapping(value = "/category-{categoryId}/create", method = RequestMethod.POST)
    @Override
    public String create(Model model, @Valid @ModelAttribute("m") Product product, BindingResult result, RedirectAttributes redirectAttributes) {
        return super.create(model, product, result, redirectAttributes);
    }

    @RequestMapping(value = {"/category-{categoryId}/{m}/update", "/{m}/update"}, method = RequestMethod.GET)
    public String showUpdateForm(@PathVariable("m") Product product, Model model) {
        return super.showUpdateForm(product, model);
    }

    @RequestMapping(value = {"/category-{categoryId}/{id}/update", "/{id}/update"}, method = RequestMethod.POST)
    public String update(
            Model model,
            @Valid @ModelAttribute("m") Product product, BindingResult result,
            @RequestParam(value = "BackURL", required = false) String backURL,
            RedirectAttributes redirectAttributes) {

        return super.update(model, product, result, backURL, redirectAttributes);
    }

    @RequestMapping(value = {"/category-{categoryId}/{id}/delete", "/{id}/delete"}, method = RequestMethod.GET)
    public String showDeleteForm(@PathVariable("id") Product product, Model model) {
        return super.showDeleteForm(product, model);
    }

    @RequestMapping(value = {"/category-{categoryId}/{id}/delete", "/{id}/delete"}, method = RequestMethod.POST)
    public String delete(@ModelAttribute("m") Product product, @RequestParam(value = "BackURL", required = false) String backURL, RedirectAttributes redirectAttributes) {
        return super.delete(product, backURL, redirectAttributes);
    }
}
