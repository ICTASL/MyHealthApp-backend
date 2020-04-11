package lk.gov.govtech.covid19.repository;

import lk.gov.govtech.covid19.dto.AlertNotification;
import lk.gov.govtech.covid19.dto.CaseNotificationRequest;
import lk.gov.govtech.covid19.dto.Location;
import lk.gov.govtech.covid19.dto.UpdateStatusRequest;
import lk.gov.govtech.covid19.model.AlertNotificationEntity;
import lk.gov.govtech.covid19.model.CaseNotificationEntity;
import lk.gov.govtech.covid19.model.StatusEntity;
import lk.gov.govtech.covid19.model.mapper.AlertNotificationEntityRowMapper;
import lk.gov.govtech.covid19.model.mapper.CaseNotificationEntityRowMapper;
import lk.gov.govtech.covid19.model.mapper.CaseNotificationLocationMapper;
import lk.gov.govtech.covid19.model.mapper.StatusEntityRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

/**
 * repository class for accessing any tables from covid19_db
 */
@Slf4j
@Repository
public class CovidRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public AlertNotificationEntity getAlertNotificationById(String alertId) {
        List<AlertNotificationEntity> notificationList = jdbcTemplate.query("SELECT * FROM notification where " +
                "id = ?", new Object[]{alertId}, new AlertNotificationEntityRowMapper());

        return notificationList.isEmpty() ? null : notificationList.get(0);
    }

    public Integer getLastAlertNotificationId() {
        List<Integer> idList = jdbcTemplate.query("select id from notification ORDER BY id DESC LIMIT 1", (rs, rowNum) -> rs.getInt("id"));

        return idList.isEmpty() ? 0 : idList.get(0);
    }

    public int addAlertNotification(AlertNotification notification) {
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `notification` (`source`, `title_en`, `title_si`, `title_ta`, `message_en`, `message_si`, `message_ta`) VALUES (?,?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, notification.getSource());
            ps.setString(2, notification.getTitle().getEnglish());
            ps.setString(3, notification.getTitle().getSinhala());
            ps.setString(4, notification.getTitle().getTamil());
            ps.setString(5, notification.getMessage().getEnglish());
            ps.setString(6, notification.getMessage().getSinhala());
            ps.setString(7, notification.getMessage().getTamil());

            return ps;
        }, holder);

        return holder.getKey().intValue();
    }

    public boolean updateAlertNotification(String alertId, AlertNotification notification) {
        int i = jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("UPDATE `notification` " +
                            "SET source=?, title_en=?, title_si=?, title_ta=?, message_en=?, message_si=?, message_ta=? " +
                            "WHERE id=?");
            ps.setString(1, notification.getSource());
            ps.setString(2, notification.getTitle().getEnglish());
            ps.setString(3, notification.getTitle().getSinhala());
            ps.setString(4, notification.getTitle().getTamil());
            ps.setString(5, notification.getMessage().getEnglish());
            ps.setString(6, notification.getMessage().getSinhala());
            ps.setString(7, notification.getMessage().getTamil());
            ps.setString(8, alertId);

            return ps;
        });
        return i > 0;
    }

    public int addCaseNotification(CaseNotificationRequest request) {
//        jdbcTemplate.update("INSERT INTO `epid_case` (`case_number`, `message_en`, `message_si`, `message_ta`) VALUES (?,?,?,?)",
//               request.getCaseNumber(), request.getMessage_en(), request.getMessage_si(), request.getMessage_ta());

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `epid_case` (`case_number`, `is_local`, `detected_from`, `message_en`, `message_si`, `message_ta`) VALUES (?,?,?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.getCaseNumber());
            ps.setBoolean(2, request.isLocal());
            ps.setString(3, request.getDetectedFrom());





            ps.setString(4, request.getMessage_en());
            ps.setString(5, request.getMessage_si());
            ps.setString(6, request.getMessage_ta());

            return ps;
        }, holder);

        addCaseLocation(request.getLocations(), holder.getKey().intValue());

        return holder.getKey().intValue();
    }

    private void addCaseLocation(List<Location> locations, Integer caseId) {

        for (Location location : locations) {
            jdbcTemplate.update("INSERT INTO `epid_location` (`case_id`, `date`, `area`, `longitude`, `latitude`) VALUES (?,?,?,?,?)",
                    caseId, location.getDate(), location.getArea(), location.getLongitude(), location.getLatitude());
        }

    }

    public CaseNotificationEntity getCaseNotificationById(String caseId) {
        List<CaseNotificationEntity> caseList = jdbcTemplate.query("SELECT * FROM epid_case where " +
                "id = ?", new Object[]{caseId}, new CaseNotificationEntityRowMapper());

        return caseList.isEmpty() ? null : caseList.get(0);
    }

    public List<Location> getCaseNotificationLocations(String caseId) {
        return jdbcTemplate.query("SELECT * FROM epid_location where case_id = ?", new Object[]{caseId} , new CaseNotificationLocationMapper());

    }

    public Integer getLastCaseNotificationId() {
        List<Integer> idList = jdbcTemplate.query("select id from epid_case ORDER BY id DESC LIMIT 1", (rs, rowNum) -> rs.getInt("id"));

        return idList.isEmpty() ? 0 : idList.get(0);
    }

    public StatusEntity getStatus() {
        List<StatusEntity> statusList =  jdbcTemplate.query("select * from covid_status", new StatusEntityRowMapper());
        return statusList.isEmpty() ? null : statusList.get(0);

    }

    public void updateStatus(UpdateStatusRequest request) {
        jdbcTemplate.update("update covid_status set lk_total_case=?, lk_recovered_case=?, lk_total_deaths=?, lk_total_suspect=? where id=?",
                request.getLk_total_case() , request.getLk_recovered_case(), request.getLk_total_deaths(), request.getLk_total_suspect(), 1);
    }

}

