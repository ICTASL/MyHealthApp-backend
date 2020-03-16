package lk.gov.govtech.covid19.repository;

import lk.gov.govtech.covid19.config.DatasourceConfiguration;
import lk.gov.govtech.covid19.datasource.CovidDatasource;
import lk.gov.govtech.covid19.dto.AlertNotificationRequest;
import lk.gov.govtech.covid19.dto.CaseNotificationRequest;
import lk.gov.govtech.covid19.dto.Location;
import lk.gov.govtech.covid19.model.NotificationEntity;
import lk.gov.govtech.covid19.model.mapper.NotificationEntityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * repository class for accessing any tables from covid19_db
 */
@Repository
public class CovidRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DatasourceConfiguration datasourceConfiguration;

    @PostConstruct
    private void init() {
        jdbcTemplate = new JdbcTemplate(CovidDatasource.getDatasource(datasourceConfiguration));
    }

    public NotificationEntity getNotificationById(String messageId) {
        List<NotificationEntity> notificationList = jdbcTemplate.query("SELECT * FROM notification where " +
                "id = ?", new Object[]{messageId}, new NotificationEntityRowMapper());

        return notificationList.isEmpty() ? null : notificationList.get(0);
    }

    public Integer getLastNotificationId() {
        List<Integer> idList = jdbcTemplate.query("select id from notification ORDER BY id DESC LIMIT 1", (rs, rowNum) -> rs.getInt("id"));

        return idList.isEmpty() ? 0 : idList.get(0);
    }

    public void addAlertNotification(AlertNotificationRequest notification) {

        jdbcTemplate.update("INSERT INTO `notification` (`title`, `subtitle`, `source`, `message_en`, `message_si`, `message_ta`) VALUES (?,?,?,?,?,?)",
                notification.getTitle(), notification.getSubtitle(), notification.getSource(),
                notification.getMessageEn(), notification.getMessageSi(), notification.getMessageTa());
    }

    public void addCaseNotification(CaseNotificationRequest request){
//        jdbcTemplate.update("INSERT INTO `epid_case` (`case_number`, `message_en`, `message_si`, `message_ta`) VALUES (?,?,?,?)",
//               request.getCaseNumber(), request.getMessage_en(), request.getMessage_si(), request.getMessage_ta());

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement("INSERT INTO `epid_case` (`case_number`, `message_en`, `message_si`, `message_ta`) VALUES (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.getCaseNumber());
            ps.setString(2, request.getMessage_en());
            ps.setString(3, request.getMessage_si());
            ps.setString(4, request.getMessage_ta());

            return ps;
        }, holder);

       addCaseLocation(request.getLocations(), holder.getKey().intValue());
    }

    private void addCaseLocation(List<Location> locations, Integer caseId){

        for (Location location : locations){
            jdbcTemplate.update("INSERT INTO `epid_location` (`date`, `from`, `to`, `location`, `case_id`) VALUES (?,?,?,?,?)", location.getDate(), location.getFrom(), location.getTo(), location.getLocation(), caseId);
        }

    }

}

