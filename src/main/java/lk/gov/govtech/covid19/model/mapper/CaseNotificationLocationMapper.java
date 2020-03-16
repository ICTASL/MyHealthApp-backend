package lk.gov.govtech.covid19.model.mapper;

import lk.gov.govtech.covid19.dto.Location;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class CaseNotificationLocationMapper implements RowMapper<Location> {

    @Override
    public Location mapRow(ResultSet rs, int i) throws SQLException {
        Location location = new Location();

        location.setDate(rs.getString("date"));
        location.setFrom(rs.getString("from"));
        location.setTo(rs.getString("to"));
        location.setAddress(rs.getString("address"));
        location.setLongitude(rs.getString("longitude"));
        location.setLatitude(rs.getString("latitude"));

        return location;
    }
}
