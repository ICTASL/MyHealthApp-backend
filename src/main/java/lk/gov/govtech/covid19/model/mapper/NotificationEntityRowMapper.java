package lk.gov.govtech.covid19.model.mapper;

import lk.gov.govtech.covid19.model.NotificationEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationEntityRowMapper implements RowMapper<NotificationEntity> {
    @Override
    public NotificationEntity mapRow(ResultSet rs, int i) throws SQLException {
        NotificationEntity entity = new NotificationEntity();
        entity.setId(rs.getString("id"));
        entity.setTitle(rs.getString("title"));
        entity.setSubtitle(rs.getString("subtitle"));
        entity.setSource(rs.getString("source"));
        entity.setMessageEn(rs.getString("message_en"));
        entity.setMessageSi(rs.getString("message_si"));
        entity.setMessageTa(rs.getString("message_ta"));
        entity.setCreatedTime(rs.getString("created"));

        return entity;
    }
}
