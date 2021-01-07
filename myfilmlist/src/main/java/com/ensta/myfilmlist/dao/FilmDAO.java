package com.ensta.myfilmlist.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ensta.myfilmlist.exception.DaoException;
import com.ensta.myfilmlist.persistence.ConnectionManager;
import com.ensta.myfilmlist.pojo.FilmPojo;

import org.springframework.stereotype.Repository;



@Repository
public class FilmDAO {
	private static final String COUNT_FILMS = "SELECT COUNT(*) FROM Film;";
	
    private static final String COUNT_FILMS_FILTERED_QUERY = "SELECT COUNT(*) FROM Film, Director WHERE Film.directorID = Director.id AND titre LIKE ? AND (CONCAT(firstName,' ',lastName) LIKE ?);";
    private static final String FIND_FILM_QUERY = "SELECT id, titre, duration, directorID FROM Film WHERE id = ?;";

	private static final String DELETE_QUERY = "DELETE FROM Film WHERE id = ?;";
	private static final String CREATE_QUERY = "INSERT INTO Film(titre, duration, directorID) VALUES(? ,? ,? )";
	private static final String UPDATE_QUERY = "UPDATE Film SET titre = ?, duration = ?, directorID = ? WHERE id = ?;";

	/**
	 * Retourne tous les films de la BDD avec une limite correspondant à la taille d'une page
	 * @return liste des films de la BDD (List<FilmPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public List<FilmPojo> findAll(int number, int size, String order, boolean reverse) throws DaoException {
		List<FilmPojo> resultList = new ArrayList<>();
		String sens = reverse ? "DESC" : "ASC";
		String FIND_FILMS = "SELECT id, titre, duration, directorID FROM Film ORDER BY "+ order +" "+ sens +" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_FILMS);) {
			statement.setInt(1, (number-1)*size);
			statement.setInt(2, size);

			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				FilmPojo film = new FilmPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));

				resultList.add(film);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du SELECT : " + e.getMessage());
		}
	}

		/**
	 * Retourne les films de la BDD corespondant au filtre en argument
	 * @return liste des films de la BDD correspondant au filtre (List<FilmPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public List<FilmPojo> findWithFilter(String titre, String real, int number, int size, String order, boolean reverse) throws DaoException {
		List<FilmPojo> resultList = new ArrayList<>();
		String sens = reverse ? "DESC" : "ASC";
		String FIND_FILMS_FILTERED = "SELECT Film.id, titre, duration, directorID FROM Film, Director WHERE Film.directorID = Director.id AND titre LIKE ? AND (CONCAT(firstName,' ',lastName) LIKE ?) ORDER BY " + order + " " + sens + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_FILMS_FILTERED);) {

			statement.setString(1, '%' + titre + '%');
			statement.setString(2,'%' + real + '%');
			statement.setInt(3,(number-1)*size);
			statement.setInt(4,size);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				FilmPojo film = new FilmPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));
  
				resultList.add(film);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du SELECT : " + e.getMessage());
		}
	}


	/**
	 * Compte tous les films de la BDD 
	 * @return lastNamebre de films dans la bdd (int)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public int countFilms() throws DaoException {
		int total = 0;
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(COUNT_FILMS);) {

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
	 * Compte tous les films de la BDD avec un filtre 
	 * @return lastNamebre de films dans la bdd (int)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public int countFilmsFiltered(String titre, String real) throws DaoException {
		int total = 0;
		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(COUNT_FILMS_FILTERED_QUERY);) {
			statement.setString(1,  '%' + titre + '%');
			statement.setString(2,  '%' + real + '%');
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
	 * Retourne un film à partir de son id
	 * @return liste des films de la BDD (List<FilmPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public FilmPojo findById(String id) throws DaoException {
		FilmPojo film = new FilmPojo();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_FILM_QUERY);) {
			
			statement.setInt(1, Integer.parseInt(id));
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {

				film.setId( resultSet.getInt(1));
				film.setTitre(resultSet.getString(2));
				film.setDuration(resultSet.getInt(3));
				film.setDirectorID(resultSet.getInt(4));

			}
			return film;

		} catch (SQLException e) {
			throw new DaoException("Erreur lors du SELECT : " + e.getMessage());
		}
	}
	
	/**
	 * Crée un film dans la BDD 
	 * @return void
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public void create(FilmPojo film) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE_QUERY);) {
					statement.setString(1, film.getTitre());
					statement.setInt(2, film.getDuration());
					statement.setInt(3, film.getDirectorID());
					statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Error creating film : " + e.getMessage());
		}
	}

	/**
	 * Update un film dans la BDD 
	 * @return void
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public Integer update(FilmPojo film) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE_QUERY);) {
					statement.setString(1, film.getTitre());
					statement.setInt(2, film.getDuration());
					statement.setInt(3, film.getDirectorID());
					statement.setInt(4, (int) film.getId());
					return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur editing film : " + e.getMessage());
		}
	}


	/**
	 * Retourne tous les films de la BDD 
	 * @return liste des films de la BDD (List<FilmPojo>)
	 * @throws DaoException Une erreur est survenue lors de la connexion à la BDD
	 */
    public String delete(String id) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE_QUERY);) {
					statement.setInt(1, Integer.parseInt(id));
					statement.executeUpdate();
					return id;
		} catch (SQLException e) {
			throw new DaoException("Erreur deleting film : " + e.getMessage());
		}
	}
}