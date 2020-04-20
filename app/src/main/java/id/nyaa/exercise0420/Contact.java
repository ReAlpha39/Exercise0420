package id.nyaa.exercise0420;

public class Contact {

    private String id;
    private String nama;
    private String nomor;
    private String email;
    private String alamat;

    public Contact(String id, String nama, String nomor, String email, String alamat) {
        this.id = id;
        this.nama = nama;
        this.nomor = nomor;
        this.email = email;
        this.alamat = alamat;
    }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public String getNomor() {
        return this.nomor;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAlamat() {
        return this.alamat;
    }


}
