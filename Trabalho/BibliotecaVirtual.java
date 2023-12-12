import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

class Livro {
    String titulo;
    String autor;
    String genero;

    public Livro(String titulo, String autor, String genero) {
        this.titulo = titulo;
        this.autor = autor;
        this.genero = genero;
    }

    @Override
    public String toString() {
        return "Livro{" +
                "titulo='" + titulo + '\'' +
                ", autor='" + autor + '\'' +
                ", genero='" + genero + '\'' +
                '}';
    }
}

public class BibliotecaVirtual {

    private static final int NUM_LIVROS = 10000;
    private static final int NUM_LIVROS_EXIBICAO = 100;
    private static final String[] NOMES_TEMAS = {"Tecnologia", "Historia", "Ciencia", "Autoajuda"};
    private static final String[] NOMES_AUTORES = {"Autor"};

    private static String gerarNomeLivro() {
        return String.format("Livro%04d", new Random().nextInt(10001));
    }

    private static String gerarNomeAutor(int indiceAleatorio) {
        return NOMES_AUTORES[indiceAleatorio] + new Random().nextInt(501);
    }

    private static String gerarTema() {
        return NOMES_TEMAS[new Random().nextInt(NOMES_TEMAS.length)];
    }

    private static Livro[] gerarLivros(int quantidade) {
        Livro[] livros = new Livro[quantidade];
        Set<String> titulosExistentes = new HashSet<>();

        for (int i = 0; i < quantidade; i++) {
            String novoTitulo;
            do {
                novoTitulo = gerarNomeLivro();
            } while (titulosExistentes.contains(novoTitulo));

            titulosExistentes.add(novoTitulo);
            livros[i] = new Livro(novoTitulo, gerarNomeAutor(new Random().nextInt(NOMES_AUTORES.length)), gerarTema());
        }

        return livros;
    }

    private static void quickSort(Livro[] livros, int inicio, int fim) {
        if (inicio < fim) {
            int indiceParticao = particionar(livros, inicio, fim);

            quickSort(livros, inicio, indiceParticao - 1);
            quickSort(livros, indiceParticao + 1, fim);
        }
    }

    private static int particionar(Livro[] livros, int inicio, int fim) {
        Livro pivo = livros[fim];
        int indiceMenor = inicio - 1;

        for (int i = inicio; i < fim; i++) {
            if (livros[i].titulo.compareTo(pivo.titulo) <= 0) {
                indiceMenor++;
                trocar(livros, indiceMenor, i);
            }
        }

        trocar(livros, indiceMenor + 1, fim);
        return indiceMenor + 1;
    }

    private static void trocar(Livro[] livros, int i, int j) {
        Livro temp = livros[i];
        livros[i] = livros[j];
        livros[j] = temp;
    }

    // Implementação do MergeSort
    private static void mergeSort(Livro[] livros, int inicio, int fim) {
        if (inicio < fim) {
            int meio = (inicio + fim) / 2;

            mergeSort(livros, inicio, meio);
            mergeSort(livros, meio + 1, fim);

            mesclar(livros, inicio, meio, fim);
        }
    }

    private static void mesclar(Livro[] livros, int inicio, int meio, int fim) {
        int tamanhoEsquerda = meio - inicio + 1;
        int tamanhoDireita = fim - meio;

        Livro[] ladoEsquerdo = Arrays.copyOfRange(livros, inicio, inicio + tamanhoEsquerda);
        Livro[] ladoDireito = Arrays.copyOfRange(livros, meio + 1, meio + 1 + tamanhoDireita);

        int i = 0, j = 0, k = inicio;
        while (i < tamanhoEsquerda && j < tamanhoDireita) {
            if (ladoEsquerdo[i].autor.compareTo(ladoDireito[j].autor) <= 0) {
                livros[k++] = ladoEsquerdo[i++];
            } else {
                livros[k++] = ladoDireito[j++];
            }
        }

        while (i < tamanhoEsquerda) {
            livros[k++] = ladoEsquerdo[i++];
        }

        while (j < tamanhoDireita) {
            livros[k++] = ladoDireito[j++];
        }
    }

    private static void procurarLivro(Livro[] biblioteca) {
        Scanner scanner = new Scanner(System.in);

        // Cria uma cópia dos livros para usar o MergeSort
        Livro[] copiaBiblioteca = Arrays.copyOf(biblioteca, biblioteca.length);

        // Ordena a cópia usando MergeSort
        mergeSort(copiaBiblioteca, 0, copiaBiblioteca.length - 1);

        do {
            System.out.println("Digite o título do livro que está procurando:");
            String livroProcurado = scanner.nextLine();

            // Busca o livro na cópia ordenada
            buscarPorTitulo(copiaBiblioteca, livroProcurado);

            System.out.println("Deseja informar outro livro? (S para Sim / N para Não):");
            String resposta = scanner.nextLine().toUpperCase();

            if (resposta.equals("S")) {
            } else if (resposta.equals("N")) {
                System.out.println("Saindo do programa. Até mais!");
                break;
            } else {
                System.out.println("Opção inválida. Por favor, digite S para Sim ou N para Não.");
            }

        } while (true);
    }

    private static void buscarPorTitulo(Livro[] biblioteca, String titulo) {
        for (Livro livro : biblioteca) {
            if (livro.titulo.equalsIgnoreCase(titulo)) {
                System.out.println("Livro encontrado: " + livro);
                return;
            }
        }
        System.out.println("Livro não encontrado.");
    }

    public static void main(String[] args) {
        Livro[] biblioteca = gerarLivros(NUM_LIVROS);

        quickSort(biblioteca, 0, biblioteca.length - 1);

        System.out.println("Livros ordenados por título:");
        Arrays.stream(biblioteca).limit(NUM_LIVROS_EXIBICAO).forEach(System.out::println);

        procurarLivro(biblioteca);
    }
}
