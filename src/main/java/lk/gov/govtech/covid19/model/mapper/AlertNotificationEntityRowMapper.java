package lk.gov.govtech.covid19.model.mapper;

import lk.gov.govtech.covid19.model.AlertNotificationEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlertNotificationEntityRowMapper implements RowMapper<AlertNotificationEntity> {
    @Override
    public AlertNotificationEntity mapRow(ResultSet rs, int i) throws SQLException {
        AlertNotificationEntity entity = new AlertNotificationEntity();
        entity.setId(rs.getString("id"));
        entity.setSource(rs.getString("source"));
        entity.setTitleEn(rs.getString("title_en"));
        entity.setTitleSi(rs.getString("title_si"));
        entity.setTitleTa(rs.getString("title_ta"));
        entity.setMessageEn(rs.getString("message_en"));
        entity.setMessageSi(rs.getString("message_si"));
        entity.setMessageTa(rs.getString("message_ta"));
        entity.setCreatedTime(rs.getString("created"));

        return entity;
    }
}
