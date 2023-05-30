/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.id.unikom.perpusif.Models;

/**
 *
 * @author Bagas
 */
public class Buku {
    private String nomor;
    private String judul;
    private String pengarang;
    private String tahun_terbit;

    public Buku(String nomor, String judul, String pengarang, String tahun_terbit) {
        this.nomor = nomor;
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
    }

    public Buku(String judul, String pengarang, String tahun_terbit) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.tahun_terbit = tahun_terbit;
    }
    
    

    public String getNomor() {
        return nomor;
    }

    public void setNomor(String nomor) {
        this.nomor = nomor;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getTahun_terbit() {
        return tahun_terbit;
    }

    public void setTahun_terbit(String tahun_terbit) {
        this.tahun_terbit = tahun_terbit;
    }
    
    
}
