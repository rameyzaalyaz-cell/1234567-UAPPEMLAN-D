import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static GoDriveRental sistem = new GoDriveRental();

    public static void main(String[] args) {
        int pilihan;
        do {
            tampilkanMenu();
            pilihan = bacaInt("Pilih menu: ");
            switch (pilihan) {
                case 1 -> tambahKendaraan();
                case 2 -> sistem.tampilkanDaftarKendaraan();
                case 3 -> menuSewaKendaraan();
                case 4 -> menuKembalikanKendaraan();
                case 5 -> System.out.println("Terima kasih telah menggunakan GoDrive Rental System!");
                default -> System.out.println("[ERROR] Pilihan tidak valid. Silakan coba lagi.");
            }
        } while (pilihan != 5);
    }

    static void tampilkanMenu() {
        System.out.println("====== MENU GO DRIVE RENTAL SYSTEM ======");
        System.out.println("1. Tambah Kendaraan");
        System.out.println("2. Tampilkan Daftar Armada");
        System.out.println("3. Sewa Kendaraan");
        System.out.println("4. Kembalikan Kendaraan");
        System.out.println("5. Keluar");
    }

    static void tambahKendaraan() {
        System.out.print("Masukkan jenis kendaraan (mobil/motor): ");
        String jenis = scanner.nextLine().trim().toLowerCase();

        System.out.print("Masukkan kode kendaraan: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        System.out.print("Masukkan nama kendaraan: ");
        String nama = scanner.nextLine().trim();

        double harga = bacaDouble("Masukkan harga sewa per hari: ");

        if (jenis.equals("mobil")) {
            int kursi = bacaInt("Masukkan kapasitas kursi: ");
            Mobil mobil = new Mobil(kode, nama, harga, kursi);
            sistem.tambahKendaraan(mobil);
        } else if (jenis.equals("motor")) {
            System.out.print("Masukkan jenis transmisi (Matik/Manual): ");
            String transmisi = scanner.nextLine().trim();
            Motor motor = new Motor(kode, nama, harga, transmisi);
            sistem.tambahKendaraan(motor);
        } else {
            System.out.println("[ERROR] Jenis kendaraan tidak dikenal. Hanya 'mobil' atau 'motor'.");
        }
    }

    static void menuSewaKendaraan() {
        System.out.print("Masukkan kode kendaraan yang ingin disewa: ");
        String kode = scanner.nextLine().trim().toUpperCase();

        int lama = bacaInt("Masukkan durasi sewa (dalam hari): ");

        System.out.print("Apakah Anda Member VIP? (y/n): ");
        String vipInput = scanner.nextLine().trim().toLowerCase();
        boolean isVIP = vipInput.equals("y");

        try {
            sistem.sewaKendaraan(kode, lama, isVIP);
        } catch (KendaraanTidakTersediaException e) {
            System.out.println("Exception in thread \"main\" KendaraanTidakTersediaException: " + e.getMessage());
        }
    }

    static void menuKembalikanKendaraan() {
        System.out.print("Masukkan kode kendaraan yang ingin dikembalikan: ");
        String kode = scanner.nextLine().trim().toUpperCase();
        sistem.kembalikanKendaraan(kode);
    }

   
    static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double val = Double.parseDouble(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Input harus berupa angka. Coba lagi.");
            }
        }
    }
}
