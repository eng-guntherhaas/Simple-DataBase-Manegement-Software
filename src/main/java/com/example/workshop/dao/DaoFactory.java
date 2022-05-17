package com.example.workshop.dao;


import com.example.workshop.dao.impl.DepartmentDaoJDBC;
import com.example.workshop.dao.impl.SellerDaoJDBC;
import com.example.workshop.db.DB;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection());
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }
}
