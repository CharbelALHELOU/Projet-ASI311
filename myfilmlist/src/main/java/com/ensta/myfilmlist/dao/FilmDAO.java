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
	
    private static final String COUNT_FILMS_FILTERED = "SELECT COUNT(*) FROM Film, Director WHERE Film.directorID = Director.id AND UPPER(titre) LIKE UPPER(?) AND (UPPER(CONCAT(firstName,' ',lastName)) LIKE UPPER(?));";
    private static final String FIND_FILM = "SELECT id, titre, duration, directorID FROM Film WHERE id = ?;";

	private static final String DELETE = "DELETE FROM Film WHERE id = ?;";
	private static final String CREATE = "INSERT INTO Film(titre, duration, directorID) VALUES(? ,? ,? )";
	private static final String UPDATE = "UPDATE Film SET titre = ?, duration = ?, directorID = ? WHERE id = ?;";


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
			throw new DaoException("Erreur SELECT : " + e.getMessage());
		}
	}

    public List<FilmPojo> findWithFilter(String titre, String director, int number, int size, String order, boolean reverse) throws DaoException {
		List<FilmPojo> resultList = new ArrayList<>();
		String sens = reverse ? "DESC" : "ASC";
		String FIND_FILMS_FILTERED = "SELECT Film.id, titre, duration, directorID FROM Film, Director WHERE Film.directorID = Director.id AND UPPER(titre) LIKE UPPER(?) AND (UPPER(CONCAT(firstName,' ',lastName)) LIKE UPPER(?)) ORDER BY " + order + " " + sens + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_FILMS_FILTERED);) {

			statement.setString(1, '%' + titre + '%');
			statement.setString(2,'%' + director + '%');
			statement.setInt(3,(number-1)*size);
			statement.setInt(4,size);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {

				FilmPojo film = new FilmPojo(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getInt(4));
  
				resultList.add(film);
			}
			return resultList;

		} catch (SQLException e) {
			throw new DaoException("Erreur SELECT : " + e.getMessage());
		}
	}


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
			throw new DaoException("Erreur COUNT : " + e.getMessage());
		}
	}


    public int countFilmsFiltered(String titre, String director) throws DaoException {
		int total = 0;
		try (Connection conn = ConnectionManager.getConnection();
			PreparedStatement statement = conn.prepareStatement(COUNT_FILMS_FILTERED);) {
			statement.setString(1,  '%' + titre + '%');
			statement.setString(2,  '%' + director + '%');
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				total = resultSet.getInt(1);
			}					
		return total;
		} catch (SQLException e) {
			throw new DaoException("Erreur COUNT : " + e.getMessage());
		}
	}

    public FilmPojo findById(String id) throws DaoException {
		FilmPojo film = new FilmPojo();
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(FIND_FILM);) {
			
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
			throw new DaoException("Erreur SELECT : " + e.getMessage());
		}
	}
	
    public void create(FilmPojo film) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(CREATE);) {
					statement.setString(1, film.getTitre());
					statement.setInt(2, film.getDuration());
					statement.setInt(3, film.getDirectorID());
					statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Error creating film : " + e.getMessage());
		}
	}

    public Integer update(FilmPojo film) throws DaoException {
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(UPDATE);) {
					statement.setString(1, film.getTitre());
					statement.setInt(2, film.getDuration());
					statement.setInt(3, film.getDirectorID());
					statement.setInt(4, (int) film.getId());
					return statement.executeUpdate();
		} catch (SQLException e) {
			throw new DaoException("Erreur editing film : " + e.getMessage());
		}
	}


    public String delete(String id) throws DaoException {
		
		try (Connection conn = ConnectionManager.getConnection();
				PreparedStatement statement = conn.prepareStatement(DELETE);) {
					statement.setInt(1, Integer.parseInt(id));
					statement.executeUpdate();
					return id;
		} catch (SQLException e) {
			throw new DaoException("Erreur deleting film : " + e.getMessage());
		}
	}
}