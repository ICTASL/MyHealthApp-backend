package lk.gov.govtech.covid19.model.mapper;

import lk.gov.govtech.covid19.model.CaseNotificationEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CaseNotificationEntityRowMapper implements RowMapper<CaseNotificationEntity> {
    @Override
    public CaseNotificationEntity mapRow(ResultSet rs, int i) throws SQLException {
        CaseNotificationEntity entity = new CaseNotificationEntity();
        entity.setId(rs.getString("id"));
        entity.setCaseNumber(rs.getString("case_number"));
        entity.setLocal(rs.getBoolean("is_local"));
        entity.setDetectedFrom(rs.getString("detected_from"));
        entity.setMessageEn(rs.getString("message_en"));
        entity.setMessageSi(rs.getString("message_si"));
        entity.setMessageTa(rs.getString("message_ta"));
        entity.setCreated(rs.getString("created"));

        return entity;
    }
}
