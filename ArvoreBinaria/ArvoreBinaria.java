import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class ArvoreBinaria {

    private No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    public void inserir(int valor) {
        if (this.raiz == null) {
            this.raiz = new No(valor);
        } else {
            this.inserir(valor, this.raiz);
        }
    }

    private void inserir(int valor, No no) {
        if (valor < no.valor) {
            if (no.esquerda == null) {
                no.esquerda = new No(valor);
            } else {
                this.inserir(valor, no.esquerda);
            }
        } else {
            if (no.direita == null) {
                no.direita = new No(valor);
            } else {
                this.inserir(valor, no.direita);
            }
        }
    }

    public void imprimirPreOrdem() {
        if (this.raiz != null) {
            this.imprimirPreOrdem(this.raiz);
        }
    }

    private void imprimirPreOrdem(No no) {
        if (no != null) {
            System.out.println(no.valor);
            this.imprimirPreOrdem(no.esquerda);
            this.imprimirPreOrdem(no.direita);
        }
    }

    public void imprimirInOrdem() {
        if (this.raiz != null) {
            this.imprimirInOrdem(this.raiz);
        }
    }

    private void imprimirInOrdem(No no) {
        if (no != null) {
            this.imprimirInOrdem(no.esquerda);
            System.out.println(no.valor);
            this.imprimirInOrdem(no.direita);
        }
    }

    public void imprimirPosOrdem() {
        if (this.raiz != null) {
            this.imprimirPosOrdem(this.raiz);
        }
    }

    private void imprimirPosOrdem(No no) {
        if (no != null) {
            this.imprimirPosOrdem(no.esquerda);
            this.imprimirPosOrdem(no.direita);
            System.out.println(no.valor);
        }
    }

    public void imprimirEmNivel() {
        if (this.raiz == null) {
            System.out.println("Árvore vazia");
            return;
        }

        Queue<No> fila = new LinkedList<>();
        fila.add(this.raiz);

        while (!fila.isEmpty()) {
            int tamanhoNivel = fila.size();

            for (int i = 0; i < tamanhoNivel; i++) {
                No noAtual = fila.poll();
                System.out.print(noAtual.valor + " ");

                if (noAtual.esquerda != null && !fila.contains(noAtual.esquerda)) {
                    fila.add(noAtual.esquerda);
                }

                if (noAtual.direita != null && !fila.contains(noAtual.direita)) {
                    fila.add(noAtual.direita);
                }
            }
            System.out.println();
        }
    }

    public void gerarArvoreAleatoria() {
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            int valor = random.nextInt(101);
            this.inserir(valor);
        }
    }

    public static void main(String[] args) {
        ArvoreBinaria arvore = new ArvoreBinaria();
        arvore.gerarArvoreAleatoria();
        System.out.println("Árvore binária pré-ordem: ");
        arvore.imprimirPreOrdem();
        System.out.println("\nÁrvore binária in-ordem: ");
        arvore.imprimirInOrdem();
        System.out.println("\nÁrvore binária pós-ordem: ");
        arvore.imprimirPosOrdem();
        System.out.println("\nSequência em nível: ");
        arvore.imprimirEmNivel();
    }
}