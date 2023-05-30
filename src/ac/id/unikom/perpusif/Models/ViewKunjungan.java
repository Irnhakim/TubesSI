package ac.id.unikom.perpusif.Models;

public class ViewKunjungan {

    String id;
    String nama_anggota;
    String nama_petugas;
    String tanggal_kunjungan;

    public ViewKunjungan(String id, String nama_anggota, String nama_petugas, String tanggal_kunjungan) {
        this.id = id;
        this.nama_anggota = nama_anggota;
        this.nama_petugas = nama_petugas;
        this.tanggal_kunjungan = tanggal_kunjungan;
    }

    public String getId() {
        return id;
    }
    //setter
    public void setId(String id) {
        this.id = id;
    }
    public String getNama_anggota() {
        return nama_anggota;
    }
    public void setNama_anggota(String nama_anggota) {
        this.nama_anggota = nama_anggota;
    }
    public String getNama_petugas() {
        return nama_petugas;
    }
    public void setNama_petugas(String nama_petugas) {
        this.nama_petugas = nama_petugas;
    }
    public String getTanggal_kunjungan() {
        return tanggal_kunjungan;
    }
    public void setTanggal_kunjungan(String tanggal_kunjungan) {
        this.tanggal_kunjungan = tanggal_kunjungan;
    }

}
