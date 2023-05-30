package ac.id.unikom.perpusif.Models;

public class Kunjungan {
    private String id;
    private String nim_anggota;
    private String nim_petugas;
    private String tanggal_kunjungan;


    public Kunjungan(String id, String nim_anggota, String nim_petugas, String tanggal_kunjungan) {
        this.id = id;
        this.nim_anggota = nim_anggota;
        this.nim_petugas = nim_petugas;
        this.tanggal_kunjungan = tanggal_kunjungan;
    }

    public Kunjungan(String nimAnggota, String nimPetugas, String tanggalKunjungan) {
        this.nim_anggota = nimAnggota;
        this.nim_petugas = nimPetugas;
        this.tanggal_kunjungan = tanggalKunjungan;
    }


    //getter and setter
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getNim_anggota() {
        return nim_anggota;
    }
    public void setNim_anggota(String nim_anggota) {
        this.nim_anggota = nim_anggota;
    }
    public String getNim_petugas() {
        return nim_petugas;
    }
    public void setNim_petugas(String nim_petugas) {
        this.nim_petugas = nim_petugas;
    }
    public String getTgl_kunjungan() {
        return tanggal_kunjungan;
    }
    public void setTgl_kunjungan(String tanggal_kunjungan) {
        this.tanggal_kunjungan = tanggal_kunjungan;
    }
}
