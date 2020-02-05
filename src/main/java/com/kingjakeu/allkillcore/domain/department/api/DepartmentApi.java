package com.kingjakeu.allkillcore.domain.department.api;

import com.kingjakeu.allkillcore.domain.department.dao.DepartmentRepository;
import com.kingjakeu.allkillcore.domain.department.domain.Department;
import com.kingjakeu.allkillcore.domain.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentApi {

    private DepartmentService departmentService;
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentApi(DepartmentService departmentService, DepartmentRepository departmentRepository){
        this.departmentService = departmentService;
        this.departmentRepository = departmentRepository;
    }

    @PostMapping("/crawl")
    public void crawlDepartmentInfo(){
        List<Department> departmentList = departmentService.crawlDepartmentInfoFromSource();
        departmentRepository.saveAll(departmentList);
    }
}
