/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ac.id.unikom.perpusif.Views;

import ac.id.unikom.perpusif.Controller.AnggotaController;
import ac.id.unikom.perpusif.Controller.BukuController;
import ac.id.unikom.perpusif.Controller.KunjunganController;
import ac.id.unikom.perpusif.Controller.PeminjamanController;
import ac.id.unikom.perpusif.Helpers.DatabaseHelper;
import ac.id.unikom.perpusif.Models.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ryusaga
 */
public class DashboardPetugas extends javax.swing.JFrame {

    private Anggota anggotaDipilih;
    private Buku bukuDipilih;
    private Kunjungan kunjunganDipilih;
    private Peminjaman peminjamanDipilih;
    private String currentDir;


    public DashboardPetugas() {
        initComponents();
        mainPanel.removeAll();
        mainPanel.add(homePanel);
        mainPanel.repaint();
        mainPanel.revalidate();
        refreshTabelAnggota();
        refreshTabelBuku();
        refreshTabelKunjungan();
        refreshTabelPeminjaman();

        tabelAnggota.getSelectionModel().addListSelectionListener(e -> {
            if (tabelAnggota.getSelectedRow()==-1){
                btnEditAnggota.setEnabled(false);
                btnHapusAnggota.setEnabled(false);
            }else {
                btnEditAnggota.setEnabled(true);
                btnHapusAnggota.setEnabled(true);
            }
        });

        tabelAnggota.getSelectionModel().addListSelectionListener(e -> {
            
            int rowAnggota = tabelAnggota.getSelectedRow();
            if(rowAnggota != -1){

                btnEditAnggota.setEnabled(true);
                btnHapusAnggota.setEnabled(true);

                String nim = tabelAnggota.getValueAt(rowAnggota, 0).toString();
                String nama = tabelAnggota.getValueAt(rowAnggota, 1).toString();
                String noTelepon = tabelAnggota.getValueAt(rowAnggota, 2).toString();

                anggotaDipilih = new Anggota(nim, nama, noTelepon);
            }
        });

        tabelBuku.getSelectionModel().addListSelectionListener(e -> {
            if (tabelBuku.getSelectedRow()==-1){
                btnEditBuku.setEnabled(false);
                btnHapusBuku.setEnabled(false);
            }else {
                btnEditBuku.setEnabled(true);
                btnHapusBuku.setEnabled(true);
            }
        });

        tabelBuku.getSelectionModel().addListSelectionListener(e -> {
            int rowBuku = tabelBuku.getSelectedRow();
            if(rowBuku != -1){

                btnEditBuku.setEnabled(true);
                btnHapusBuku.setEnabled(true);

                String noBuku = tabelBuku.getValueAt(rowBuku, 0).toString();
                String judul = tabelBuku.getValueAt(rowBuku, 1).toString();
                String pengarang = tabelBuku.getValueAt(rowBuku, 2).toString();
                String tahunTerbit = tabelBuku.getValueAt(rowBuku, 3).toString();

                bukuDipilih = new Buku(noBuku, judul, pengarang, tahunTerbit);
            }
        });
        
        tabelKunjungan.getSelectionModel().addListSelectionListener(e -> {
            if (tabelKunjungan.getSelectedRow()==-1){
                btnEditKunjungan.setEnabled(false);
                btnHapusKunjungan.setEnabled(false);
            }else {
                btnEditKunjungan.setEnabled(true);
                btnHapusKunjungan.setEnabled(true);
            }
        });

        tabelKunjungan.getSelectionModel().addListSelectionListener(e -> {
            int rowKunjungan = tabelKunjungan.getSelectedRow();
            if(rowKunjungan != -1){

                btnEditKunjungan.setEnabled(true);
                btnHapusKunjungan.setEnabled(true);

                  String noKunjungan = tabelKunjungan.getValueAt(rowKunjungan, 0).toString();

                try{
                      Kunjungan dataKunjungan = KunjunganController.getInstance().get(Integer.valueOf(noKunjungan));
                      kunjunganDipilih = new Kunjungan(dataKunjungan.getId(),dataKunjungan.getNim_anggota(),dataKunjungan.getNim_petugas(),dataKunjungan.getTgl_kunjungan());
                }catch(Exception err){
                    JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
                }
                
            }
        });

        tabelPeminjaman.getSelectionModel().addListSelectionListener(e -> {
            int rowPeminjaman = tabelPeminjaman.getSelectedRow();
            if(rowPeminjaman != -1){
                btnEditPeminjaman.setEnabled(true);
                btnHapusPeminjaman.setEnabled(true);

                String noPeminjaman = tabelPeminjaman.getValueAt(rowPeminjaman, 0).toString();

                try{
                      Peminjaman dataPeminjaman = PeminjamanController.getInstance().get(Integer.valueOf(noPeminjaman));
                      peminjamanDipilih = new Peminjaman(dataPeminjaman.getId(),dataPeminjaman.getNim_anggota(),dataPeminjaman.getNo_buku(),dataPeminjaman.getNim_petugas_dibuat(),dataPeminjaman.getNim_petugas_diubah(),dataPeminjaman.getTanggal_pinjam(),dataPeminjaman.getTanggal_kembali(),dataPeminjaman.getStatus(),dataPeminjaman.getKeterangan());
                }catch(Exception err){
                    JOptionPane.showMessageDialog(this, "Data tidak ditemukan");
                }
            }
        });
    }

    public static Image getImage(final String pathAndFileName) {
        final URL url = Thread.currentThread().getContextClassLoader().getResource(pathAndFileName);
        return Toolkit.getDefaultToolkit().getImage(url);
    }

    public void refreshTabelAnggota(){
        try {
            List<Anggota> listAnggota = AnggotaController.getInstance().getAll();
            clearAnggota();
            updateAngota(listAnggota);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void refreshTabelBuku(){
        try {
            List<Buku> listBuku = BukuController.getInstance().getAll();
            clearBuku();
            updateBuku(listBuku);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        }

    public void  refreshTabelKunjungan() {
        try {
            List<ViewKunjungan> listViewKunjungan = KunjunganController.getInstance().getView();
            clearKunjungan();
            updateKunjungan(listViewKunjungan);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void refreshTabelPeminjaman(){
        try {
            List<ViewPeminjaman> listViewPeminjaman = PeminjamanController.getInstance().getView();
            clearPeminjaman();
            updatePeminjaman(listViewPeminjaman);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void clearAnggota(){
        DefaultTableModel model = (DefaultTableModel) tabelAnggota.getModel();
        model.setRowCount(0);
    }

    public void  clearBuku(){
        DefaultTableModel model = (DefaultTableModel) tabelBuku.getModel();
        model.setRowCount(0);
    }
    public void  clearKunjungan(){
        DefaultTableModel model = (DefaultTableModel) tabelKunjungan.getModel();
        model.setRowCount(0);
    }

    public void  clearPeminjaman(){
        DefaultTableModel model = (DefaultTableModel) tabelPeminjaman.getModel();
        model.setRowCount(0);
    }

    public void updateAngota(List<Anggota> listAnggota){
        DefaultTableModel model = (DefaultTableModel)  tabelAnggota.getModel();
        for (Anggota anggota : listAnggota){
            model.addRow(new Object[]{
                anggota.getNim(),
                anggota.getNama(),
                anggota.getNo_telepon()
            });
        }
    }

    public void updateBuku(List<Buku> listBuku){
        DefaultTableModel model = (DefaultTableModel) tabelBuku.getModel();
        for (Buku buku : listBuku){
            model.addRow(new Object[]{
                buku.getNomor(),
                buku.getJudul(),
                buku.getPengarang(),
                buku.getTahun_terbit()
            });
        }
    }

    public void updateKunjungan(List<ViewKunjungan> listKunjungan){
        DefaultTableModel model = (DefaultTableModel) tabelKunjungan.getModel();
        for (ViewKunjungan kunjungan : listKunjungan){
            model.addRow(new Object[]{
                kunjungan.getId(),
                kunjungan.getNama_anggota(),
                kunjungan.getNama_petugas(),
                kunjungan.getTanggal_kunjungan()
            });
        }
    }

    public void updatePeminjaman(List<ViewPeminjaman> listPeminjaman) {
        DefaultTableModel model = (DefaultTableModel) tabelPeminjaman.getModel();
        for (ViewPeminjaman peminjaman : listPeminjaman) {
            model.addRow(new Object[]{
                    peminjaman.getId(),
                    peminjaman.getNama_anggota(),
                    peminjaman.getJudul(),
                    peminjaman.getNama_petugas_peminjaman(),
                    peminjaman.getTanggal_peminjaman(),
                    peminjaman.getNama_petugas_pengembalian(),
                    peminjaman.getTanggal_pengembalian(),
                    peminjaman.getStatus(),
                    peminjaman.getKeterangan()
            });
        }
    }




    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        bodyPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        btnHome = new javax.swing.JButton();
        btnKunjungan = new javax.swing.JButton();
        btnAnggota = new javax.swing.JButton();
        btnPeminjaman = new javax.swing.JButton();
        btnBuku = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        mainPanel = new javax.swing.JPanel();
        homePanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        anggotaPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelAnggota = new javax.swing.JTable();
        btnRefreshAnggota = new javax.swing.JButton();
        btnEditAnggota = new javax.swing.JButton();
        btnHapusAnggota = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        btnTambahAnggota = new javax.swing.JButton();
        txtCariAnggota = new javax.swing.JTextField();
        btnCariAnggota = new javax.swing.JToggleButton();
        btnCetakAnggota = new javax.swing.JButton();
        bukuPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelBuku = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnRefreshBuku = new javax.swing.JButton();
        btnEditBuku = new javax.swing.JButton();
        btnHapusBuku = new javax.swing.JButton();
        btnTambahBuku = new javax.swing.JButton();
        txtCariBuku = new javax.swing.JTextField();
        btnCariBuku = new javax.swing.JToggleButton();
        kunjunganPanel = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelKunjungan = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        btnRefreshKunjungan = new javax.swing.JButton();
        btnEditKunjungan = new javax.swing.JButton();
        btnHapusKunjungan = new javax.swing.JButton();
        btnTambahKunjungan = new javax.swing.JButton();
        txtCariKunjungan = new javax.swing.JTextField();
        btnCariKunjungan = new javax.swing.JToggleButton();
        peminjamanPanel = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabelPeminjaman = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnRefreshPeminjaman = new javax.swing.JButton();
        btnEditPeminjaman = new javax.swing.JButton();
        btnHapusPeminjaman = new javax.swing.JButton();
        btnTambahPeminjaman = new javax.swing.JButton();
        btnPengembalian = new javax.swing.JToggleButton();
        txtCariPeminjaman = new javax.swing.JTextField();
        btnCariPeminjaman = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menu Petugas");
        setResizable(false);

        bodyPanel.setBackground(new java.awt.Color(231, 246, 242));

        menuPanel.setBackground(new java.awt.Color(165, 201, 202));
        menuPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(242, 242, 242), new java.awt.Color(242, 242, 242), new java.awt.Color(242, 242, 242), new java.awt.Color(242, 242, 242)));

        btnHome.setText("Home");
        btnHome.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHomeActionPerformed(evt);
            }
        });

        btnKunjungan.setText("Kunjungan");
        btnKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKunjunganActionPerformed(evt);
            }
        });

        btnAnggota.setText("Anggota");
        btnAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnggotaActionPerformed(evt);
            }
        });

        btnPeminjaman.setText("Peminjaman");
        btnPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPeminjamanActionPerformed(evt);
            }
        });

        btnBuku.setText("Buku");
        btnBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBukuActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout menuPanelLayout = new javax.swing.GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnHome, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnKunjungan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPeminjaman, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                    .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnBuku, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAnggota, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        menuPanelLayout.setVerticalGroup(
            menuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(menuPanelLayout.createSequentialGroup()
                .addGap(185, 185, 185)
                .addComponent(btnHome, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        mainPanel.setBackground(new java.awt.Color(39, 174, 96));
        mainPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED, new java.awt.Color(242, 242, 242), new java.awt.Color(242, 242, 242), new java.awt.Color(242, 242, 242), new java.awt.Color(242, 242, 242)));
        mainPanel.setLayout(new java.awt.CardLayout());

        homePanel.setBackground(new java.awt.Color(165, 201, 202));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/assets/logoUnikom.png"))); // NOI18N
        jLabel6.setText("jLabel6");

        javax.swing.GroupLayout homePanelLayout = new javax.swing.GroupLayout(homePanel);
        homePanel.setLayout(homePanelLayout);
        homePanelLayout.setHorizontalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(653, 653, 653)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(863, Short.MAX_VALUE))
        );
        homePanelLayout.setVerticalGroup(
            homePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(homePanelLayout.createSequentialGroup()
                .addGap(251, 251, 251)
                .addComponent(jLabel6)
                .addContainerGap(513, Short.MAX_VALUE))
        );

        mainPanel.add(homePanel, "card2");

        tabelAnggota.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "NIM", "Nama", "No Telepon"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tabelAnggota);

        btnRefreshAnggota.setText("Refresh");
        btnRefreshAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshAnggotaActionPerformed(evt);
            }
        });

        btnEditAnggota.setText("Edit");
        btnEditAnggota.setEnabled(false);
        btnEditAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditAnggotaActionPerformed(evt);
            }
        });

        btnHapusAnggota.setText("Hapus");
        btnHapusAnggota.setEnabled(false);
        btnHapusAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusAnggotaActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel4.setText("Data Anggota");

        btnTambahAnggota.setText("Tambah");
        btnTambahAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahAnggotaActionPerformed(evt);
            }
        });

        txtCariAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariAnggotaActionPerformed(evt);
            }
        });

        btnCariAnggota.setText("Cari");
        btnCariAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariAnggotaActionPerformed(evt);
            }
        });

        btnCetakAnggota.setText("Cetak Laporan");
        btnCetakAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakAnggotaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout anggotaPanelLayout = new javax.swing.GroupLayout(anggotaPanel);
        anggotaPanel.setLayout(anggotaPanelLayout);
        anggotaPanelLayout.setHorizontalGroup(
            anggotaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, anggotaPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(anggotaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(anggotaPanelLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1558, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(anggotaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, anggotaPanelLayout.createSequentialGroup()
                            .addComponent(btnTambahAnggota)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnEditAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnHapusAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(btnRefreshAnggota)
                            .addGap(793, 793, 793)
                            .addComponent(btnCetakAnggota)
                            .addGap(18, 18, 18)
                            .addComponent(btnCariAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(26, 26, 26)
                            .addComponent(txtCariAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15))
        );
        anggotaPanelLayout.setVerticalGroup(
            anggotaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(anggotaPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(anggotaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(anggotaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCariAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCariAnggota)
                        .addComponent(btnCetakAnggota))
                    .addGroup(anggotaPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnRefreshAnggota)
                        .addComponent(btnTambahAnggota)
                        .addComponent(btnEditAnggota)
                        .addComponent(btnHapusAnggota)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        mainPanel.add(anggotaPanel, "card3");

        tabelBuku.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nomor", "Judul", "Pengarang", "Tahun Terbit"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tabelBuku);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Data Buku");

        btnRefreshBuku.setText("Refresh");
        btnRefreshBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshBukuActionPerformed(evt);
            }
        });

        btnEditBuku.setText("Edit");
        btnEditBuku.setEnabled(false);
        btnEditBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditBukuActionPerformed(evt);
            }
        });

        btnHapusBuku.setText("Hapus");
        btnHapusBuku.setEnabled(false);
        btnHapusBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusBukuActionPerformed(evt);
            }
        });

        btnTambahBuku.setText("Tambah");
        btnTambahBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahBukuActionPerformed(evt);
            }
        });

        txtCariBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariBukuActionPerformed(evt);
            }
        });

        btnCariBuku.setText("Cari");
        btnCariBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariBukuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bukuPanelLayout = new javax.swing.GroupLayout(bukuPanel);
        bukuPanel.setLayout(bukuPanelLayout);
        bukuPanelLayout.setHorizontalGroup(
            bukuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bukuPanelLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(bukuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bukuPanelLayout.createSequentialGroup()
                        .addComponent(btnTambahBuku)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditBuku)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapusBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRefreshBuku)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCariBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(txtCariBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 233, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1559, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        bukuPanelLayout.setVerticalGroup(
            bukuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bukuPanelLayout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(bukuPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCariBuku)
                    .addComponent(btnRefreshBuku)
                    .addComponent(btnTambahBuku)
                    .addComponent(btnEditBuku)
                    .addComponent(btnHapusBuku))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 769, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(bukuPanel, "card4");

        tabelKunjungan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        tabelKunjungan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Kunjungan", "Nama Anggota", "Nama Petugas", "Tanggal Kunjungan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tabelKunjungan);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Data Kunjungan");

        btnRefreshKunjungan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRefreshKunjungan.setText("Refresh");
        btnRefreshKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshKunjunganActionPerformed(evt);
            }
        });

        btnEditKunjungan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditKunjungan.setText("Edit");
        btnEditKunjungan.setEnabled(false);
        btnEditKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditKunjunganActionPerformed(evt);
            }
        });

        btnHapusKunjungan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnHapusKunjungan.setText("Hapus");
        btnHapusKunjungan.setEnabled(false);
        btnHapusKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusKunjunganActionPerformed(evt);
            }
        });

        btnTambahKunjungan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnTambahKunjungan.setText("Tambah");
        btnTambahKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahKunjunganActionPerformed(evt);
            }
        });

        txtCariKunjungan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txtCariKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariKunjunganActionPerformed(evt);
            }
        });

        btnCariKunjungan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCariKunjungan.setText("Cari");
        btnCariKunjungan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariKunjunganActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout kunjunganPanelLayout = new javax.swing.GroupLayout(kunjunganPanel);
        kunjunganPanel.setLayout(kunjunganPanelLayout);
        kunjunganPanelLayout.setHorizontalGroup(
            kunjunganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kunjunganPanelLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(kunjunganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kunjunganPanelLayout.createSequentialGroup()
                        .addComponent(btnTambahKunjungan)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnHapusKunjungan)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefreshKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCariKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(txtCariKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1555, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        kunjunganPanelLayout.setVerticalGroup(
            kunjunganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(kunjunganPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(kunjunganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(kunjunganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnCariKunjungan)
                        .addComponent(txtCariKunjungan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(kunjunganPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambahKunjungan)
                        .addComponent(btnEditKunjungan)
                        .addComponent(btnHapusKunjungan)
                        .addComponent(btnRefreshKunjungan)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 770, Short.MAX_VALUE)
                .addContainerGap())
        );

        mainPanel.add(kunjunganPanel, "card5");

        tabelPeminjaman.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "No Peminjaman", "Nama Anggota", "Judul", "Petugas Peminjaman", "Tanggal Peminjaman", "Petugas Pengembalian", "Tanggal Pengembalian", "Status", "Keterangan"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tabelPeminjaman);
        if (tabelPeminjaman.getColumnModel().getColumnCount() > 0) {
            tabelPeminjaman.getColumnModel().getColumn(8).setMinWidth(300);
        }

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Data Peminjaman");

        btnRefreshPeminjaman.setText("Refresh");
        btnRefreshPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshPeminjamanActionPerformed(evt);
            }
        });

        btnEditPeminjaman.setText("Edit");
        btnEditPeminjaman.setEnabled(false);
        btnEditPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditPeminjamanActionPerformed(evt);
            }
        });

        btnHapusPeminjaman.setText("Hapus");
        btnHapusPeminjaman.setEnabled(false);
        btnHapusPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusPeminjamanActionPerformed(evt);
            }
        });

        btnTambahPeminjaman.setText("Tambah");
        btnTambahPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahPeminjamanActionPerformed(evt);
            }
        });

        btnPengembalian.setText("Pemngembalian Buku");
        btnPengembalian.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPengembalianActionPerformed(evt);
            }
        });

        txtCariPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCariPeminjamanActionPerformed(evt);
            }
        });

        btnCariPeminjaman.setText("Cari");
        btnCariPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPeminjamanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout peminjamanPanelLayout = new javax.swing.GroupLayout(peminjamanPanel);
        peminjamanPanel.setLayout(peminjamanPanelLayout);
        peminjamanPanelLayout.setHorizontalGroup(
            peminjamanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, peminjamanPanelLayout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addGroup(peminjamanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(peminjamanPanelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(1469, 1469, 1469))
                    .addGroup(peminjamanPanelLayout.createSequentialGroup()
                        .addComponent(btnTambahPeminjaman)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditPeminjaman)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHapusPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnRefreshPeminjaman)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnPengembalian)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCariPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(279, 279, 279))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, peminjamanPanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(peminjamanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 1559, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCariPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18))
        );
        peminjamanPanelLayout.setVerticalGroup(
            peminjamanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(peminjamanPanelLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(peminjamanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(peminjamanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCariPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnCariPeminjaman))
                    .addGroup(peminjamanPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnTambahPeminjaman)
                        .addComponent(btnRefreshPeminjaman)
                        .addComponent(btnEditPeminjaman)
                        .addComponent(btnHapusPeminjaman)
                        .addComponent(btnPengembalian)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 776, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        mainPanel.add(peminjamanPanel, "card6");

        javax.swing.GroupLayout bodyPanelLayout = new javax.swing.GroupLayout(bodyPanel);
        bodyPanel.setLayout(bodyPanelLayout);
        bodyPanelLayout.setHorizontalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        bodyPanelLayout.setVerticalGroup(
            bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bodyPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(bodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(menuPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 848, Short.MAX_VALUE))
                .addContainerGap())
        );

        jDesktopPane1.setLayer(bodyPanel, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bodyPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        //klik logout kembali ke Login
        Login login = new Login();
        login.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnLogoutActionPerformed

    private void btnHomeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHomeActionPerformed
        //klik home menuju home panel
        mainPanel.removeAll();
        mainPanel.add(homePanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnHomeActionPerformed

    private void btnAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnggotaActionPerformed
        //klik anggota menuju anggota panel
        mainPanel.removeAll();
        mainPanel.add(anggotaPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnAnggotaActionPerformed

    private void btnBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBukuActionPerformed
        //klik buku menuju buku panel
        mainPanel.removeAll();
        mainPanel.add(bukuPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnBukuActionPerformed

    private void btnKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKunjunganActionPerformed
        //klik kunjungan menuju kunjungan panel
        mainPanel.removeAll();
        mainPanel.add(kunjunganPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnKunjunganActionPerformed

    private void btnPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPeminjamanActionPerformed
        //klik peminjaman menuju peminjaman panel
        mainPanel.removeAll();
        mainPanel.add(peminjamanPanel);
        mainPanel.repaint();
        mainPanel.revalidate();
    }//GEN-LAST:event_btnPeminjamanActionPerformed

    private void btnRefreshAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshAnggotaActionPerformed
        refreshTabelAnggota();
        txtCariAnggota.setText("");
    }//GEN-LAST:event_btnRefreshAnggotaActionPerformed

    private void btnEditAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditAnggotaActionPerformed
        //klik edit anggota menuju edit anggota panel
        if(anggotaDipilih == null){
            return;
        }

        new EditAnggota(anggotaDipilih).setVisible(true);
        
    }//GEN-LAST:event_btnEditAnggotaActionPerformed

    private void btnHapusAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusAnggotaActionPerformed
        //klik hapus anggota menuju hapus anggota panel
        if(anggotaDipilih == null){
            return;
        }

        int pilihan = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data anggota ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if(pilihan == JOptionPane.YES_OPTION){
            try {
                AnggotaController.getInstance().delete(anggotaDipilih.getNim());
                JOptionPane.showMessageDialog(this, "Data anggota berhasil dihapus");
                refreshTabelAnggota();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Data anggota gagal dihapus");
            }
        }
    }//GEN-LAST:event_btnHapusAnggotaActionPerformed

    private void btnTambahAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahAnggotaActionPerformed
        //klik tambah anggota menuju tambah anggota panel
        new TambahAnggota().setVisible(true);
    }//GEN-LAST:event_btnTambahAnggotaActionPerformed

    private void btnTambahBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahBukuActionPerformed
        //klik tambah buku menuju tambah buku panel
        new TambahBuku().setVisible(true);
    }//GEN-LAST:event_btnTambahBukuActionPerformed

    private void btnTambahPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahPeminjamanActionPerformed
       //klik tambah peminjaman menuju tambah peminjaman panel
        new TambahPeminjaman().setVisible(true);
    }//GEN-LAST:event_btnTambahPeminjamanActionPerformed

    private void btnTambahKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahKunjunganActionPerformed
        //klik tambah kunjungan menuju tambah kunjungan panel
        new TambahKunjungan().setVisible(true);
    }//GEN-LAST:event_btnTambahKunjunganActionPerformed

    private void btnEditBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditBukuActionPerformed
        if(bukuDipilih == null){
            return;
        }

        new EditBuku(bukuDipilih).setVisible(true);
    }//GEN-LAST:event_btnEditBukuActionPerformed

    private void btnRefreshBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshBukuActionPerformed
        refreshTabelBuku();
        txtCariBuku.setText("");
    }//GEN-LAST:event_btnRefreshBukuActionPerformed

    private void btnEditKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditKunjunganActionPerformed
        if(kunjunganDipilih == null){
            return;
        }

        new EditKunjungan(kunjunganDipilih).setVisible(true);
    }//GEN-LAST:event_btnEditKunjunganActionPerformed

    private void btnRefreshKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshKunjunganActionPerformed
        refreshTabelKunjungan();
        txtCariKunjungan.setText("");
    }//GEN-LAST:event_btnRefreshKunjunganActionPerformed

    private void btnRefreshPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshPeminjamanActionPerformed
        refreshTabelPeminjaman();
        txtCariPeminjaman.setText("");
    }//GEN-LAST:event_btnRefreshPeminjamanActionPerformed

    private void btnPengembalianActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPengembalianActionPerformed
        if(peminjamanDipilih == null){
            return;
        }

        new PengembalianBuku(peminjamanDipilih).setVisible(true);
    }//GEN-LAST:event_btnPengembalianActionPerformed

    private void btnHapusPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusPeminjamanActionPerformed
        if (peminjamanDipilih == null) {
            return;
        }

        int pilihan = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data peminjaman ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            try {
                PeminjamanController.getInstance().delete(peminjamanDipilih.getId());
                JOptionPane.showMessageDialog(this, "Data peminjaman berhasil dihapus");
                refreshTabelPeminjaman();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Data peminjaman gagal dihapus");
            }
        }
    }//GEN-LAST:event_btnHapusPeminjamanActionPerformed

    private void btnHapusKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusKunjunganActionPerformed
        if(kunjunganDipilih == null){
            return;
        }

        int pilihan = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data peminjaman ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            try {
                KunjunganController.getInstance().delete(kunjunganDipilih.getId());
                JOptionPane.showMessageDialog(this, "Data kunjungan berhasil dihapus");
                refreshTabelKunjungan();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Data kunjungan gagal dihapus");
            }
        }

    }//GEN-LAST:event_btnHapusKunjunganActionPerformed

    private void btnHapusBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusBukuActionPerformed
        if(bukuDipilih == null){
            return;
        }

        int pilihan = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin menghapus data peminjaman ini?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            try {
                BukuController.getInstance().delete(bukuDipilih.getNomor());
                JOptionPane.showMessageDialog(this, "Data buku berhasil dihapus");
                refreshTabelBuku();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Data buku gagal dihapus");
            }
        }
    }//GEN-LAST:event_btnHapusBukuActionPerformed

    private void txtCariAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariAnggotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariAnggotaActionPerformed

    private void btnCariAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariAnggotaActionPerformed
        try{
            List<Anggota> listAnggota = AnggotaController.getInstance().search(txtCariAnggota.getText());
            clearAnggota();
            updateAngota(listAnggota);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
        
    }//GEN-LAST:event_btnCariAnggotaActionPerformed

    private void txtCariBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariBukuActionPerformed

    private void btnCariBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariBukuActionPerformed
        try{
            List<Buku> listBuku = BukuController.getInstance().search(txtCariBuku.getText());
            clearBuku();
            updateBuku(listBuku);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e);
        }

    }//GEN-LAST:event_btnCariBukuActionPerformed

    private void txtCariKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariKunjunganActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariKunjunganActionPerformed

    private void btnCariKunjunganActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariKunjunganActionPerformed
        try{
            List<ViewKunjungan> listViewKunjungan = KunjunganController.getInstance().search(txtCariKunjungan.getText());
            clearKunjungan();
            updateKunjungan(listViewKunjungan);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnCariKunjunganActionPerformed

    private void txtCariPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCariPeminjamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCariPeminjamanActionPerformed

    private void btnCariPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPeminjamanActionPerformed
        try{
            List<ViewPeminjaman> listViewPeminjaman = PeminjamanController.getInstance().search(txtCariPeminjaman.getText());
            clearPeminjaman();
            updatePeminjaman(listViewPeminjaman);
        }catch (Exception e){
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_btnCariPeminjamanActionPerformed

    private void btnEditPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditPeminjamanActionPerformed
        if(peminjamanDipilih == null){
            return;
        }

        new EditPeminjaman(peminjamanDipilih).setVisible(true);
    }//GEN-LAST:event_btnEditPeminjamanActionPerformed

    private void btnCetakAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakAnggotaActionPerformed
        //cetak reportAnggota
        try{
            //File file = new File("src/reportAnggota.jasper"); 

            InputStream input = getClass().getResourceAsStream("");
            System.out.println(input);
            File file = File.createTempFile("dist/laporan", ".pdf");
            JasperReport jr = JasperCompileManager.compileReport(input);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, DatabaseHelper.getInstance().getConnection());
            
            JasperViewer.viewReport(jp,false);
            JasperViewer.setDefaultLookAndFeelDecorated(true);
            JasperExportManager.exportReportToPdfFile(jp, file.getAbsolutePath());
            System.out.println(file.getAbsolutePath());
        }catch(JRException | SQLException ex) {
            Logger.getLogger(DashboardPetugas.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DashboardPetugas.class.getName()).log(Level.SEVERE, null, ex);
        }
//        URL res = getClass().getClassLoader().getResource("report/reportAnggota.jasper");
//        try{
//            File file = Paths.get(res.toURI()).toFile();
//            String absolutePath = file.getAbsolutePath();
//            JasperReport jr = (JasperReport) JRLoader.loadObject(file);
//            JasperPrint jp = JasperFillManager.fillReport(jr, null, DatabaseHelper.getInstance().getConnection());
//            JasperViewer.viewReport(jp, false);
//        }catch (Exception e){
//            Logger.getLogger(DashboardPetugas.class.getName()).log(Level.SEVERE, null, e);
//        }
        
       
        
//        URL res = getClass().getClassLoader().getResource("report/reportAnggota.jasper");
//        try{
//            File file = Paths.get(res.toURI()).toFile();
//            String absolutePath = file.getAbsolutePath();
//            JasperReport jr = (JasperReport) JRLoader.loadObject(file);
//            JasperPrint jp = JasperFillManager.fillReport(jr, null, DatabaseHelper.getInstance().getConnection());
//            JasperViewer.viewReport(jp, false);
//        }catch (Exception e){
//            Logger.getLogger(DashboardPetugas.class.getName()).log(Level.SEVERE, null, e);
//        }



    }//GEN-LAST:event_btnCetakAnggotaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DashboardPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DashboardPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DashboardPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DashboardPetugas.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DashboardPetugas().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel anggotaPanel;
    private javax.swing.JPanel bodyPanel;
    private javax.swing.JButton btnAnggota;
    private javax.swing.JButton btnBuku;
    private javax.swing.JToggleButton btnCariAnggota;
    private javax.swing.JToggleButton btnCariBuku;
    private javax.swing.JToggleButton btnCariKunjungan;
    private javax.swing.JToggleButton btnCariPeminjaman;
    private javax.swing.JButton btnCetakAnggota;
    private javax.swing.JButton btnEditAnggota;
    private javax.swing.JButton btnEditBuku;
    private javax.swing.JButton btnEditKunjungan;
    private javax.swing.JButton btnEditPeminjaman;
    private javax.swing.JButton btnHapusAnggota;
    private javax.swing.JButton btnHapusBuku;
    private javax.swing.JButton btnHapusKunjungan;
    private javax.swing.JButton btnHapusPeminjaman;
    private javax.swing.JButton btnHome;
    private javax.swing.JButton btnKunjungan;
    private javax.swing.JButton btnLogout;
    private javax.swing.JButton btnPeminjaman;
    private javax.swing.JToggleButton btnPengembalian;
    private javax.swing.JButton btnRefreshAnggota;
    private javax.swing.JButton btnRefreshBuku;
    private javax.swing.JButton btnRefreshKunjungan;
    private javax.swing.JButton btnRefreshPeminjaman;
    private javax.swing.JButton btnTambahAnggota;
    private javax.swing.JButton btnTambahBuku;
    private javax.swing.JButton btnTambahKunjungan;
    private javax.swing.JButton btnTambahPeminjaman;
    private javax.swing.JPanel bukuPanel;
    private javax.swing.JPanel homePanel;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel kunjunganPanel;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel peminjamanPanel;
    private javax.swing.JTable tabelAnggota;
    private javax.swing.JTable tabelBuku;
    private javax.swing.JTable tabelKunjungan;
    private javax.swing.JTable tabelPeminjaman;
    private javax.swing.JTextField txtCariAnggota;
    private javax.swing.JTextField txtCariBuku;
    private javax.swing.JTextField txtCariKunjungan;
    private javax.swing.JTextField txtCariPeminjaman;
    // End of variables declaration//GEN-END:variables
}
