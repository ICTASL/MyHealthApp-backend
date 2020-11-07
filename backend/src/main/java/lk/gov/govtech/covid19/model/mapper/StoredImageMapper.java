package lk.gov.govtech.covid19.model.mapper;

import lk.gov.govtech.covid19.dto.StoredImage;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;


public class StoredImageMapper implements RowMapper<StoredImage> {
    @Override
    public StoredImage mapRow(ResultSet resultSet, int i) throws SQLException {
        StoredImage si = new StoredImage();
        si.setName(resultSet.getString("name"));
        Blob blob = resultSet.getBlob("image");
        si.setImage(blob.getBytes(1, (int) blob.length()));
        return si;
    }
}
