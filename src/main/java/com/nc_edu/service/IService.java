package com.nc_edu.service;

import java.util.List;

/**
 * Created by anch0317 on 13.04.2017.
 */
public interface IService {
    void exit();
    void select(String arg);
    void insert(List<String> params);
    void delete(String arg);
    void find(String arg);
}
