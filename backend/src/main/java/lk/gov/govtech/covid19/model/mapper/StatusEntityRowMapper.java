package lk.gov.govtech.covid19.model.mapper;

import lk.gov.govtech.covid19.model.StatusEntity;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusEntityRowMapper implements RowMapper<StatusEntity> {
    @Override
    public StatusEntity mapRow(ResultSet rs, int i) throws SQLException {
        StatusEntity entity = new StatusEntity();
        entity.setLk_total_case(rs.getInt("lk_total_case"));
        entity.setLk_recovered_case(rs.getInt("lk_recovered_case"));
        entity.setLk_total_deaths(rs.getInt("lk_total_deaths"));
        entity.setLk_total_suspect(rs.getInt("lk_total_suspect"));
        entity.setLast_update_time(rs.getTimestamp("last_update_time"));

        return entity;
    }
}