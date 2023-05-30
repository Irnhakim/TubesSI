package ac.id.unikom.perpusif.Models;

public class Peminjaman {
    //Atribut
    private String id;
    private String nim_anggota;
    private String no_buku;
    private String nim_petugas_dibuat;
    private String nim_petugas_diubah;
    private String tanggal_pinjam;
    private String tanggal_kembali;
    private String status;
    private String keterangan;

    //Konstruktor
    public Peminjaman(String id, String nim_anggota, String no_buku, String nim_petugas_dibuat, String nim_petugas_diubah, String tanggal_pinjam, String tanggal_kembali, String status, String keterangan) {
        this.id = id;
        this.nim_anggota = nim_anggota;
        this.no_buku = no_buku;
        this.nim_petugas_dibuat = nim_petugas_dibuat;
        this.nim_petugas_diubah = nim_petugas_diubah;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
        this.status = status;
        this.keterangan = keterangan;
    }

    public Peminjaman(String nim_anggota, String no_buku, String nim_petugas_dibuat, String tanggal_pinjam){
        this.nim_anggota = nim_anggota;
        this.no_buku = no_buku;
        this.nim_petugas_dibuat = nim_petugas_dibuat;
        this.tanggal_pinjam = tanggal_pinjam;
    }
    //buat pengembalian buku


    public Peminjaman(String noPeminjaman, String namaAnggota, String namaBuku, String tanggalPinjam, String tanggalKembali) {
        this.id = noPeminjaman;
        this.nim_anggota = namaAnggota;
        this.no_buku = namaBuku;
        this.tanggal_pinjam = tanggalPinjam;
        this.tanggal_kembali = tanggalKembali;
    }

    public Peminjaman(String nomor, String tanggalPengembalian, String status) {
        this.id = nomor;
        this.tanggal_kembali = tanggalPengembalian;
        this.status = status;
    }


    //Getter and Setter
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
    public String getNo_buku() {
        return no_buku;
    }
    public void setNo_buku(String no_buku) {
        this.no_buku = no_buku;
    }
    public String getNim_petugas_dibuat() {
        return nim_petugas_dibuat;
    }
    public void setNim_petugas_dibuat(String nim_petugas_dibuat) {
        this.nim_petugas_dibuat = nim_petugas_dibuat;
    }
    public String getNim_petugas_diubah() {
        return nim_petugas_diubah;
    }
    public void setNim_petugas_diubah(String nim_petugas_diubah) {
        this.nim_petugas_diubah = nim_petugas_diubah;
    }
    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }
    public void setTanggal_pinjam(String tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }
    public String getTanggal_kembali() {
        return tanggal_kembali;
    }
    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
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

