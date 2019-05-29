package login.com.department.service.impl;

import login.com.department.model.DepartmentModel;
import login.com.department.repository.DepartmentRepository;
import login.com.department.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentModel save(DepartmentModel model) {
        departmentRepository.save(model);

        return this.findById(model.getId());
    }

    @Override
    public DepartmentModel findById(Integer id) {
        DepartmentModel model = new DepartmentModel();
        model.setId(id);

        model = departmentRepository.findById(model);

        return model;
    }

    @Override
    public DepartmentModel saveDepartment(DepartmentModel model) {
        DepartmentModel existModel = this.findOneByName(model);
        if (existModel != null){
            return null;
        }

        return this.save(model);
    }

    @Override
    public List<DepartmentModel> findAll() {
        return departmentRepository.findAll();
    }

    @Override
    public List<DepartmentModel> findByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }

    @Override
    public DepartmentModel updateDepartment(DepartmentModel model) {
        DepartmentModel existModel = this.findOneByName(model);
        if (existModel != null){
            return null;
        }

        return this.update(model);
    }

    @Override
    public DepartmentModel update(DepartmentModel model) {
        departmentRepository.update(model);

        return this.findById(model.getId());
    }

    @Override
    public DepartmentModel findOneByName(DepartmentModel model) {
        return departmentRepository.findOneByName(model);
    }

    @Override
    public void deleteById(Integer id) {
        departmentRepository.deleteById(id);
    }
}
