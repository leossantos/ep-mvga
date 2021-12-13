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
            Matriz[] matrizes = read_matrix(in, n, operacao);
            Matriz matriz = matrizes[0];
            Matriz agregada = matrizes[1];
            matriz.formaEscalonadaReduzida(agregada);

        } else if ("inverte".equals(operacao)) {
            Matriz[] matrizes = read_matrix(in, n, operacao);
            Matriz matriz = matrizes[0];
            Matriz agregada = matrizes[1];
            matriz.formaEscalonadaReduzida(agregada);

        } else if ("determinante".equals(operacao)) {
            Matriz[] matrizes = read_matrix(in, n, operacao);
            Matriz matriz = matrizes[0];
            Matriz agregada = matrizes[1];
            double determinante = matriz.formaEscalonada(agregada);
            System.out.println(determinante);

        } else {
            System.out.println("Operação desconhecida!");
            System.exit(1);
        }
    }

    private static Matriz[] read_matrix(Scanner in, int n, String operacao) {
        Matriz matriz;
        Matriz agregada;
        Matriz identidade = Matriz.identidade(n);
        if (operacao.equals("inverte")){
            matriz = new Matriz(n, n);
            agregada = new Matriz(n, n * 2);
        }
        else {
            matriz = new Matriz(n, n);
            agregada = new Matriz(n, n + 1);
        }
        for (int i = 0; i < n; i++) {
            String[] line = in.nextLine().split(" ");
            double resul = 0;
            for (int j = 0; j < n; j++) {
                double aux = Double.parseDouble(line[j]);
                resul += aux;
                matriz.set(i, j, aux);
                agregada.set(i, j, aux);
            }
            if (operacao.equals("inverte")) {
                for (int j = n; j < n*2; j++) {
                    double aux = identidade.get(i, j-n);
                    agregada.set(i, j, aux);
                }
            }
            else{
                double aux;
                if (operacao.equals("resolve")) aux = Double.parseDouble(line[n]);
                else aux = resul;
                agregada.set(i, n, aux);
            }
        }
//        matriz.imprime(agregada);

        return new Matriz[]{matriz, agregada};
    }
}