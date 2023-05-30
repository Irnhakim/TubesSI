package ac.id.unikom.perpusif.Controller;

import ac.id.unikom.perpusif.Dto.PengembalianDto;
import ac.id.unikom.perpusif.Helpers.DatabaseHelper;
import ac.id.unikom.perpusif.Helpers.SessionHelper;
import ac.id.unikom.perpusif.Models.Peminjaman;
import ac.id.unikom.perpusif.Models.ViewPeminjaman;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeminjamanController<Public> {
    private Connection connection;
    private static PeminjamanController instance;


    private PeminjamanController(Connection connection) {
        this.connection = connection;
    }

    //get instance peminjaman controller
    public static PeminjamanController getInstance() throws SQLException {
        if (instance == null) {
            instance = new PeminjamanController(DatabaseHelper.getInstance().getConnection());
        }
        return instance;
    }

    public  List<ViewPeminjaman> search(String query) throws Exception{
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM viewpeminjaman WHERE id LIKE ? OR judul LIKE ? OR nama_anggota LIKE ? OR petugas_dibuat LIKE ? OR tanggal_pinjam LIKE ? OR petugas_diubah LIKE ? OR tanggal_kembali LIKE ? OR status LIKE ? OR keterangan LIKE ?");
        stmt.setString(1, "%" + query + "%");
        stmt.setString(2, "%" + query + "%");
        stmt.setString(3, "%" + query + "%");
        stmt.setString(4, "%" + query + "%");
        stmt.setString(5, "%" + query + "%");
        stmt.setString(6, "%" + query + "%");
        stmt.setString(7, "%" + query + "%");
        stmt.setString(8, "%" + query + "%");
        stmt.setString(9, "%" + query + "%");
        ResultSet rs = stmt.executeQuery();
        List<ViewPeminjaman> list = new ArrayList<>();

        while (rs.next()) {
            ViewPeminjaman peminjaman = new ViewPeminjaman(
                    rs.getString("id"),
                    rs.getString("nama_anggota"),
                    rs.getString("judul"),
                    rs.getString("petugas_dibuat"),
                    rs.getString("tanggal_pinjam"),
                    rs.getString("petugas_diubah"),
                    rs.getString("tanggal_kembali"),
                    rs.getString("status"),
                    rs.getString("keterangan")
            );
            list.add(peminjaman);
        }
        return list;
    }

    //insert ke tabel peminjaman
    public void create( Peminjaman peminjaman) throws SQLException {
                PreparedStatement stmt = connection.prepareStatement("INSERT INTO peminjaman ( nim_anggota, no_buku, nim_petugas_dibuat, tanggal_pinjam, status) VALUES ( ?, ?, ?, ?, ?)");
                stmt.setString(1, peminjaman.getNim_anggota());
                stmt.setString(2, peminjaman.getNo_buku());
                stmt.setString(3, peminjaman.getNim_petugas_dibuat());
                stmt.setString(4, peminjaman.getTanggal_pinjam());
                stmt.setString(5, "Dipinjam");
                stmt.executeUpdate();

    }

    //pengembalian buku
    public void pengembalian(PengembalianDto pengembalianDto) throws SQLException {
            PreparedStatement stmt = connection.prepareStatement("UPDATE peminjaman SET nim_petugas_diubah = ?, tanggal_kembali = ?, status = ?, keterangan=? WHERE id = ?");
            stmt.setString(1, SessionHelper.getInstance().getPetugas().getNim());
            stmt.setString(2, pengembalianDto.getTanggalPenembalian());
            stmt.setString(3, pengembalianDto.getStatus());
            stmt.setString(4, pengembalianDto.getKeterangan());
            stmt.setString(5, pengembalianDto.getId());
            stmt.executeUpdate();

    }
    
    // get satu baris data
    public Peminjaman get(int id) throws Exception{
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM peminjaman where id = ?");
        stmt.setInt(1, id);
        ResultSet rs = stmt.executeQuery();
 
        if(rs.next()){
            String idPeminjaman = rs.getString("id");
            String nimAnggota = rs.getString("nim_anggota");
            String noBuku = rs.getString("no_buku");
            String nimPetugasDibuat = rs.getString("nim_petugas_dibuat") != null ? rs.getString("nim_petugas_dibuat"): "";
            String nimPetugasDiubah = rs.getString("nim_petugas_diubah") != null ? rs.getString("nim_petugas_diubah") : "";
            String tanggalPeminjaman = rs.getString("tanggal_pinjam");
            String tanggalKembali = rs.getString("tanggal_kembali") != null ? rs.getString("tanggal_kembali") : "";
            String status = rs.getString("status");
            String keterangan = rs.getString("keterangan") != null ? rs.getString("keterangan") : "";

            Peminjaman peminjaman = new Peminjaman(idPeminjaman,nimAnggota,noBuku,nimPetugasDibuat,nimPetugasDiubah,tanggalPeminjaman,tanggalKembali,status,keterangan);
            return peminjaman;
        }else{
            throw new Exception("Kunjungan tidak ditemukan");
        }
        
    }
    
    //get view peminjaman
    public List<ViewPeminjaman> getView() throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM viewpeminjaman");
        ResultSet rs = stmt.executeQuery();
        List<ViewPeminjaman> viewPeminjamanList = new ArrayList<>();
        while (rs.next()) {
            ViewPeminjaman viewPeminjaman = new ViewPeminjaman(
                    rs.getString("id"),
                    rs.getString("nama_anggota"),
                    rs.getString("judul"),
                    rs.getString("petugas_dibuat"),
                    rs.getString("tanggal_pinjam"),
                    rs.getString("petugas_diubah"),
                    rs.getString("tanggal_kembali"),
                    rs.getString("status"),
                    rs.getString("keterangan")
            );
               viewPeminjamanList.add(viewPeminjaman);
        }
        return viewPeminjamanList;
    }

    //update tabel peminjaman
    public void update(String id, String nim_anggota, String no_buku, String tanggal_pinjam) throws SQLException {
        //validasi inputan kosong
        if (id.isEmpty() || nim_anggota.isEmpty() || no_buku.isEmpty() || tanggal_pinjam.isEmpty()) {
            System.out.println("Inputan tidak boleh kosong");
        } else {
            PreparedStatement stmt = connection.prepareStatement("UPDATE peminjaman SET nim_anggota = ?, no_buku = ?, tanggal_pinjam = ? WHERE id = ?");
            stmt.setString(1, nim_anggota);
            stmt.setString(2, no_buku);
            stmt.setString(3, tanggal_pinjam);
            stmt.setString(4, id);
            stmt.executeUpdate();
        }
    }

    //delete tabel peminjaman
    public void delete(String id) throws SQLException {
        PreparedStatement stmt = connection.prepareStatement("DELETE FROM peminjaman WHERE id = ?");
        stmt.setString(1, id);
        stmt.executeUpdate();
    }

}




