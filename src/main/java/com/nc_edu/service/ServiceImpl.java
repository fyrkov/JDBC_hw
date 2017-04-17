package com.nc_edu.service;

import com.nc_edu.repository.IRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by anch0317 on 13.04.2017.
 */
@Component
public class ServiceImpl implements IService {

    @Autowired
    private IRepository repo;
    private final Logger log = LoggerFactory.getLogger(ServiceImpl.class);

    @Override
    public void exit() {
        repo.exit();
    }

    @Override
    public void select(String arg) {
        repo.select(arg);
    }

    @Override
    public void insert(List<String> params) {
        repo.insert(params);
    }

    @Override
    public void delete(String arg) {
        repo.delete(arg);
    }

    @Override
    public void find(String arg) {
        repo.find(arg);
    }
}
