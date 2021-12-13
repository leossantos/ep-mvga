import java.util.*;

// classe que representa uma matriz de valores do tipo double.

class Matriz {

    // constante para ser usada na comparacao de valores double.
    // Se a diferenca absoluta entre dois valores double for menor
    // do que o valor definido por esta constante, eles devem ser
    // considerados iguais.
    public static final double SMALL = 0.000001;

    private int lin, col;
    private double [][] m;

    // metodo estatico que cria uma matriz identidade de tamanho n x n.

    public static Matriz identidade(int n){

        Matriz mat = new Matriz(n, n);
        for(int i = 0; i < mat.lin; i++) mat.m[i][i] = 1;
        return mat;
    }

    // construtor que cria uma matriz de n linhas por m colunas com todas as entradas iguais a zero.

    public Matriz(int n, int m){

        this.lin = n;
        this.col = m;
        this.m = new double[lin][col];
    }

    public void set(int i, int j, double valor){

        m[i][j] = valor;
    }

    public double get(int i, int j){

        return m[i][j];
    }

    // metodo que imprime as entradas da matriz.

    public void imprime(){

        for(int i = 0; i < lin; i++){

            for(int j = 0; j < col; j++){

                System.out.printf("%7.2f ", m[i][j]);
            }

            System.out.println();
        }
    }

    // metodo que imprime a matriz expandida formada pela combinacao da matriz que
    // chama o metodo com a matriz "agregada" recebida como parametro. Ou seja, cada
    // linha da matriz impressa possui as entradas da linha correspondente da matriz
    // que chama o metodo, seguida das entradas da linha correspondente em "agregada".

    public void imprime(Matriz agregada){

        for(int i = 0; i < lin; i++){

            for(int j = 0; j < col; j++){

                System.out.printf("%7.2f ", m[i][j]);
            }

            System.out.print(" |");

            for(int j = 0; j < agregada.col; j++){

                System.out.printf("%7.2f ", agregada.m[i][j]);
            }

            System.out.println();
        }
    }

    // metodo que troca as linhas i1 e i2 de lugar.

    private void trocaLinha(int i1, int i2){
        for (int j = 0; j < this.col; j++) {
            double aux = this.get(i1, j);
            this.set(i1, j, this.get(i2, j));
            this.set(i2, j, aux*-1);
        }
    }

    // metodo que multiplica as entradas da linha i pelo escalar k

    private void multiplicaLinha(int i, double k){
        for (int j = 0; j < this.col; j++) {
            this.set(i, j, this.get(i, j)*k);
        }
    }

    // metodo que faz a seguinte combinacao de duas linhas da matriz:
    //
    // 	(linha i1) = (linha i1) + (linha i2 * k)
    //

    private void combinaLinhas(int i1, int i2, double k){
        for (int j = 0; j < this.col; j++) {
            double aux = this.get(i1, j) + this.get(i2, j)*k;
            this.set(i1, j, aux);
        }
    }

    // metodo que procura, a partir da linha ini, a linha com uma entrada nao nula que
    // esteja o mais a esquerda possivel dentre todas as linhas. Os indices da linha e da
    // coluna referentes a entrada nao nula encontrada sao devolvidos como retorno do metodo.
    // Este metodo ja esta pronto para voces usarem na implementacao da eliminacao gaussiana
    // e eliminacao de Gauss-Jordan.

    private int [] encontraLinhaPivo(int ini){

        int pivo_col, pivo_lin;

        pivo_lin = lin;
        pivo_col = col;

        for(int i = ini; i < lin; i++){

            int j;

            for(j = 0; j < col; j++) if(Math.abs(m[i][j]) > 0) break;

            if(j < pivo_col) {

                pivo_lin = i;
                pivo_col = j;
            }
        }

        return new int [] { pivo_lin, pivo_col };
    }

    // metodo que implementa a eliminacao gaussiana, que coloca a matriz (que chama o metodo)
    // na forma escalonada. As operacoes realizadas para colocar a matriz na forma escalonada
    // tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Este metodo
    // tambem deve calcular e devolver o determinante da matriz que invoca o metodo. Assumimos
    // que a matriz que invoca este metodo eh uma matriz quadrada.

    public double formaEscalonada(Matriz agregada){
        for (int i = 0; i < this.lin-1; i++) {
            int[] cord_pivo = this.encontraLinhaPivo(i);
            int pivo_lin = cord_pivo[0];
            int pivo_col = cord_pivo[1];
            if (pivo_lin != i){
                this.trocaLinha(i, pivo_lin);
                agregada.trocaLinha(i,pivo_lin);
                cord_pivo = this.encontraLinhaPivo(i);
                pivo_lin = cord_pivo[0];
                pivo_col = cord_pivo[1];
            }
            double pivo = this.get(pivo_lin, pivo_col);
            for (int j = 1; j < this.lin-i; j++) {
                double embaixo_pivo = this.m[pivo_lin+j][pivo_col];
                double k = -embaixo_pivo/pivo;
                this.combinaLinhas(pivo_lin+j, pivo_lin, k);
                agregada.combinaLinhas(pivo_lin+j, pivo_lin, k);
            }
        }
//        System.out.println("_____________________________");
//        this.imprime(agregada);
        double determinante = 1;
        for (int i = 0; i < this.lin; i++) {
            determinante *= this.m[i][i];
        }
        return determinante;
    }

    // metodo que implementa a eliminacao de Gauss-Jordan, que coloca a matriz (que chama o metodo)
    // na forma escalonada reduzida. As operacoes realizadas para colocar a matriz na forma escalonada
    // reduzida tambem devem ser aplicadas na matriz "agregada" caso esta seja nao nula. Assumimos que
    // a matriz que invoca esta metodo eh uma matriz quadrada. Não se pode assumir, contudo, que esta
    // matriz ja esteja na forma escalonada (mas voce pode usar o metodo acima para isso).

    public void formaEscalonadaReduzida(Matriz agregada){
        double det = this.formaEscalonada(agregada);
        for (int i = this.lin-1; i > 0 ; i--) {
            int[] cord_pivo = this.encontraLinhaPivo(i);
            int pivo_lin = cord_pivo[0];
            int pivo_col = cord_pivo[1];
            if (pivo_lin != i){
                this.trocaLinha(i, pivo_lin);
                agregada.trocaLinha(i,pivo_lin);
                cord_pivo = this.encontraLinhaPivo(i);
                pivo_lin = cord_pivo[0];
                pivo_col = cord_pivo[1];
            }
            double pivo = this.get(pivo_lin, pivo_col);
            for (int j = 1; j <= i; j++) {
                double acima_pivo = this.m[pivo_lin-j][pivo_col];
                double k = -acima_pivo/pivo;
                this.combinaLinhas(pivo_lin-j, pivo_lin, k);
                agregada.combinaLinhas(pivo_lin-j, pivo_lin, k);
            }
//            this.imprime(agregada);
        }
//        System.out.println("-----------------------");
        for (int i = 0; i < this.lin; i++) {
            double coeficiente = agregada.get(i, i);
            double k = 1/coeficiente;
            agregada.multiplicaLinha(i, k);
            this.multiplicaLinha(i, k);
        }
//        this.imprime(agregada);

        Matriz resultado = new Matriz(this.lin, agregada.col-this.col);

        for (int i = 0; i < resultado.lin; i++) {
            for (int j = 0; j < resultado.col; j++) {
                double aux = agregada.get(i, j+this.col);
                resultado.set(i, j, aux);
            }
        }
        resultado.imprime();
    }
}
