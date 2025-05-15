package crud.demo.controller;

import crud.demo.model.Brand;
import crud.demo.service.BrandService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/brands")
public class BrandController {
    
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public Optional<Brand> getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id);
    }
}
