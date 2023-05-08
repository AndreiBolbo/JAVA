package com.example.guiex1.repository.dbrepo;

import com.example.guiex1.domain.Prietenie;
import com.example.guiex1.domain.Utilizator;
import com.example.guiex1.domain.Validator;
import com.example.guiex1.repository.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


public class FriendshipDbRepository {
    private String url;
    private String username;
    private String password;
    private Validator<Prietenie> validator;

    public FriendshipDbRepository(String url, String username, String password, Validator<Prietenie> validator) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.validator = validator;
    }
    public Optional<Prietenie> findOne(Long id1, Long id2) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from Friendships where Id1=? and id2=?");
        ) {
            statement.setLong(1, id1);
            statement.setLong(2, id2);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id_1 = resultSet.getLong("id1");
                Long id_2 = resultSet.getLong("id2");
                Date date = resultSet.getDate("data");
                String status = resultSet.getString("status");
                Prietenie prietenie = new Prietenie(id_1,id_2,date,status);
                Optional<Prietenie> opt = Optional.ofNullable(prietenie);
                return opt;
            }

        }
        return Optional.empty();
    }

    public Iterable<Prietenie> findAll() {
        Set<Prietenie> friendships = new HashSet<>();
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("SELECT * from Friendships");
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Long id_1 = resultSet.getLong("id1");
                Long id_2 = resultSet.getLong("id2");
                Date date = resultSet.getDate("data");
                String status = resultSet.getString("status");
                Prietenie prietenie = new Prietenie(id_1,id_2,date,status);
                friendships.add(prietenie);
            }
            return friendships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friendships;
    }


    public Optional<Prietenie> save(Prietenie entity) {
        String sql = "insert into friendships ( id1, id2, status, data) values (?, ?,?,?)";
        validator.validate(entity);
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, entity.getId1());
            ps.setLong(2, entity.getId2());
            ps.setString(3,entity.getStatus());
            ps.setDate(4, (Date) entity.getDate());
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            return Optional.ofNullable(entity);
        }
        return Optional.empty();
    }


    public Optional<Prietenie> delete(Long id1,Long id2) throws SQLException {
        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("delete from friendships where Id1=? and id2=?");
        ){
            statement.setLong(1, id1);
            statement.setLong(2,id2);
            //statement.executeQuery();
            statement.executeUpdate();

        }
        return findOne(id1,id2);

    }


    public Optional<Prietenie> update(Long id1,Long id2) throws SQLException {

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement statement = connection.prepareStatement("update  friendships set status=? where id1=? and id2=?");
        ) {
            statement.setString(1, "confirmed");
            statement.setLong(2,id1);
            statement.setLong(3,id2);
            //statement.executeQuery();
            statement.executeUpdate();
        }
        return Optional.empty();
    }

}