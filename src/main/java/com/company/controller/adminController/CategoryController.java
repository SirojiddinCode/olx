package com.company.controller.adminController;

import com.company.dto.CategoryDTO;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/category")
@Api(tags = "Category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create_category/by_admin")
    @ApiOperation(value = "Create category for admin method")
    @ApiResponse(code = 200, message = "Successful", response = CategoryDTO.class)
    public HttpEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO dto,
                                                  HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.Admin);
        CategoryDTO response = categoryService.createCategory(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/update/{id}")
    @ApiOperation(value = "update category for admin")
    @ApiResponse(code = 200, message = "Successfull", response = CategoryDTO.class)
    public HttpEntity<CategoryDTO> update(@Valid @ApiParam(value = "Id of Category")
                                              @PathVariable("id") Integer id,
                                              @RequestBody CategoryDTO dto,
                                              HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.Admin);
        CategoryDTO response = categoryService.update(dto, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/delete/")
    @ApiOperation(value = "Delete category for admin")
    @ApiResponse(code = 200, message = "Successfull", response = CategoryDTO.class)
    public HttpEntity<?> delete(@ApiParam(value = "Id of Category") @RequestParam("id") Integer id,
                                HttpServletRequest request) {
        JwtUtil.getProfile(request, ProfileRole.Admin);
        categoryService.delete(id);
        return ResponseEntity.ok("Deleted");
    }

    @GetMapping("/get_category_parent_is_null")
    @ApiOperation(value = "Get general category for all user")
    @ApiResponse(code = 200, message = "Successfull", response = CategoryDTO.class,responseContainer = "List")
    public HttpEntity<List<CategoryDTO>> getCategory() {
        List<CategoryDTO> dtoList = categoryService.getCategoryParents();
        return ResponseEntity.ok(dtoList);
    }

    @GetMapping("/get_child_category")
    public HttpEntity<List<CategoryDTO>> getChildCategory(@ApiParam(value = "id of general category id")
                                                           @RequestParam("id")Integer id){
        List<CategoryDTO> dtoList=categoryService.getCategoryByParentId(id);
        return ResponseEntity.ok(dtoList);
    }

}
