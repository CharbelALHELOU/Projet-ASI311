package com.ensta.myfilmlist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.myfilmlist.exception.DaoException;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import com.ensta.myfilmlist.pojo.DirectorPojo;

import org.springframework.stereotype.Repository;

@Repository
public class DirectorDAO {
    private static final String FIND_DIR = "SELECT id, lastName, firstName, birthDate FROM Director;";
    private static final String COUNT_DIR = "SELECT COUNT(*) FROM Director;";
    private static final String FIND_REAL = "SELECT id, lastName, firstName, birthDate FROM Director WHERE id = ?;";
	private static final String DELETE = "DELETE FROM Director WHERE id = ?;";
	private static final String CREATE = "INSERT INTO Director(lastName, firstName, birthDate) VALUES(? ,? ,? )";
	private static final String UPDATE = "UPDATE Director SET lastName = ?, firstName = ?, birthDate = ? WHERE id = ?;";

    public List<DirectorPojo> findAll() throws DaoException {
		List<DirectorPojo> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_DIR);) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				DirectorPojo director = new DirectorPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));

				resultList.add(director);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("Erreur SELECT : " + e.getMessage());
		}
	}
	
    public long countDirector() throws DaoException {
		long total = 0;
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(COUNT_DIR);) {

			ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			total = resultSet.getInt(1);
		}					
		return total;
		} catch (SQLException e) {
			throw new DaoException("Erreur COUNT : " + e.getMessage());
		}
	}

    public DirectorPojo findById(String id) throws DaoException {
		DirectorPojo director = new DirectorPojo();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_REAL);) {
			
			statement.setInt(1, Integer.parseInt(id));
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				director.setId(resultSet.getInt(1));
				director.setLastName(resultSet.getString(2));
                director.setFirstName(resultSet.getString(3));
				director.setDate(resultSet.getDate(4));

			}
			return director;

		} catch (SQLException e) {
			throw new DaoException("Erreur SELECT : " + e.getMessage());
		}
	}

    public void create(DirectorPojo director) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE);) {
					statement.setString(1, director.getLastName());
					statement.setString(2, director.getFirstName());
					statement.setDate(3, director.getDate());
					statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur INSERT : " + e.getMessage());
		}
	}

    public Integer update(DirectorPojo director) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE);) {
					statement.setString(1, director.getLastName());
					statement.setString(2, director.getFirstName());
					statement.setDate(3, director.getDate());
					statement.setInt(4, (int) director.getId());
					return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur UPDATE : " + e.getMessage());
		}
	}


    public String delete(String id) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE);) {
					statement.setInt(1, Integer.parseInt(id));
					statement.executeUpdate();
					return id;
		} catch (SQLException e) {
			throw new DaoException("Erreur DELETE : " + e.getMessage());
		}
	}
}