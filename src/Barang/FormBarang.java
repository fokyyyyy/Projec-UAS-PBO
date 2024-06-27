package Barang;

import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormBarang extends JFrame {
    private String[] judul = {"Kode Barang", "Nama Barang", "Harga Barang", "Stok Barang"};
    DefaultTableModel df;
    JTable tab = new JTable();
    JScrollPane scp = new JScrollPane();
    JPanel pnl = new JPanel();
    JLabel lblKodeBarang = new JLabel("Kode Barang");
    JTextField txKodeBarang = new JTextField(10);
    JLabel lblNamaBarang = new JLabel("Nama Barang");
    JTextField txNamaBarang = new JTextField(20);
    JLabel lblHargaBarang = new JLabel("Harga Barang");
    JTextField txHargaBarang = new JTextField(10);
    JLabel lblStokBarang = new JLabel("Stok Barang");
    JTextField txStokBarang = new JTextField(10);
    JButton btAdd = new JButton("Simpan");
    JButton btNew = new JButton("Baru");
    JButton btDel = new JButton("Hapus");
    JButton btEdit = new JButton("Ubah");

    FormBarang() {
        super("Data Barang");
        setSize(480, 350);
        pnl.setLayout(null);
        pnl.add(lblKodeBarang);
        lblKodeBarang.setBounds(20, 10, 80, 20);
        pnl.add(txKodeBarang);
        txKodeBarang.setBounds(105, 10, 100, 20);
        pnl.add(lblNamaBarang);
        lblNamaBarang.setBounds(20, 33, 80, 20);
        pnl.add(txNamaBarang);
        txNamaBarang.setBounds(105, 33, 175, 20);
        pnl.add(lblHargaBarang);
        lblHargaBarang.setBounds(20, 56, 80, 20);
        pnl.add(txHargaBarang);
        txHargaBarang.setBounds(105, 56, 175, 20);
        pnl.add(lblStokBarang);
        lblStokBarang.setBounds(20, 79, 80, 20);
        pnl.add(txStokBarang);
        txStokBarang.setBounds(105, 79, 175, 20);

        pnl.add(btNew);
        btNew.setBounds(300, 10, 125, 20);
        btNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btNewAksi(e);
            }
        });

        pnl.add(btAdd);
        btAdd.setBounds(300, 33, 125, 20);
        btAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btAddAksi(e);
            }
        });

        pnl.add(btEdit);
        btEdit.setBounds(300, 56, 125, 20);
        btEdit.setEnabled(false);
        btEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btEditAksi(e);
            }
        });

        pnl.add(btDel);
        btDel.setBounds(300, 79, 125, 20);
        btDel.setEnabled(false);
        btDel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btDelAksi(e);
            }
        });

        df = new DefaultTableModel(null, judul);
        tab.setModel(df);
        scp.getViewport().add(tab);
        tab.setEnabled(true);
        tab.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                tabMouseClicked(evt);
            }
        });
        scp.setBounds(20, 110, 405, 180);
        pnl.add(scp);
        getContentPane().add(pnl);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    void loadData() {
        try {
            Connection cn = new connecDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "SELECT * FROM tbl_barang";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                String kodeBarang, namaBarang;
                int hargaBarang, stokBarang;
                kodeBarang = rs.getString("kode_barang");
                namaBarang = rs.getString("nama_barang");
                hargaBarang = rs.getInt("harga_barang");
                stokBarang = rs.getInt("stok_barang");
                String[] data = {kodeBarang, namaBarang, String.valueOf(hargaBarang), String.valueOf(stokBarang)};
                df.addRow(data);
            }
            rs.close();
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void clearTable() {
        int numRow = df.getRowCount();
        for (int i = 0; i < numRow; i++) {
            df.removeRow(0);
        }
    }

    void clearTextField() {
        txKodeBarang.setText(null);
        txNamaBarang.setText(null);
        txHargaBarang.setText(null);
        txStokBarang.setText(null);
    }

    void simpanData(Barang B) {
        Connection cn = null;
        Statement st = null;
        try {
            cn = new connecDB().getConnect();
            st = cn.createStatement();
            String sql = "INSERT INTO tbl_barang (kode_barang, nama_barang, harga_barang, stok_barang) " +
                    "VALUES ('" + B.getKodebarang() + "', '" + B.getNamaBarang() + "', " + B.getHargaBarang() + ", " + B.getStokbarang() + ")";
            System.out.println("Executing SQL: " + sql);  // Output debug
            st.executeUpdate(sql);  // Pastikan untuk mengeksekusi query
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan",
                    "Info Proses", JOptionPane.INFORMATION_MESSAGE);

            // Ambil data kembali untuk memastikan data tersimpan
            String sqlSelect = "SELECT * FROM tbl_barang WHERE kode_barang = '" + B.getKodebarang() + "'";
            ResultSet rs = st.executeQuery(sqlSelect);
            if (rs.next()) {
                System.out.println("Data Tersimpan: " + rs.getString("kode_barang") + ", " + rs.getString("nama_barang"));
            }

            String[] data = {B.getKodebarang(), B.getNamaBarang(), String.valueOf(B.getHargaBarang()), String.valueOf(B.getStokbarang())};
            df.addRow(data);
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            // Tutup statement dan koneksi
            try {
                if (st != null) st.close();
                if (cn != null) cn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    void hapusData(String kodeBarang) {
        try {
            Connection cn = new connecDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "DELETE FROM tbl_barang WHERE kode_barang = '" + kodeBarang + "'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus",
                    "Info Proses", JOptionPane.INFORMATION_MESSAGE);
            st.close();
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    void editData(Barang B) {
        try {
            Connection cn = new connecDB().getConnect();
            Statement st = cn.createStatement();
            String sql = "UPDATE tbl_barang SET nama_barang = '" + B.getNamaBarang() + "', harga_barang = " + B.getHargaBarang() + ", stok_barang = " + B.getStokbarang() + " WHERE kode_barang = '" + B.getKodebarang() + "'";
            st.executeUpdate(sql);
            JOptionPane.showMessageDialog(null, "Data Berhasil Diubah",
                    "Info Proses", JOptionPane.INFORMATION_MESSAGE);
            st.close();
            cn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void btAddAksi(ActionEvent evt) {
        try {
            String kodeBarang = txKodeBarang.getText();
            String namaBarang = txNamaBarang.getText();
            int hargaBarang = Integer.parseInt(txHargaBarang.getText());
            int stokBarang = Integer.parseInt(txStokBarang.getText());
            Barang B = new Barang(kodeBarang, namaBarang, hargaBarang, stokBarang);
            simpanData(B);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga dan Stok harus berupa angka", "Error Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btNewAksi(ActionEvent evt) {
        clearTextField();
        btAdd.setEnabled(true);
        btEdit.setEnabled(false);
        btDel.setEnabled(false);
    }

    private void btEditAksi(ActionEvent evt) {
        try {
            String kodeBarang = txKodeBarang.getText();
            String namaBarang = txNamaBarang.getText();
            int hargaBarang = Integer.parseInt(txHargaBarang.getText());
            int stokBarang = Integer.parseInt(txStokBarang.getText());
            Barang B = new Barang(kodeBarang, namaBarang, hargaBarang, stokBarang);
            editData(B);
            clearTable();
            loadData();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Harga dan Stok harus berupa angka", "Error Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btDelAksi(ActionEvent evt) {
        String kodeBarang = txKodeBarang.getText();
        hapusData(kodeBarang);
        clearTable();
        loadData();
        clearTextField();
    }

    private void tabMouseClicked(MouseEvent evt) {
        int row = tab.getSelectedRow();
        txKodeBarang.setText(df.getValueAt(row, 0).toString());
        txNamaBarang.setText(df.getValueAt(row, 1).toString());
        txHargaBarang.setText(df.getValueAt(row, 2).toString());
        txStokBarang.setText(df.getValueAt(row, 3).toString());
        btAdd.setEnabled(false);
        btEdit.setEnabled(true);
        btDel.setEnabled(true);
    }

    public static void main(String[] args) {
        new FormBarang().loadData();
    }
}
