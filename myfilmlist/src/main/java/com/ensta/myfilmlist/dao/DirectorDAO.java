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
    private static final String FIND_REALS_QUERY = "SELECT id, lastName, firstName, birthDate FROM Director;";
    private static final String FIND_REALS_FILTER_QUERY = "SELECT id, lastName, firstName, birthDate FROM Director WHERE (lastName LIKE ? OR firstName LIKE ?);";
    private static final String COUNT_REALS_QUERY = "SELECT COUNT(*) FROM Director;";
    private static final String FIND_REAL_QUERY = "SELECT id, lastName, firstName, birthDate FROM Director WHERE id = ?;";
	private static final String DELETE_QUERY = "DELETE FROM Director WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO Director(lastName, firstName, birthDate) VALUES(? ,? ,? )";
	private static final String UPDATE_QUERY = "UPDATE Director SET lastName = ?, firstName = ?, birthDate = ? WHERE id = ?;";

	/**
	 * Retourne tous les directors de la BDD 
	 * @return liste des directors de la BDD (List<DirectorPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public List<DirectorPojo> findAll() throws DaoException {
		List<DirectorPojo> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_REALS_QUERY);) {
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				DirectorPojo director = new DirectorPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));

				resultList.add(director);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du SELECT : " + e.getMessage());
		}
	}
	
	/**
	 * Compte tous les reals de la BDD 
	 * @return lastNamebre de reals dans la bdd (int)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public long countReals() throws DaoException {
		long total = 0;
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(COUNT_REALS_QUERY);) {

			ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			total = resultSet.getInt(1);
		}					
		return total;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du COUNT : " + e.getMessage());
		}
	}

	/**
	 * Retourne un director à partir de son id
	 * @return liste des directors de la BDD (List<DirectorPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public DirectorPojo findById(String id) throws DaoException {
		DirectorPojo director = new DirectorPojo();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_REAL_QUERY);) {
			
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
			throw new DaoException("Erreur lors du SELECT : " + e.getMessage());
		}
	}

	/**
	 * Retourne tous les directors de la BDD 
	 * @return liste des directors de la BDD (List<DirectorPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public List<DirectorPojo> findWithFilter(String real) throws DaoException {
		List<DirectorPojo> resultList = new ArrayList<>();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_REALS_FILTER_QUERY);) {

			statement.setString(1, '%' + real + '%');
			statement.setString(2, '%' + real + '%');

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				DirectorPojo director = new DirectorPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));

				resultList.add(director);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du SELECT : " + e.getMessage());
		}
	}

	/**
	 * Crée un director dans la BDD 
	 * @return void
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public void create(DirectorPojo director) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE_QUERY);) {
					statement.setString(1, director.getLastName());
					statement.setString(2, director.getFirstName());
					statement.setDate(3, director.getDate());
					statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du INSERT : " + e.getMessage());
		}
	}

	/**
	 * Update un director dans la BDD 
	 * @return void
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public Integer update(DirectorPojo director) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);) {
					statement.setString(1, director.getLastName());
					statement.setString(2, director.getFirstName());
					statement.setDate(3, director.getDate());
					statement.setInt(4, (int) director.getId());
					return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du UPDATE : " + e.getMessage());
		}
	}


	/**
	 * Retourne tous les directors de la BDD 
	 * @return liste des directors de la BDD (List<DirectorPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public String delete(String id) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);) {
					statement.setInt(1, Integer.parseInt(id));
					statement.executeUpdate();
					return id;
		} catch (SQLException e) {
			throw new DaoException("Erreur lors du DELETE : " + e.getMessage());
		}
	}
}