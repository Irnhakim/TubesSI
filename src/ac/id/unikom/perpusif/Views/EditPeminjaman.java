/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ac.id.unikom.perpusif.Views;


import ac.id.unikom.perpusif.Controller.AnggotaController;
import ac.id.unikom.perpusif.Controller.BukuController;
import ac.id.unikom.perpusif.Controller.PeminjamanController;
import ac.id.unikom.perpusif.Models.Peminjaman;
import javax.swing.JOptionPane;

/**
 *
 * @author rifqi
 */
public class EditPeminjaman extends javax.swing.JFrame {

    private final Peminjaman peminjamanDipilih;
    /**
     * Creates new form TambahAnggota
     */
    public EditPeminjaman(Peminjaman peminjamanDipilih) {
        initComponents();
        this.peminjamanDipilih = peminjamanDipilih;
        updateComboBoxAnggota();
        updateComboBoxBuku();
        cbNimAnggota.setSelectedItem(peminjamanDipilih.getNim_anggota());
        cbNoBuku.setSelectedItem(peminjamanDipilih.getNo_buku());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtNamaAnggota = new javax.swing.JTextField();
        txtJudulBuku = new javax.swing.JTextField();
        txtTanggalPeminjaman = new javax.swing.JTextField();
        btnSimpan = new javax.swing.JButton();
        btnBatal = new javax.swing.JButton();
        cbNimAnggota = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        cbNoBuku = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();

        setResizable(false);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("EDIT DATA PEMINJAMAN");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel2.setText("NIM Anggota");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("No Buku");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setText("Tanggal Peminjaman");

        txtNamaAnggota.setEnabled(false);
        txtNamaAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNamaAnggotaActionPerformed(evt);
            }
        });

        txtJudulBuku.setEnabled(false);
        txtJudulBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtJudulBukuActionPerformed(evt);
            }
        });

        txtTanggalPeminjaman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTanggalPeminjamanActionPerformed(evt);
            }
        });

        btnSimpan.setBackground(new java.awt.Color(255, 204, 102));
        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnBatal.setBackground(new java.awt.Color(255, 102, 102));
        btnBatal.setText("Batal");
        btnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBatalActionPerformed(evt);
            }
        });

        cbNimAnggota.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== PILIH NIM ==" }));
        cbNimAnggota.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNimAnggotaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel5.setText("Nama Anggota");

        cbNoBuku.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "== PILIH NO BUKU ==" }));
        cbNoBuku.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbNoBukuActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setText("Judul Buku");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSimpan)
                        .addGap(18, 18, 18)
                        .addComponent(btnBatal))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTanggalPeminjaman)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                                .addGap(36, 36, 36)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNamaAnggota, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                                    .addComponent(cbNimAnggota, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cbNoBuku, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cbNimAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtNamaAnggota, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(cbNoBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(txtJudulBuku, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtTanggalPeminjaman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel4)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSimpan)
                    .addComponent(btnBatal))
                .addGap(47, 47, 47))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaAnggotaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaAnggotaActionPerformed

    private void txtJudulBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtJudulBukuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtJudulBukuActionPerformed

    private void txtTanggalPeminjamanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTanggalPeminjamanActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTanggalPeminjamanActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // konfirmasi simpan
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(this, "Apakah anda yakin ingin mengubah data ini?","Konfirmasi",dialogButton);
        
        if(dialogResult == JOptionPane.YES_OPTION){
            // simpan data
              String idPeminjaman = peminjamanDipilih.getId();
              String nimAnggota = cbNimAnggota.getSelectedItem().toString();
              String noBuku = cbNoBuku.getSelectedItem().toString();
              String tanggalPinjam = txtTanggalPeminjaman.getText();
            
            try{
                PeminjamanController.getInstance().update(idPeminjaman,nimAnggota,noBuku,tanggalPinjam);
                JOptionPane.showMessageDialog(this, "Data berhasil diubah");
                dispose();
            }catch(Exception e){
                JOptionPane.showMessageDialog(this, e.getMessage());
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBatalActionPerformed
        dispose();
    }//GEN-LAST:event_btnBatalActionPerformed

    private void cbNimAnggotaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNimAnggotaActionPerformed
        try {
            String nama = AnggotaController.getInstance().getNama(cbNimAnggota.getSelectedItem().toString());
            txtNamaAnggota.setText(nama);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cbNimAnggotaActionPerformed

    private void cbNoBukuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbNoBukuActionPerformed
         try {
            String judul = BukuController.getInstance().getJudul(cbNoBuku.getSelectedItem().toString());
            txtJudulBuku.setText(judul);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_cbNoBukuActionPerformed
    private void updateComboBoxAnggota() {
                try {
                    AnggotaController.getInstance().getAll().forEach(anggota -> {
                        cbNimAnggota.addItem(anggota.getNim());
                    });
                }catch (Exception e) {
                    e.printStackTrace();
            }
    }
    private void updateComboBoxBuku() {
                try {
                    BukuController.getInstance().getAll().forEach(buku -> {
                        cbNoBuku.addItem(buku.getNomor());
                    });
                }catch (Exception e) {
                    e.printStackTrace();
            }
    }
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBatal;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JComboBox<String> cbNimAnggota;
    private javax.swing.JComboBox<String> cbNoBuku;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JTextField txtJudulBuku;
    private javax.swing.JTextField txtNamaAnggota;
    private javax.swing.JTextField txtTanggalPeminjaman;
    // End of variables declaration//GEN-END:variables
}
