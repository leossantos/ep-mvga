// Classe "executavel".

import java.util.Scanner;

public class EP1 {

    // metodo principal.

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);    // Scanner para facilitar a leitura de dados a partir da entrada padrao.
        String operacao = in.next();        // le, usando o scanner, a string que determina qual operacao deve ser realizada.
        int n = in.nextInt();            // le a dimensão da matriz a ser manipulada pela operacao escolhida.
        in.nextLine();

        if ("resolve".equals(operacao)) {
            Matriz coeficientes = new Matriz(n, n);
            Double[] valores = new Double[n];
            for (int i = 0; i < n; i++) {
                String[] line = in.nextLine().split(" ");
                for (int j = 0; j < n; j++) {
                    coeficientes.set(i, j, Double.parseDouble(line[j]));
                }
                valores[i] = Double.parseDouble(line[n]);
            }
            coeficientes.imprime();

        } else if ("inverte".equals(operacao)) {
            read_matrix(in, n);

        } else if ("determinante".equals(operacao)) {
            Matriz matriz = read_matrix(in, n);

        } else {
            System.out.println("Operação desconhecida!");
            System.exit(1);
        }
    }

    private static Matriz read_matrix(Scanner in, int n) {
        Matriz matriz = new Matriz(n, n);
        for (int i = 0; i < n; i++) {
            String[] line = in.nextLine().split(" ");
            for (int j = 0; j < n; j++) {
                matriz.set(i, j, Double.parseDouble(line[j]));
            }
        }
        matriz.imprime();
        return matriz;
    }
}