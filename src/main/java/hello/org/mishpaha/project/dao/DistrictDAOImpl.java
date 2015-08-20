package hello.org.mishpaha.project.dao;


import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import hello.org.mishpaha.project.model.District;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DistrictDAOImpl implements DistrictDAO {

    private JdbcTemplate jdbcTemplate;

    public DistrictDAOImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public int saveOrUpdate(District district) {
        if (district.getId() > 0) {
            //update
            String sql = "UPDATE districts SET district=? WHERE id=?";
            return jdbcTemplate.update(sql, district.getDistrict(), district.getId());
        } else {
            //insert
            String sql = "INSERT INTO districts (district) values (?)";
            return jdbcTemplate.update(sql, district.getDistrict());
        }
    }

    @Override
    public int delete(int districtId) {
        String sql = "DELETE FROM districts WHERE id=?";
        return jdbcTemplate.update(sql, districtId);
    }

    @Override
    public District get(int districtId) {
        String sql = "SELECT * FROM districts WHERE id=" + districtId;
        return jdbcTemplate.query(sql, new ResultSetExtractor<District>() {
            @Override
            public District extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    District district = new District();
                    district.setId(rs.getInt("id"));
                    district.setDistrict(rs.getString("district"));
                    return district;
                }
                return null;
            }
        });
    }

    @Override
    public List<District> list() {
        String sql = "SELECT * FROM districts";
        return jdbcTemplate.query(sql, new org.springframework.jdbc.core.RowMapper<District>() {
            @Override
            public District mapRow(ResultSet rs, int rowNum) throws SQLException {
                District district = new District();
                district.setId(rs.getInt("id"));
                district.setDistrict(rs.getString("district"));
                return district;
            }
        });
    }
}
