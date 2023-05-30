package ac.id.unikom.perpusif.Models;

public class ViewPeminjaman {

    String id;
    String nama_anggota;
    String judul;
    String nama_petugas_peminjaman;
    String tanggal_peminjaman;
    String nama_petugas_pengembalian;
    String tanggal_pengembalian;
    String status;
    String keterangan;

    public ViewPeminjaman(String id, String nama_anggota,String judul, String nama_petugas_peminjaman, String tanggal_peminjaman, String nama_petugas_pengembalian, String tanggal_pengembalian, String status, String keterangan) {
        this.id = id;
        this.nama_anggota = nama_anggota;
        this.judul = judul;
        this.nama_petugas_peminjaman = nama_petugas_peminjaman;
        this.tanggal_peminjaman = tanggal_peminjaman;
        this.nama_petugas_pengembalian = nama_petugas_pengembalian;
        this.tanggal_pengembalian = tanggal_pengembalian;
        this.status = status;
        this.keterangan = keterangan;
    }

    //getter and setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNama_anggota() {
        return nama_anggota;
    }
    public void setNama_anggota(String nama_anggota) {
        this.nama_anggota = nama_anggota;
    }
    public String getJudul() {
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }
    public String getNama_petugas_peminjaman() {
        return nama_petugas_peminjaman;
    }
    public void setNama_petugas_peminjaman(String nama_petugas_peminjaman) {
        this.nama_petugas_peminjaman = nama_petugas_peminjaman;
    }
    public String getTanggal_peminjaman() {
        return tanggal_peminjaman;
    }
    public void setTanggal_peminjaman(String tanggal_peminjaman) {
        this.tanggal_peminjaman = tanggal_peminjaman;
    }
    public String getNama_petugas_pengembalian() {
        return nama_petugas_pengembalian;
    }
    public void setNama_petugas_pengembalian(String nama_petugas_pengembalian) {
        this.nama_petugas_pengembalian = nama_petugas_pengembalian;
    }
    public String getTanggal_pengembalian() {
        return tanggal_pengembalian;
    }
    public void setTanggal_pengembalian(String tanggal_pengembalian) {
        this.tanggal_pengembalian = tanggal_pengembalian;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getKeterangan() {
        return keterangan;
    }
    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

}
