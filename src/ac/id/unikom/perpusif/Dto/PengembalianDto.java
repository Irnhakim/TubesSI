package ac.id.unikom.perpusif.Dto;

public class PengembalianDto {

    String id;
    String tanggalPenembalian;
    String status;
    String keterangan;

    //kostruktor
    public PengembalianDto(String id, String tanggalPenembalian, String status, String keterangan) {
        this.id = id;
        this.tanggalPenembalian = tanggalPenembalian;
        this.status = status;
        this.keterangan = keterangan;
    }

    //getter and setter
    public String getId() {
        return id;
    }
    //getter and setter
    public String getTanggalPenembalian() {
        return tanggalPenembalian;
    }
    //getter and setter
    public String getStatus() {
        return status;
    }
    //getter and setter
    public String getKeterangan() {
        return keterangan;
    }

}
