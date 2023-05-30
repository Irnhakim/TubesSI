package ac.id.unikom.perpusif.Controller;

import ac.id.unikom.perpusif.Helpers.DatabaseHelper;
import ac.id.unikom.perpusif.Models.Anggota;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AnggotaController {
    //koneksi anggota controller
    private Connection connection;
    private static AnggotaController instance;

    // get instance anggota controller
    public static AnggotaController getInstance() throws SQLException {
        if (instance == null) {
            instance = new AnggotaController();
        }
        return instance;
    }

    // buat constructor
    private AnggotaController() throws SQLException {
        connection = DatabaseHelper.getInstance().getConnection();
    }


    //get nama anggota
    public String getNama(String id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT nama FROM anggota WHERE nim = ?");
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        String nama = "";
        if (rs.next()) {
            nama = rs.getString("nama");
        }
        return nama;
    }
    // get all anggota
    public List<Anggota> getAll() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM anggota");
        ResultSet rs = stmt.executeQuery();
        List<Anggota> list = new ArrayList<>();

        while (rs.next()) {
            Anggota anggota = new Anggota(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("no_telepon")
            );
            list.add(anggota);
        }
        return list;
    }

    public List<Anggota> search(String query) throws Exception{
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM anggota WHERE nim LIKE ? OR nama LIKE ? OR no_telepon LIKE ?");
        stmt.setString(1, "%" + query + "%");
        stmt.setString(2, "%" + query + "%");
        stmt.setString(3, "%" + query + "%");
        ResultSet rs = stmt.executeQuery();
        List<Anggota> list = new ArrayList<>();
        while (rs.next()) {
            Anggota anggota = new Anggota(
                    rs.getString("nim"),
                    rs.getString("nama"),
                    rs.getString("no_telepon")
            );
            list.add(anggota);
        }
        return list;
    }

    //insert ke tabel anggota
    public void create(Anggota anggota) throws Exception {
        //validasi inputan kosong
        if (anggota.getNim().isEmpty() || anggota.getNama().isEmpty() || anggota.getNo_telepon().isEmpty()) {
            throw new Exception("Data tidak boleh kosong");
        }

            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM anggota WHERE nim = ?");
            stmt.setString(1, anggota.getNim());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                throw new Exception("Anggota sudah ada");
            }

            stmt = connection.prepareStatement("INSERT INTO anggota (nim, nama, no_telepon) VALUES (?, ?, ?)");
            stmt.setString(1, anggota.getNim());
            stmt.setString(2, anggota.getNama());
            stmt.setString(3, anggota.getNo_telepon());
            stmt.executeUpdate();

    }

    //update tabel anggota
    public void update(String nim, String nama, String no_telepon) throws SQLException {
        //validasi inputan kosong
        if (nim.isEmpty() || nama.isEmpty() || no_telepon.isEmpty()) {
            System.out.println("Inputan tidak boleh kosong");
        } else {
            PreparedStatement stmt = connection.prepareStatement("UPDATE anggota SET nama = ?, no_telepon = ? WHERE nim = ?");
            stmt.setString(1, nama);
            stmt.setString(2, no_telepon);
            stmt.setString(3, nim);
            stmt.executeUpdate();
        }
    }

    //delete tabel anggota
    public void delete(String nim) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM anggota WHERE nim=?");
        stmt.setString(1, nim);
        stmt.executeUpdate();
    }
}
