package lk.gov.govtech.covid19.datasource;

import lk.gov.govtech.covid19.config.DatasourceConfiguration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

public class CovidDatasource {
    private CovidDatasource(){}

    public static DataSource getDatasource(DatasourceConfiguration datasourceConfiguration) {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(datasourceConfiguration.getDriverClass());
        dataSource.setUrl(datasourceConfiguration.getUrl());
        dataSource.setUsername(datasourceConfiguration.getUsername());
        dataSource.setPassword(datasourceConfiguration.getPassword());

        return dataSource;
    }
}
