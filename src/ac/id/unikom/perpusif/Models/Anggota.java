/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.id.unikom.perpusif.Models;

/**
 *
 * @author Bagas
 */
public class Anggota {
    private String nim;
    private String nama;
    private String noTelepon;

    public Anggota(String nim, String nama, String no_telepon) {
        this.nim = nim;
        this.nama = nama;
        this.noTelepon = no_telepon;
    }

    public Anggota(String nama, String no_telepon) {
        this.nama = nama;
        this.noTelepon = no_telepon;
    }



    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNo_telepon() {
        return noTelepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.noTelepon = no_telepon;
    }
    
    
}
