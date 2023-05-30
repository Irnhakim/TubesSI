package ac.id.unikom.perpusif.Controller;

import ac.id.unikom.perpusif.Helpers.DatabaseHelper;
import ac.id.unikom.perpusif.Models.Buku;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BukuController {

    private Connection connection;
    private static BukuController instance;

    // get instance buku controller
    public static BukuController getInstance() throws SQLException {
        if (instance == null) {
            instance = new BukuController();
        }
        return instance;
    }


 // buat constructor
    private BukuController() throws SQLException {
        connection = DatabaseHelper.getInstance().getConnection();
    }

    public  List<Buku> search(String query) throws Exception{
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM buku WHERE nomor LIKE ? OR judul LIKE ? OR pengarang LIKE ? or tahun_terbit LIKE ?");
        stmt.setString(1, "%" + query + "%");
        stmt.setString(2, "%" + query + "%");
        stmt.setString(3, "%" + query + "%");
        stmt.setString(4, "%" + query + "%");
        ResultSet rs = stmt.executeQuery();
        List<Buku> list = new ArrayList<>();

        while (rs.next()) {
            Buku buku = new Buku(
                    rs.getString("nomor"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getString("tahun_terbit")
            );
            list.add(buku);
        }
        return list;
    }
    //get judul buku
    public String getJudul(String id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT judul FROM buku WHERE nomor = ?");
        stmt.setString(1, id);
        ResultSet rs = stmt.executeQuery();
        String judul = "";

        if (rs.next()) {
            judul = rs.getString("judul");
        }
        return judul;
    }

    // get all buku
    public List<Buku> getAll() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM buku");
        ResultSet rs = stmt.executeQuery();
        List<Buku> list = new ArrayList<>();

        while (rs.next()) {
            Buku buku = new Buku(
                    rs.getString("nomor"),
                    rs.getString("judul"),
                    rs.getString("pengarang"),
                    rs.getString("tahun_terbit")
            );
            list.add(buku);
        }
        return list;
    }
    //insert ke tabel buku
    public void create(Buku buku) throws Exception{
        if (buku.getNomor().isEmpty() || buku.getJudul().isEmpty() || buku.getPengarang().isEmpty() || buku.getTahun_terbit().isEmpty()) {
            throw new Exception("Data tidak boleh kosong");
        }
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM buku WHERE nomor = ?");
        stmt.setString(1, buku.getNomor());
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            throw new Exception("Nomor buku sudah ada");
        }

        stmt = connection.prepareStatement("INSERT INTO buku VALUES (?, ?, ?, ?)");
        stmt.setString(1, buku.getNomor());
        stmt.setString(2, buku.getJudul());
        stmt.setString(3, buku.getPengarang());
        stmt.setString(4, buku.getTahun_terbit());
        stmt.executeUpdate();
    }

    //update tabel buku
    public void update(String judul, String pengarang, String tahun_terbit, String nomor) throws SQLException {
        //validasi inputan kosong
        if (judul.isEmpty() || pengarang.isEmpty() || tahun_terbit.isEmpty() || nomor.isEmpty()) {
            System.out.println("Inputan tidak boleh kosong");
        } else {
            PreparedStatement stmt = connection.prepareStatement("UPDATE buku SET judul = ?, pengarang = ?, tahun_terbit = ? WHERE nomor = ?");
            stmt.setString(1, judul);
            stmt.setString(2, pengarang);
            stmt.setString(3, tahun_terbit);
            stmt.setString(4, nomor);
            stmt.executeUpdate();
        }
    }

    //delete tabel buku
    public void delete(String nomor) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM buku WHERE nomor=?");
        stmt.setString(1, nomor);
        stmt.executeUpdate();
    }

}
