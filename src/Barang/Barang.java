package Barang;

public class Barang {
    private String kodebarang;
    private String namaBarang;
    private int hargaBarang;
    private int stokbarang;

    //Constructor
    public Barang(String kodebarang, String namaBarang, int hargaBarang, int stokbarang) {
        this.kodebarang = kodebarang;
        this.namaBarang = namaBarang;
        this.hargaBarang = hargaBarang;
        this.stokbarang = stokbarang;
    }

    //Getter and Setter
    public String getKodebarang() {
        return kodebarang;
    }

    public void setKodebarang(String kodebarang) {
        this.kodebarang = kodebarang;
    }

    public String getNamaBarang() {
        return namaBarang;  
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public int getHargaBarang() {
        return hargaBarang;
    }

    public void setHargaBarang(int hargaBarang) {
        this.hargaBarang = hargaBarang;
    }

    public int getStokbarang() {
        return stokbarang;
    }

    public void setStokbarang(int stokbarang) {
        this.stokbarang = stokbarang;
    }
}
