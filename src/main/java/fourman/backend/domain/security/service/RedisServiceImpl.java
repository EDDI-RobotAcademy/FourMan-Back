package fourman.backend.domain.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService {

    final private StringRedisTemplate redisTemplate;

    @Override
    public void setKeyAndValue(String token, Long memberId) {
        String memberIdString = String.valueOf(memberId);
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        value.set(token, memberIdString, Duration.ofMinutes(180));
    }

    @Override
    public Long getValueByKey(String token) {
        ValueOperations<String, String> value = redisTemplate.opsForValue();
        String tempMemberId = value.get(token);
        Long memberId;

        if (tempMemberId == null) { memberId = null; }//토큰으로검색한 유저id가 없을때
        else { memberId = Long.parseLong(tempMemberId); }//유저id가 있을때

        return memberId;
    }

    @Override
    public void deleteByKey(String token) {
        redisTemplate.delete(token);
    }

    public boolean isRefreshTokenExists(String token) {
        return getValueByKey(token) != null;
    }
}