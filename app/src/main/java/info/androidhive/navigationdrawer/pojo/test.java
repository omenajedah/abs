package info.androidhive.navigationdrawer.pojo;

import java.util.ArrayList;
import java.util.List;

public class test {

    private String test;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;

        List<String> kelas = new ArrayList<>();

        String namaKelas = kelas.get(0);


        List<Kelas> kelas1 = new ArrayList<>();

        String id = kelas1.get(0).getId_kelas();
        String nama = kelas1.get(0).getNama_kelas();
    }
}
