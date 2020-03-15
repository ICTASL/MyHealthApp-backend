package lk.gov.govtech.covid19.repository;

import lk.gov.govtech.covid19.config.DatasourceConfiguration;
import lk.gov.govtech.covid19.datasource.CovidDatasource;
import lk.gov.govtech.covid19.model.NotificationEntity;
import lk.gov.govtech.covid19.model.mapper.NotificationEntityRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
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
        List<NotificationEntity> notificationList = jdbcTemplate.query("SELECT * FROM hpb_notification where " +
                "id = ?", new Object[]{messageId}, new NotificationEntityRowMapper());

        return notificationList.isEmpty() ? null : notificationList.get(0);
    }

}
