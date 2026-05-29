import java.util.ArrayList;

public class GoDriveRental {
    private ArrayList<Kendaraan> daftarKendaraan;

    public GoDriveRental() {
        daftarKendaraan = new ArrayList<>();
    }

    public void tambahKendaraan(Kendaraan k) {
        daftarKendaraan.add(k);
        System.out.println("[INFO] Kendaraan berhasil ditambahkan: " + k.getNamaKendaraan() + " (" + k.getKodeKendaraan() + ")");
    }

    public void tampilkanDaftarKendaraan() {
        if (daftarKendaraan.isEmpty()) {
            System.out.println("[INFO] Belum ada kendaraan dalam armada.");
            return;
        }
        System.out.println("\n=== DAFTAR ARMADA GODRIVE ===");
        int no = 1;
        for (Kendaraan k : daftarKendaraan) {
            System.out.print(no + ". ");
            k.tampilInfo();
            no++;
        }
    }

    public void sewaKendaraan(String kode, int lamaSewa, boolean isVIP) throws KendaraanTidakTersediaException {
        Kendaraan target = cariKendaraan(kode);

        if (target == null || !target.isTersedia()) {
            throw new KendaraanTidakTersediaException(
                "Kendaraan dengan kode " + kode + " gagal disewa. Alasan: Kendaraan sedang disewa atau tidak ditemukan!"
            );
        }

        double biayaDasar = target.hitungBiayaDasar(lamaSewa);
        double totalBiaya = biayaDasar;
        double diskonVIP = 0;
        double diskon7Hari = 0;

        // Diskon member VIP = 10 persen
        if (isVIP) {
            diskonVIP = biayaDasar * 0.10;
            totalBiaya -= diskonVIP;
        }

        // Diskon jika sewa > 7 hari =  15 persen
        if (lamaSewa > 7) {
            diskon7Hari = biayaDasar * 0.15;
            totalBiaya -= diskon7Hari;
        }

        target.setTersedia(false);

        System.out.println("\n=== TRANSAKSI SEWA GODRIVE ===");
        System.out.println("Kendaraan Berhasil Disewa!");
        System.out.printf("Unit         : %s (%s)%n", target.getNamaKendaraan(), target.getKodeKendaraan());
        System.out.printf("Lama Sewa    : %d hari%n", lamaSewa);
        System.out.printf("Biaya Dasar Harian : Rp %,.0f%n", biayaDasar);

        if (target instanceof Mobil) {
            Mobil m = (Mobil) target;
            if (m.getJumlahKursi() > 5) {
                System.out.printf("Tambahan Kursi (>5): Rp 50,000%n");
            }
        } else if (target instanceof Motor) {
            Motor mo = (Motor) target;
            if (mo.getJenisTransmisi().equalsIgnoreCase("Matik")) {
                System.out.printf("Asuransi Matik (Rp10.000 x %d hari): Rp %,.0f%n",
                        lamaSewa, 10000.0 * lamaSewa);
            }
        }

        if (isVIP) {
            System.out.printf("Diskon Member VIP (10%%): -Rp %,.0f%n", diskonVIP);
        }
        if (lamaSewa > 7) {
            System.out.printf("Diskon Sewa >7 Hari (15%%): -Rp %,.0f%n", diskon7Hari);
        }

        System.out.println("------------------------------------------");
        System.out.printf("TOTAL BIAYA AKHIR: Rp %,.0f%n", totalBiaya);
    }

    public void kembalikanKendaraan(String kode) {
        Kendaraan target = cariKendaraan(kode);
        if (target == null) {
            System.out.println("[ERROR] Kendaraan dengan kode " + kode + " tidak ditemukan.");
            return;
        }
        if (target.isTersedia()) {
            System.out.println("[INFO] Kendaraan " + target.getNamaKendaraan() + " (" + kode + ") tidak sedang disewa.");
            return;
        }
        target.setTersedia(true);
        System.out.println("[INFO] Kendaraan " + target.getNamaKendaraan() + " (" + kode + ") berhasil dikembalikan. Status: Tersedia.");
    }

    private Kendaraan cariKendaraan(String kode) {
        for (Kendaraan k : daftarKendaraan) {
            if (k.getKodeKendaraan().equalsIgnoreCase(kode)) {
                return k;
            }
        }
        return null;
    }
}
