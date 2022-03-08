package com.tabeldata.bootcamp.web.controller;

import com.tabeldata.bootcamp.web.dao.CategoryDao;
import com.tabeldata.bootcamp.web.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class OlController {

    @Autowired
    private CategoryDao dao;

    @GetMapping("/list")
    public List<Category> listCategory() {
        return dao.list();
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<?> findById(@PathVariable Integer id) {
        try {
            Category data = this.dao.findById(id);
            return ResponseEntity.ok(data);
        } catch (EmptyResultDataAccessException erdae) {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(value = "/show")
    public Category showData(
            @RequestParam(name = "ctg") Integer category_id,
            @RequestParam(name = "dpt") Integer department,
            @RequestParam(name = "nme") String name,
            @RequestParam(name = "dsc") String description) {
        Category data = new Category();
        data.setCategory_id(category_id);
        data.setDepartment_id(department);
        data.setName(name);
        data.setDescription(description);
        return data;
    }

    @PostMapping(value = "/insert")
    public ResponseEntity<Map<String, Object>>
    insertData(@Valid @RequestBody Category data, BindingResult result) {
        Map<String, Object> hasil = new HashMap<>();

        if (result.hasErrors()){
            hasil.put("status", result.getFieldErrors());
            return ResponseEntity.badRequest().body(hasil);
        }else {
            hasil.put("id", dao.insertData(data));
            hasil.put("status", "Simpan berhasil");
            return ResponseEntity.ok(hasil);
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>>
    updateCategory(@RequestBody Category data) {
        Map<String, Object> hasil = new HashMap<>();
        dao.updateCategory(data);
        hasil.put("id", 0);
        hasil.put("status", "Update Berhasil");
        return ResponseEntity.ok().build();

    }


    @DeleteMapping("/delete/{kodeid}")
    public ResponseEntity<?> delete(@PathVariable Integer kodeid) {
        this.dao.delete(kodeid);
        return ResponseEntity.ok().build();
    }
}

//        try {
//            this.dao.insertData(data);
//            return ResponseEntity.ok().build();
//        } catch (DuplicateKeyException dke) {
//            dke.printStackTrace();
//            return ResponseEntity.badRequest()
//                    .body("Duplicate data");
//        } catch (DataAccessException dea) {
//            dea.printStackTrace();
//            return ResponseEntity.internalServerError()
//                    .body("database gak konek atau sql salah");
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            return ResponseEntity.internalServerError()
//                    .body("Gak tau errornya apa! check sendiri");
//        }