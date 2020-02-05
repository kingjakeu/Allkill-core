package com.kingjakeu.allkillcore.domain.department.dao;

import com.kingjakeu.allkillcore.domain.department.domain.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
