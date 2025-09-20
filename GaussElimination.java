import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GaussElimination extends JFrame {
    // Deklarasi komponen GUI
    private JTextField[][] matrixFields;
    private JTextArea resultArea;
    private JButton solveButton;
    private JComboBox<String> methodComboBox;

    public GaussElimination() {
        // Set judul
        setTitle("Penyelesaian Persamaan Linear Metode Eliminasi Gauss");

        // Panel input matriks
        JPanel matrixPanel = new JPanel(new GridLayout(3, 4, 5, 5)); // Matriks augmented 3x4
        matrixFields = new JTextField[3][4]; // Matriks augmented 3x4

        // Inisialisasi elemen input matriks
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                matrixFields[i][j] = new JTextField(3);
                matrixPanel.add(matrixFields[i][j]);
            }
        }

        // ComboBox untuk metode (tambah opsi Gauss-Jordan)
        methodComboBox = new JComboBox<>(new String[] { "Gauss", "Gauss-Jordan" });

        // Tombol untuk menghitung
        solveButton = new JButton("Kerjakan");

        // Area teks untuk menampilkan hasil
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        // Layout utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        inputPanel.add(new JLabel("Matrik 3 x 3"));
        inputPanel.add(matrixPanel);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());
        controlPanel.add(new JLabel("Metode"));
        controlPanel.add(methodComboBox);
        controlPanel.add(solveButton);

        // Tambahkan panel input dan kontrol
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(controlPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        add(mainPanel);

        // ActionListener untuk tombol kerjakan
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[][] augmentedMatrix = new double[3][4];
                double[][] originalMatrix = new double[3][4];

                // Mengambil input dari field dan menyimpan salinan original
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
                        augmentedMatrix[i][j] = Double.parseDouble(matrixFields[i][j].getText());
                        originalMatrix[i][j] = augmentedMatrix[i][j]; // Salin matriks asli
                    }
                }

                // Tampilkan matriks augmented awal
                resultArea.setText("Augmented Matrik:\n");
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 4; j++) {
                        resultArea.append(String.format("%.1f ", originalMatrix[i][j]));
                    }
                    resultArea.append("\n");
                }

                // Pilih metode berdasarkan pilihan user
                String selectedMethod = (String) methodComboBox.getSelectedItem();
                String hasil;
                if ("Gauss".equals(selectedMethod)) {
                    hasil = selesaikanGauss(augmentedMatrix); // Menggunakan nama metode yang benar
                } else {
                    hasil = selesaikanGaussJordan(augmentedMatrix); // Menggunakan nama metode yang benar
                }

                // Tampilkan hasil akhir
                resultArea.append(hasil);
            }
        });

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    // Metode eliminasi Gauss
    public String selesaikanGauss(double[][] matriks) {
        int n = matriks.length;
        StringBuilder hasil = new StringBuilder("Matriks Augmented Awal:\n");
        hasil.append(tampilkanMatriks(matriks)).append("\n");

        for (int i = 0; i < n; i++) {
            for (int k = i + 1; k < n; k++) {
                double factor = matriks[k][i] / matriks[i][i];
                for (int j = i; j <= n; j++) {
                    matriks[k][j] -= factor * matriks[i][j];
                }
            }
        }

        double[] solusi = new double[n];
        for (int i = n - 1; i >= 0; i--) {
            solusi[i] = matriks[i][n];
            for (int j = i + 1; j < n; j++) {
                solusi[i] -= matriks[i][j] * solusi[j];
            }
            solusi[i] /= matriks[i][i];
        }

        hasil.append("Hasil OBE (Segitiga atas):\n");
        hasil.append(tampilkanMatriks(matriks)).append("\n");
        hasil.append("Hasil dengan metode Gauss:\n");
        for (int i = 0; i < n; i++) {
            hasil.append("x").append(i + 1).append(" = ").append(String.format("%+.2f", solusi[i])).append("\n\n");
        }

        return hasil.toString();
    }

    // Metode eliminasi Gauss-Jordan
    public String selesaikanGaussJordan(double[][] matriks) {
        int n = matriks.length;
        StringBuilder hasil = new StringBuilder("Matriks Augmented Awal:\n");
        hasil.append(tampilkanMatriks(matriks)).append("\n");

        double[][] a = new double[n][n + 1];
        for (int i = 0; i < n; i++) {
            System.arraycopy(matriks[i], 0, a[i], 0, n + 1);
        }

        for (int k = 0; k < n - 1; k++) {
            for (int m = k + 1; m < n; m++) {
                double factor = a[m][k] / a[k][k];
                for (int n1 = 0; n1 <= n; n1++) {
                    a[m][n1] -= factor * a[k][n1];
                }
            }
        }

        hasil.append("Hasil OBE 1 (Segitiga atas):\n");
        hasil.append(tampilkanMatriks(a)).append("\n");

        for (int i = n - 1; i >= 0; i--) {
            for (int k = i - 1; k >= 0; k--) {
                double factor = a[k][i] / a[i][i];
                for (int n1 = 0; n1 <= n; n1++) {
                    a[k][n1] -= factor * a[i][n1];
                }
            }
        }

        hasil.append("Hasil OBE 2:\n");
        hasil.append(tampilkanMatriks(a)).append("\n");

        hasil.append("Hasil dengan metode Gauss-Jordan:\n");
        for (int i = 0; i < n; i++) {
            hasil.append("x").append(i + 1).append(" = ").append(String.format("%+.2f", a[i][n])).append("\n\n");
        }

        return hasil.toString();
    }

    // Metode substitusi mundur untuk eliminasi Gauss
    private double[] backSubstitution(double[][] augmentedMatrix) {
        int n = augmentedMatrix.length;
        double[] solution = new double[n];

        for (int i = n - 1; i >= 0; i--) {
            solution[i] = augmentedMatrix[i][n]; // Ambil elemen bebas dari augmented matrix
            for (int j = i + 1; j < n; j++) {
                solution[i] -= augmentedMatrix[i][j] * solution[j];
            }
            solution[i] /= augmentedMatrix[i][i];
        }

        return solution;
    }

    // Metode untuk menampilkan matriks
    private String tampilkanMatriks(double[][] matriks) {
        StringBuilder builder = new StringBuilder();
        for (double[] baris : matriks) {
            for (double elemen : baris) {
                builder.append(String.format("%.2f ", elemen));
            }
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        new GaussElimination();
    }
}
