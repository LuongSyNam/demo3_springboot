package vn.iotstar.controllers.admin;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.validation.Valid;
import vn.iotstar.entity.Category;
import vn.iotstar.models.CategoryModel;
import vn.iotstar.services.CategoryService;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@RequestMapping("")
	public String all(Model model) {
		List<Category> list = categoryService.findAll();
		model.addAttribute("list", list);
		return "admin/category/list";
	}

	@PostMapping("saveOrUpdate")
	public ModelAndView saveOrUpdate(ModelMap model, 
	                                 @Valid @ModelAttribute("category") CategoryModel cateModel, 
	                                 BindingResult result) {
	    if(result.hasErrors()) {
	        return new ModelAndView("admin/categories/addOrEdit");
	    }
	    Category entity = new Category();
	    // Copy from Model to Entity
	    BeanUtils.copyProperties(cateModel, entity);
	    // Call save in service
	    categoryService.save(entity);
	    // Bring message back to variable
	    String message = "";
	    if(cateModel.getIsEdit() == true) {
	        message = "Category is Edited!!!!!!!!!";
	    } else {
	        message = "Category is saved!!!!!!!!!";
	    }
	    model.addAttribute("message", message);
	    // Redirect to URL controller
	    return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@RequestMapping("list")
	public String list(ModelMap model) {
	    // Call findAll in service
	    List<Category> list = categoryService.findAll();
	    // Transfer list data to variable categories
	    model.addAttribute("categories", list);
	    return "admin/categories/list";
	}

	@GetMapping("edit/{categoryid}")
	public ModelAndView edit(ModelMap model, @PathVariable("categoryid") Long categoryId) {
	    Optional<Category> optCategory = categoryService.findById(categoryId);
	    CategoryModel cateModel = new CategoryModel();
	    // Check if category exists
	    if(optCategory.isPresent()) {
	        Category entity = optCategory.get();
	        // Copy from entity to cateModel
	        BeanUtils.copyProperties(entity, cateModel);
	        cateModel.setIsEdit(true);
	        // Transfer data to view
	        model.addAttribute("category", cateModel);
	        return new ModelAndView("admin/categories/addOrEdit", model);
	    }
	    model.addAttribute("message", "Category is not existed!!!");
	    return new ModelAndView("forward:/admin/categories", model);
	}
	
	@GetMapping("delete/{categoryId}")
	public ModelAndView delete(ModelMap model, @PathVariable("categoryId") Long categoryId) {
	    categoryService.deleteById(categoryId);
	    model.addAttribute("message", "Category is deleted!!!");
	    return new ModelAndView("forward:/admin/categories/searchpaginated", model);
	}

	@GetMapping("search")
	public String search(ModelMap model, @RequestParam(name="name", required = false) String name) {
	    List<Category> list = null;
	    // có nội dung truyền về không, name là tùy chọn khi required=false
	    if(StringUtils.hasText(name)) {
	        list = categoryService.findByNameContaining(name);
	    } else {
	        list = categoryService.findAll();
	    }
	    model.addAttribute("categories", list);
	    return "admin/categories/search";
	}

	@RequestMapping("searchpaginated")
	public String search(ModelMap model,
	    @RequestParam(name="name", required = false) String name,
	    @RequestParam("page") Optional<Integer> page,
	    @RequestParam("size") Optional<Integer> size) {
	    
	    int count = (int) categoryService.count();
	    int currentPage = page.orElse(1);
	    int pageSize = size.orElse(3);
	    Pageable pageable = PageRequest.of(currentPage-1, pageSize, Sort.by("name"));
	    Page<Category> resultPage = null;
	    
	    if(StringUtils.hasText(name)) {
	        resultPage = categoryService.findByNameContaining(name, pageable);
	        model.addAttribute("name", name);
	    } else {
	        resultPage = categoryService.findAll(pageable);
	    }

	    int totalPages = resultPage.getTotalPages();
	    if(totalPages > 0) {
	        int start = Math.max(1, currentPage - 2);
	        int end = Math.min(currentPage + 2, totalPages);
	        if(totalPages > count) {
	            if(end == totalPages) start = end - count;
	            else if(start == 1) end = start + count;
	        }
	        List<Integer> pageNumbers = IntStream.rangeClosed(start, end)
	            .boxed().collect(Collectors.toList());
	        model.addAttribute("pageNumbers", pageNumbers);
	    }
	    model.addAttribute("categoryPage", resultPage);
	    return "admin/categories/searchpaginated";
	}

}