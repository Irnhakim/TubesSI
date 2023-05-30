/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ac.id.unikom.perpusif.Models;

/**
 *
 * @author Bagas
 */
public class Petugas {

    private String nim;
    private String nama;
    private String password;
    private Role role;

    public Petugas(String nim, String nama, String password, Role role) {
        this.nim = nim;
        this.nama = nama;
        this.password = password;
        this.role = role;
    }

    public Petugas(String nim, String nama, Role role) {
        this.nim = nim;
        this.nama = nama;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    
    
    
    
}
