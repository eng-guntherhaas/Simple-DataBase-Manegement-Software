package com.example.workshop.model.services;

import com.example.workshop.dao.DaoFactory;
import com.example.workshop.dao.DepartmentDao;
import com.example.workshop.model.entities.Department;

import java.util.List;

public class DepartmentService {

    private DepartmentDao dao = DaoFactory.createDepartmentDao();

    public List<Department> findAll() {
        return dao.findAll();
    }
}
