package com.nc_edu;

import java.util.List;

/**
 * Created by anch0317 on 13.04.2017.
 */
public class ServiceImpl implements IService {

    private IRepository repo = new RepositoryImpl();

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
