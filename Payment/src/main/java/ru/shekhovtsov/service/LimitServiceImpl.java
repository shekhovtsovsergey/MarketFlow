package ru.shekhovtsov.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.shekhovtsov.dao.LimitRepository;
import ru.shekhovtsov.model.Limit;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class LimitServiceImpl implements LimitService {

    private final LimitRepository limitRepository;

    @Override
    public Limit getLimit(Long clientId) {
        return limitRepository.findByClientId(clientId).orElseGet(() -> {
            Limit newLimit = new Limit(null, clientId, BigDecimal.valueOf(10000.00));
            return limitRepository.save(newLimit);
        });
    }

    @Override
    @Transactional
    public void reduceLimit(Long clientId, BigDecimal amount) {
        Limit limit = getLimit(clientId);
        limit.setDailyLimit(limit.getDailyLimit().subtract(amount));
        limitRepository.save(limit);
    }

    @Override
    @Transactional
    public void restoreLimit(Long clientId, BigDecimal amount) {
        Limit limit = getLimit(clientId);
        limit.setDailyLimit(limit.getDailyLimit().add(amount));
        limitRepository.save(limit);
    }

    @Override
    @Transactional
    //@Scheduled(cron = "0 0 0 * * ?")
    @Scheduled(cron = "0 * * * * ?")
    public void resetLimits() {
        limitRepository.findAll().forEach(limit -> {
            limit.setDailyLimit(BigDecimal.valueOf(10000.00));
            limitRepository.save(limit);
            System.out.println("Лимиты восстановлены");
        });
    }
}
