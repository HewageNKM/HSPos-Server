package com.nadunkawishika.helloshoesapplicationserver.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nadunkawishika.helloshoesapplicationserver.dto.EmployeeDTO;
import com.nadunkawishika.helloshoesapplicationserver.entity.Employee;
import com.nadunkawishika.helloshoesapplicationserver.exception.customExceptions.NotFoundException;
import com.nadunkawishika.helloshoesapplicationserver.repository.EmployeeRepository;
import com.nadunkawishika.helloshoesapplicationserver.service.EmployeeService;
import com.nadunkawishika.helloshoesapplicationserver.util.GenerateId;
import com.nadunkawishika.helloshoesapplicationserver.util.ImageUtil;
import com.nadunkawishika.helloshoesapplicationserver.util.Mapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private final Mapper mapper;
    private final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final ImageUtil imageUtil;
    private final ObjectMapper json;

    @Override
    public List<EmployeeDTO> getEmployees(int page, int limit) {
        LOGGER.info("Get All Employees Request");
        Pageable pageable = PageRequest.of(page, limit);
        return mapper.toEmployeesEntityToDTOs(employeeRepository.findAll(pageable).getContent());
    }

    @Override
    public EmployeeDTO getEmployee(String id) {
        return mapper.toEmployeeEntityToDTO(employeeRepository.findById(id).orElseThrow(() -> {
            LOGGER.error("Employee Not Found: {}", id);
            return new NotFoundException("Employee Not Found");
        }));
    }

    @Override
    public void saveEmployee(String emp, MultipartFile image) throws IOException {
        EmployeeDTO dto = json.readValue(emp, EmployeeDTO.class);
        employeeRepository.getEmployeeByEmail(dto.getEmail().toLowerCase()).ifPresent(employee -> {
            LOGGER.error("Employee Email Exists: {}", dto.getEmail());
            throw new NotFoundException("Employee Email Exists");
        });

        employeeRepository.getEmployeeByContact(dto.getContact()).ifPresent(employee -> {
            LOGGER.error("Employee Contact Exists: {}", dto.getContact());
            throw new NotFoundException("Employee Contact Exists");
        });
        String id = GenerateId.getId("EMP");
        Employee employee = Employee
                .builder()
                .email(dto.getEmail().toLowerCase())
                .employeeId(id.toLowerCase())
                .name(dto.getName().toLowerCase())
                .lane(dto.getLane().toLowerCase())
                .city(dto.getCity().toLowerCase())
                .state(dto.getState().toLowerCase())
                .gender(dto.getGender())
                .dob(dto.getDob())
                .doj(dto.getDoj())
                .designation(dto.getDesignation().toLowerCase())
                .role(dto.getRole())
                .status(dto.getStatus())
                .image(image != null ? imageUtil.encodeImage(image) : dto.getImage())
                .attachBranch(dto.getAttachBranch().toLowerCase())
                .guardianName(dto.getGuardianName().toLowerCase())
                .guardianContact(dto.getGuardianContact())
                .contact(dto.getContact())
                .postalCode(dto.getPostalCode())
                .build();
        LOGGER.info("Employee Saved Id: {}", id);
        employeeRepository.save(employee);

    }

    @Override
    public void updateEmployee(String id, String emp, MultipartFile image) throws IOException {
        EmployeeDTO dto = json.readValue(emp, EmployeeDTO.class);
        Optional<Employee> employee = employeeRepository.findById(id);

        if (employee.isPresent()) {
            Employee dbEmployee = employee.get();

            dbEmployee.setName(dto.getName().toLowerCase());
            dbEmployee.setLane(dto.getLane().toLowerCase());
            dbEmployee.setCity(dto.getCity().toLowerCase());
            dbEmployee.setState(dto.getState().toLowerCase());
            dbEmployee.setGender(dto.getGender());
            dbEmployee.setDob(dto.getDob());
            dbEmployee.setDoj(dto.getDoj());
            dbEmployee.setDesignation(dto.getDesignation().toLowerCase());
            dbEmployee.setRole(dto.getRole());
            dbEmployee.setStatus(dto.getStatus());
            dbEmployee.setAttachBranch(dto.getAttachBranch().toLowerCase());
            dbEmployee.setGuardianName(dto.getGuardianName().toLowerCase());
            dbEmployee.setGuardianContact(dto.getGuardianContact());
            dbEmployee.setContact(dto.getContact());
            dbEmployee.setPostalCode(dto.getPostalCode());
            dbEmployee.setEmail(dto.getEmail().toLowerCase());

            if (image != null) {
                dbEmployee.setImage(imageUtil.encodeImage(image));
            }
            employeeRepository.save(dbEmployee);
            LOGGER.info("Employee Updated");
        } else {
            LOGGER.error("Employee Not Found: {}", id);
            throw new NotFoundException("Employee Not Found");
        }
    }

    @Override
    public void deleteEmployee(String id) {
        LOGGER.info("Delete Employee Request: {}", id);
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDTO> filterEmployees(String pattern) {
        LOGGER.info("Filter Employees Request: {}", pattern);
        return mapper.toEmployeesEntityToDTOs(employeeRepository.filterEmployee(pattern));
    }
}
