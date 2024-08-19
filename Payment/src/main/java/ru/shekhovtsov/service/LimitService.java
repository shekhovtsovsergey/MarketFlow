package ru.shekhovtsov.service;

import ru.shekhovtsov.model.Limit;
import java.math.BigDecimal;

public interface LimitService {
    Limit getLimit(Long clientId);
    void reduceLimit(Long clientId, BigDecimal amount);
    void restoreLimit(Long clientId, BigDecimal amount);
    void resetLimits();
}
