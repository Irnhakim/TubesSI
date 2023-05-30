package ac.id.unikom.perpusif.Controller;

import ac.id.unikom.perpusif.Helpers.DatabaseHelper;
import ac.id.unikom.perpusif.Models.Kunjungan;
import ac.id.unikom.perpusif.Models.ViewKunjungan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class KunjunganController {
    //koneksi
    private Connection connection;
    //buat instance kunjungan controller
    private static KunjunganController instance;

    //get instance kunjungan controller
    public static KunjunganController getInstance() throws SQLException {
        if (instance == null) {
            instance = new KunjunganController();
        }
        return instance;
    }

    //buat constructor
    private KunjunganController() throws SQLException {
        connection = DatabaseHelper.getInstance().getConnection();
    }

    public List<ViewKunjungan> search(String query) throws Exception{
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM viewkunjungan WHERE id LIKE ? OR nama_anggota LIKE ? OR tanggal_kunjungan LIKE ? OR nama_petugas LIKE ?");
        stmt.setString(1, "%" + query + "%");
        stmt.setString(2, "%" + query + "%");
        stmt.setString(3, "%" + query + "%");
        stmt.setString(4, "%" + query + "%");
        ResultSet rs = stmt.executeQuery();
        List<ViewKunjungan> list = new ArrayList<>();

        while (rs.next()) {
            ViewKunjungan kunjungan = new ViewKunjungan(
                    rs.getString("id"),
                    rs.getString("nama_anggota"),
                    rs.getString("tanggal_kunjungan"),
                    rs.getString("nama_petugas")
            );
            list.add(kunjungan);
        }
        return list;
    }

    // get satu baris data
    public Kunjungan get(int id) throws Exception{
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM kunjungan where id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
 
        if(rs.next()){
            String idKunjungan = rs.getString("id");
            String nimAnggota = rs.getString("nim_anggota");
            String nimPetugas = rs.getString("nim_petugas");
            String tanggalKunungan = rs.getString("tanggal_kunjungan");
            Kunjungan kunjungan = new Kunjungan(idKunjungan, nimAnggota, nimPetugas, tanggalKunungan);
            return kunjungan;
        }else{
            throw new Exception("Kunjungan tidak ditemukan");
        }
        
    }
    
    // get view kunjungan
    public List<ViewKunjungan> getView() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM viewkunjungan ");
        ResultSet rs = stmt.executeQuery();
        List<ViewKunjungan> list = new ArrayList<>();

        while (rs.next()) {
            ViewKunjungan viewKunjungan = new ViewKunjungan(
                    rs.getString("id"),
                    rs.getString("nama_anggota"),
                    rs.getString("nama_petugas"),
                    rs.getString("tanggal_kunjungan")
            );
            list.add(viewKunjungan);
        }
        return list;
    }

    //get nama pengunjungan
    //get all kunjungan
    public List<Kunjungan> getAll() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM kunjungan");
        ResultSet rs = stmt.executeQuery();
        List<Kunjungan> list = new ArrayList<>();

        while (rs.next()) {
            Kunjungan kunjungan = new Kunjungan(
                    rs.getString("id"),
                    rs.getString("nim_anggota"),
                    rs.getString("nim_petugas"),
                    rs.getString("tanggal_kunjungan")
            );
            list.add(kunjungan);
        }
        return list;
    }
    //insert tabel kunjungan
    public void create(Kunjungan kunjungan) throws Exception{

        PreparedStatement stmt = connection.prepareStatement("INSERT INTO kunjungan(nim_anggota, nim_petugas, tanggal_kunjungan) VALUES (?,?,?)");
        stmt.setString(1, kunjungan.getNim_anggota());
        stmt.setString(2, kunjungan.getNim_petugas());
        stmt.setString(3, kunjungan.getTgl_kunjungan());
        stmt.executeUpdate();
    }

    //update tabel kunjungan
    public void update(String id, String nim_anggota, String nim_petugas, String tgl_kunjungan) throws SQLException {
        //validasi inputan kosong
        if (id.isEmpty() || nim_anggota.isEmpty() || nim_petugas.isEmpty() || tgl_kunjungan.isEmpty()) {
            System.out.println("Inputan tidak boleh kosong");
        } else {
            PreparedStatement stmt = connection.prepareStatement("UPDATE kunjungan SET nim_anggota = ?, nim_petugas = ?, tanggal_kunjungan = ? WHERE id = ?");
            stmt.setString(1, nim_anggota);
            stmt.setString(2, nim_petugas);
            stmt.setString(3, tgl_kunjungan);
            stmt.setString(4, id);
            stmt.executeUpdate();
        }
    }

    //delete tabel kunjungan
    public void delete(String id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM kunjungan WHERE id=?");
        stmt.setString(1, id);
        stmt.executeUpdate();
    }
}