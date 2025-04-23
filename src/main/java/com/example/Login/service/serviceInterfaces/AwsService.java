package com.example.Login.service.serviceInterfaces;

import java.util.List;

public interface AwsService {
    public List<?> listInstances(Long accountId);
}
