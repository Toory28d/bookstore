package com.bookstore.service;

import com.bookstore.dto.request.CategoryRequestDto;
import com.bookstore.dto.response.CategoryResponseDto;
import com.bookstore.entity.Category;
import com.bookstore.exception.DuplicateResourceException;
import com.bookstore.exception.ResourceNotFoundException;
import com.bookstore.mapper.CategoryMapper;
import com.bookstore.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    private Category category;
    private CategoryRequestDto categoryRequestDto;
    private CategoryResponseDto categoryResponseDto;

    @BeforeEach
    void setUp() {
        category = new Category();
        category.setId(1L);
        category.setName("Science Fiction");
        category.setDescription("Futuristic books");

        categoryRequestDto = new CategoryRequestDto();
        categoryRequestDto.setName("Science Fiction");
        categoryRequestDto.setDescription("Futuristic books");

        categoryResponseDto = new CategoryResponseDto();
        categoryResponseDto.setId(1L);
        categoryResponseDto.setName("Science Fiction");
        categoryResponseDto.setDescription("Futuristic books");
    }

    @Test
    void testGetAllCategories() {
        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category));
        when(categoryMapper.toResponseDto(any(Category.class))).thenReturn(categoryResponseDto);

        List<CategoryResponseDto> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Science Fiction", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testGetCategoryById_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryMapper.toResponseDto(any(Category.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryService.getCategoryById(1L);

        assertNotNull(result);
        assertEquals("Science Fiction", result.getName());
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testGetCategoryById_NotFound() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> categoryService.getCategoryById(1L));
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCategory_Success() {
        when(categoryRepository.existsByName(anyString())).thenReturn(false);
        when(categoryMapper.toEntity(any(CategoryRequestDto.class))).thenReturn(category);
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponseDto(any(Category.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryService.createCategory(categoryRequestDto);

        assertNotNull(result);
        assertEquals("Science Fiction", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testCreateCategory_DuplicateName() {
        when(categoryRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> categoryService.createCategory(categoryRequestDto));
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testUpdateCategory_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);
        when(categoryMapper.toResponseDto(any(Category.class))).thenReturn(categoryResponseDto);

        CategoryResponseDto result = categoryService.updateCategory(1L, categoryRequestDto);

        assertNotNull(result);
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(category));

        categoryService.deleteCategory(1L);

        verify(categoryRepository, times(1)).delete(any(Category.class));
    }
}