package com.javaegitimleri.petclinic.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.javaegitimleri.petclinic.dao.OwnerRepository;
import com.javaegitimleri.petclinic.model.Owner;

@Repository
public class OwnerRepositoryJdbcImpl implements OwnerRepository {
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	private RowMapper<Owner> rowmapper = new RowMapper<Owner>() {

		@Override
		public Owner mapRow(ResultSet rs, int rowNum) throws SQLException {
			// TODO Auto-generated method stub
			Owner owner = new Owner();
			owner.setId(rs.getLong("id"));
			owner.setFirstName(rs.getString("first_name"));
			owner.setLastName(rs.getString("last_name"));
			
			return owner;
		}
		
		
	};
	
	@Override
	public List<Owner> findAll() {
		String sql = "select id,first_name,last_name from t_owner";
		return jdbcTemplate.query(sql, rowmapper);
	}

	@Override
	public Owner findById(Long id) {
		String sql = "select id,first_name,last_name from t_owner where id = ?";
		return DataAccessUtils.singleResult(jdbcTemplate.query(sql, rowmapper , id));
		
	}

	@Override
	public List<Owner> findByLastName(String LastName) {
		String sql = "select id,first_name,last_name from t_owner where last_name like ?";
		return jdbcTemplate.query(sql, rowmapper, "%" + LastName + "%");
	}

	@Override
	public void Create(Owner owner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Owner update(Owner owner) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
        String sql = "delete from t_owner where id = ?";
        jdbcTemplate.update(sql,id);
	}

}
