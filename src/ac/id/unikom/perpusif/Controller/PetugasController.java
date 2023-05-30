/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.id.unikom.perpusif.Controller;

import ac.id.unikom.perpusif.Helpers.DatabaseHelper;
import ac.id.unikom.perpusif.Helpers.SessionHelper;
import ac.id.unikom.perpusif.Models.Petugas;
import ac.id.unikom.perpusif.Models.Role;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * @author Bagas
 */
public class PetugasController {

    private final Connection connection;
    private static PetugasController instance;

    public static PetugasController getInstance() throws SQLException {
        if (instance == null) {
            instance = new PetugasController();
        }
        return instance;
    }

    private PetugasController() throws SQLException {
        this.connection = DatabaseHelper.getInstance().getConnection();
    }

    private String hash256(String input) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public List<Petugas> getAll() throws Exception {
        if (!SessionHelper.getInstance().isAdmin()) {
            throw new Exception("Anda tidak memiliki akses");
        }

        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM petugas");
        ResultSet rs = stmt.executeQuery();
        List<Petugas> list = new ArrayList<>();

        while (rs.next()) {
            Petugas petugas = new Petugas(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role"))
            );
            list.add(petugas);
        }

        return list;
    }

    public void create(Petugas petugas) throws Exception {
        if (!SessionHelper.getInstance().isAdmin()) {
            throw new Exception("Anda tidak memiliki akses");
        }

        // check input
        if (petugas.getNim().isEmpty() || petugas.getNama().isEmpty() || petugas.getPassword().isEmpty()) {
            throw new Exception("Data tidak lengkap");
        }

        // check if nim already exists
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM petugas WHERE nim = ?");
        stmt.setString(1, petugas.getNim());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            throw new Exception("NIM sudah terdaftar");
        }

        // hash password
        petugas.setPassword(hash256(petugas.getPassword()));

        // insert
        stmt = connection.prepareStatement("INSERT INTO petugas (nim, nama, password, role) VALUES (?, ?, ?, ?)");
        stmt.setString(1, petugas.getNim());
        stmt.setString(2, petugas.getNama());
        stmt.setString(3, petugas.getPassword());
        stmt.setString(4, petugas.getRole().toString());
        stmt.executeUpdate();
    }

    public void delete(String nim) throws Exception {
        if (!SessionHelper.getInstance().isAdmin()) {
            throw new Exception("Anda tidak memiliki akses");
        }

        // check if nim exists
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM petugas WHERE nim = ?");
        stmt.setString(1, nim);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            throw new Exception("NIM tidak ditemukan");
        }

        // delete
        stmt = connection.prepareStatement("DELETE FROM petugas WHERE nim = ?");
        stmt.setString(1, nim);
        stmt.executeUpdate();
    }

    /**
     * Update petugas, jika password kosong, maka password yang lama akan tetap digunakan.
     *
     * @param petugas Petugas yang akan diubah
     * @throws Exception Jika ada error
     * @author Bagas
     */
    public void update(Petugas petugas) throws Exception {
        if (!SessionHelper.getInstance().isAdmin()) {
            throw new Exception("Anda tidak memiliki akses");
        }

        // check input
        if (petugas.getNim().isEmpty() || petugas.getNama().isEmpty()) {
            throw new Exception("Data tidak lengkap");
        }

        // check if nim exists
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM petugas WHERE nim = ?");
        stmt.setString(1, petugas.getNim());
//        ResultSet rs = stmt.executeQuery();
//        if (!rs.next()) {
//            throw new Exception("NIM tidak ditemukan");
//        }

        // if password is empty, do not update password
        if (!petugas.getPassword().isEmpty()) {
            // hash password
            petugas.setPassword(hash256(petugas.getPassword()));

            // update password
            stmt = connection.prepareStatement("UPDATE petugas SET password = ? WHERE nim = ?");
            stmt.setString(1, petugas.getPassword());
            stmt.setString(2, petugas.getNim());
            stmt.executeUpdate();
        }

        // update other fields
        stmt = connection.prepareStatement("UPDATE petugas SET nama = ?, role=?  WHERE nim = ?");
        stmt.setString(1, petugas.getNama());
        stmt.setString(2, petugas.getRole().toString());
        stmt.setString(3, petugas.getNim());
        stmt.executeUpdate();


    }

    public List<Petugas> search(String query) throws Exception {
        if (!SessionHelper.getInstance().isAdmin()) {
            throw new Exception("Anda tidak memiliki akses");
        }

        // search
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM petugas WHERE nim LIKE ? OR nama LIKE ?");
        stmt.setString(1, "%" + query + "%");
        stmt.setString(2, "%" + query + "%");
        ResultSet rs = stmt.executeQuery();
        List<Petugas> list = new ArrayList<>();

        while (rs.next()) {
            Petugas petugas = new Petugas(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("password"),
                    Role.valueOf(rs.getString("role"))
            );
            list.add(petugas);
        }

        return list;
    }

    public void login(String nim, String password) throws Exception {
        if (nim.isEmpty() || password.isEmpty()) {
            throw new Exception("Data tidak boleh kosong");
        }

        // hash password using sha-256 algorithm
        String hashedPassword = hash256(password);
        System.out.println(nim);
        System.out.println(hashedPassword);

        PreparedStatement ps = connection.prepareStatement("SELECT * FROM petugas WHERE nim = ? AND password = ?");
        ps.setString(1, nim);
        ps.setString(2, hashedPassword);
        ResultSet res = ps.executeQuery();

        if (!res.next()) {
            throw new Exception("NIM atau Password salah");
        }

        Petugas Petugas = new Petugas(res.getString("nim"), res.getString("nama"), res.getString("password"), Role.valueOf(res.getString("role")));
        SessionHelper.getInstance().setPetugas(Petugas);
        res.close();
    }
}
